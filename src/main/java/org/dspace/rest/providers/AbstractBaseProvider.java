/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.params.EntityBuildParameters;
import org.dspace.rest.params.PaginationParameters;
import org.dspace.rest.params.SortParameters;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.EntityView;
import org.sakaiproject.entitybus.entityprovider.EntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.capabilities.ActionsExecutable;
import org.sakaiproject.entitybus.entityprovider.capabilities.CollectionResolvable;
import org.sakaiproject.entitybus.entityprovider.capabilities.Describeable;
import org.sakaiproject.entitybus.entityprovider.capabilities.InputTranslatable;
import org.sakaiproject.entitybus.entityprovider.capabilities.Outputable;
import org.sakaiproject.entitybus.entityprovider.capabilities.Redirectable;
import org.sakaiproject.entitybus.entityprovider.capabilities.RequestAware;
import org.sakaiproject.entitybus.entityprovider.capabilities.RequestInterceptor;
import org.sakaiproject.entitybus.entityprovider.capabilities.RequestStorable;
import org.sakaiproject.entitybus.entityprovider.capabilities.Resolvable;
import org.sakaiproject.entitybus.entityprovider.extension.Formats;
import org.sakaiproject.entitybus.entityprovider.extension.RequestGetter;
import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;
import org.sakaiproject.entitybus.exception.EntityException;
import org.sakaiproject.entitybus.rest.EntityEncodingManager;

/**
 * Base abstract class for Entity Providers. Takes care about general
 * operations like extracting url parameters, registration, unregistration <br/>
 * and other stuff. The Entity Provider should extend this class and implement
 * CoreEntityProvider. This class implements capabilities as it is currently
 * planed for REST support in DSpace, meaning, there is no Inputable capability
 * implemented but could be easily extended later if necessary.
 *
 * @author Bojan Suzic(bojan.suzic@gmail.com)
 */
public abstract class AbstractBaseProvider implements EntityProvider, Resolvable, CollectionResolvable, InputTranslatable, RequestAware, Outputable, Describeable, ActionsExecutable, Redirectable, RequestStorable, RequestInterceptor {

    // query parameters used in subclasses
    protected RequestStorage requestStore;
    protected boolean withdrawn;
    protected String user = "";
    protected String pass = "";
    protected String userc = "";
    protected String passc = "";
    protected String loggedUser, _sdate, _edate;
    private static Logger log = Logger.getLogger(AbstractBaseProvider.class);

    /**
     * Handle registration of EntityProvider
     * @param entityProviderManager
     * @throws java.sql.SQLException
     */
    public AbstractBaseProvider(
            EntityProviderManager entityProviderManager) throws SQLException {
        this.entityProviderManager = entityProviderManager;
        entityProviderManager.registerEntityProvider(this);
    }

    public void setRequestStorage(RequestStorage rStor) {
        this.requestStore = rStor;
    }

    protected EntityProviderManager entityProviderManager;

    public void setEntityProviderManager(EntityProviderManager entityProviderManager) {
        this.entityProviderManager = entityProviderManager;
    }

    public void destroy() throws Exception {
        entityProviderManager.unregisterEntityProvider(this);
    }

    /**
     * Extracts and returns information about current session user, for logging
     * @return
     */
    public String userInfo() {
        String ipaddr = "";


        try {
            ipaddr = this.entityProviderManager.getRequestGetter().getRequest().getRemoteAddr();


        } catch (NullPointerException ex) {
        }
        return "user:" + loggedUser + ":ip_addr=" + ipaddr + ":";


    }

    private String readIStoString(InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * Checks request headers and applying requested format and login data
     * note that header based request has precedence over query one
     * This method is called before other methods processing request
     * so we can change some properties of response
     * @param view
     * @param req
     * @param res
     */
    public void before(EntityView view, HttpServletRequest req, HttpServletResponse res) {
        // json by default if nothing is requested
        try {
            if (req.getContentType().equals("application/json")) {
                view.setExtension("json");
            } else if (req.getContentType().equals("application/xml")) {
                view.setExtension("xml");
            } else {
                view.setExtension("json");
            }
        } catch (Exception ex) {
            if (view.getFormat().equals("xml")) {
                view.setExtension("xml");
            } else {
                view.setExtension("json");
            }
        }

        /**
         * Check user/login data in header and apply if present
         */
        try {
            if (!(req.getHeader("user").isEmpty() && req.getHeader("pass").isEmpty())) {
                userc = req.getHeader("user");
                passc = req.getHeader("pass");
            }
        } catch (NullPointerException nu) {
            userc = "";
            passc = "";
        }
    }

    /**
     * Called after processing of request
     * Not relevant in this case but implementation must be available
     * @param view
     * @param req
     * @param res
     */
    public void after(EntityView view, HttpServletRequest req, HttpServletResponse res) {
    }

    /**
     * Extract parameters from query and do basic authentication, analyze
     * and prepare sorting and other fields
     * @param context current database context locally (in subclass method)
     * defined but used here for loging and other purposes
     */
    private void refreshParams(Context context) {
        
        /**
         * now check user login info and try to register
         */
        try {
            user = requestStore.getStoredValue("user").toString();
        } catch (NullPointerException ex) {
            user = "";
        }

        try {
            pass = requestStore.getStoredValue("pass").toString();
        } catch (NullPointerException ex) {
            pass = "";
        }

        // these are from header - have priority
        try {
            if (!(userc.isEmpty() && passc.isEmpty())) {
                user = userc;
                pass = passc;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } // now try to login user
        loggedUser = "anonymous";


        try {
            EPerson eUser = EPerson.findByEmail(context, user);
            if ((eUser.canLogIn()) && (eUser.checkPassword(pass))) {
                context.setCurrentUser(eUser);
                loggedUser = eUser.getName();
            } else {
                throw new EntityException("Bad username or password", user, 403);
            }
        } catch (SQLException sql) {
            log.info(sql.toString());
            sql.printStackTrace();
        } catch (AuthorizeException auth) {
            throw new EntityException("Unauthorised", user, 401);

// TODO Refactor this, it seems the catching of usernames/passwords does not work


        } catch (NullPointerException ne) {
            if (!(user.equals("") && pass.equals(""))) {
                throw new EntityException("Bad username or password", user, 403);
            }
        }

        try {
            _sdate = requestStore.getStoredValue("startdate").toString();
        } catch (NullPointerException ex) {
            _sdate = null;
        }

        try {
            _edate = requestStore.getStoredValue("enddate").toString();
        } catch (NullPointerException ex) {
            _edate = null;
        }

        try {
            withdrawn = requestStore.getStoredValue("withdrawn").toString().equalsIgnoreCase("true");
        } catch (NullPointerException ex) {
            withdrawn = false;
        }
    }

    public String[] getHandledInputFormats() {
        return new String[]{Formats.HTML, Formats.XML, Formats.JSON};
    }

    public Object translateFormattedData(EntityReference ref, String format, InputStream input, Map<String, Object> params) {
        String IS = "";
        try {
            IS = readIStoString(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Map<String, Object> decodedInput = new HashMap<String, Object>();
        EntityEncodingManager em = new EntityEncodingManager(null, null);
        if (format.equals("xml")) {
            decodedInput = em.decodeData(IS, Formats.XML);
        } else {
            decodedInput = em.decodeData(IS, Formats.JSON);
        }
        return decodedInput;
    }

    /**
     * Remove items from list in order to display only requested items
     * (according to _start, _limit etc.)
     * @param entities
     */
    public void removeTrailing(List<?> entities) {
        new PaginationParameters(requestStore).removeTrailing(entities);
    }

    /**
     * Complete connection in order to lower load of sql server
     * this way it goes faster and prevents droppings with higher load
     * @param context
     */
    public void complete(Context context) {
        // close connection to prevent connection problems
        try {
            context.complete();
        } catch (SQLException ex) {
        }
    }


    public String[] getHandledOutputFormats() {
        return new String[]{Formats.JSON, Formats.XML, Formats.FORM, Formats.ATOM};

    }

    protected final Context context() {
        final Context context;
        try {
            context = new Context();
        } catch (SQLException ex) {
            throw new SQLFailureEntityException(Operation.CREATE_CONTEXT, ex);
        }
        refreshParams(context);
        return context;
    }

    protected void sort(final List<Object> entities) {
        /**
         * if the full info are requested and there are sorting requirements
         * process entities through sorting filter first
         */
        if (!EntityBuildParameters.build(requestStore).isIdOnly()) {
            new SortParameters(requestStore).sort(entities);
        }
    }

    protected final void logUserInfo(Operation operation) {
        if (log.isDebugEnabled()) log.debug(userInfo() + operation.getDescription());
    }

    @Override
    public final void setRequestGetter(RequestGetter requestGetter) {}   
}
