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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import org.dspace.rest.entities.CommunityEntity;
import org.dspace.rest.entities.DetailDepth;
import org.dspace.rest.params.DetailDepthParameters;
import org.dspace.rest.params.EntityBuildParameters;
import org.dspace.rest.params.PaginationParameters;
import org.dspace.rest.params.SortParameters;
import org.dspace.rest.util.RecentSubmissionsException;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.EntityView;
import org.sakaiproject.entitybus.entityprovider.EntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.EntityProviderMethodStore;
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
import org.sakaiproject.entitybus.entityprovider.extension.ActionReturn;
import org.sakaiproject.entitybus.entityprovider.extension.EntityData;
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
    protected Map<String, String> func2actionMapGET = new HashMap<String, String>();
    protected Map<String, String> func2actionMapPUT = new HashMap<String, String>();
    protected Map<String, String> func2actionMapPOST = new HashMap<String, String>();
    protected Map<String, String> func2actionMapDELETE = new HashMap<String, String>();
    protected Map<String, Class<?>[]> funcParamsGET = new HashMap<String, Class<?>[]>();
    protected Map<String, Class<?>[]> funcParamsPUT = new HashMap<String, Class<?>[]>();
    protected Map<String, Class<?>[]> funcParamsPOST = new HashMap<String, Class<?>[]>();
    protected Map<String, Class<?>[]> funcParamsDELETE = new HashMap<String, Class<?>[]>();
    protected Map<String, String[]> inputParamsPOST = new HashMap<String, String[]>();
    protected Map<String, String> func2actionMapGET_rev = new HashMap<String, String>();
    protected Map<String, String> func2actionMapPUT_rev = new HashMap<String, String>();
    protected Map<String, String> func2actionMapPOST_rev = new HashMap<String, String>();
    protected Map<String, String> func2actionMapDELETE_rev = new HashMap<String, String>();
    protected Class<?> processedEntity = CommunityEntity.class;
    private Constructor<?> ctr = null;
    protected Constructor<?> entityConstructor = null;
    protected Map<String, Object> reqInput = new HashMap<String, Object>();
    protected RequestGetter requestGetter;

    /**
     * Handle registration of EntityProvider
     * @param entityProviderManager
     * @throws java.sql.SQLException
     */
    public AbstractBaseProvider(
            EntityProviderManager entityProviderManager) throws SQLException {
        this.entityProviderManager = entityProviderManager;
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException("Unable to register the provider (" + this + "): " + e, e);

        } // get request info for later parsing of parameters
        //this.reqStor = entityProviderManager.getRequestStorage();

    }

    protected void initMappings(Class<?> processedEntity) throws NoSuchMethodException {
        ctr = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, DetailDepth.class});
        // scan for methods;
        Method[] entityMethods = processedEntity.getMethods();
        for (Method m : entityMethods) {
            //log.info("checked method " + m.getName());
            String fieldPUT = func2actionMapPUT.get(m.getName());
            if (fieldPUT != null) {
//                log.info("added " + fieldPUT + ":" + m.getName());
                addParameters(fieldPUT, m.getParameterTypes(), funcParamsPUT);
                addMethod(fieldPUT, m.getName(), func2actionMapPUT_rev);
            }
            String fieldGET = func2actionMapGET.get(m.getName());
            if (fieldGET != null) {
                addParameters(fieldGET, m.getParameterTypes(), funcParamsGET);
                addMethod(fieldGET, m.getName(), func2actionMapGET_rev);
            }
            String fieldPOST = func2actionMapPOST.get(m.getName());
            if (fieldPOST != null) {
                addParameters(fieldPOST, m.getParameterTypes(), funcParamsPOST);
                addMethod(fieldPOST, m.getName(), func2actionMapPOST_rev);
            }
            String fieldDELETE = func2actionMapDELETE.get(m.getName());
            if (fieldDELETE != null) {
                addParameters(fieldDELETE, m.getParameterTypes(), funcParamsDELETE);
                addMethod(fieldDELETE, m.getName(), func2actionMapDELETE_rev);
            }
        }
    }

    // view_edit actions - deprecated
    protected void createPUTActions(Class<?> processedEntity) throws NoSuchMethodException {
        ctr = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, DetailDepth.class});
        EntityProviderMethodStore epms = entityProviderManager.getEntityProviderMethodStore();
        // scan for methods;
        Method[] CommunityMethods = processedEntity.getMethods();

        for (Method m : CommunityMethods) {
            log.info("====PUT Analyzed method " + m.getName());
        }
        for (Method m : CommunityMethods) {
            String field = func2actionMapPUT.get(m.getName());
            if (field != null) {
                log.info("===PUT Field found " + field);
                //CustomAction locCA = new CustomAction(field, EntityView.VIEW_EDIT, "putAction");

                // try {
                //locCA.setMethod(this.getClass().getMethod("putAction", new Class<?>[]{EntityReference.class, EntityView.class}));
                //epms.addCustomAction(getEntityPrefix(), locCA);
                addParameters(field, m.getParameterTypes(), funcParamsPUT);
                addMethod(field, m.getName(), func2actionMapPUT_rev);
                // } catch (NoSuchMethodException ex) {
                //     ex.printStackTrace();
                // }
            }
        }

        log.info("::::::::::");
        for (String key : funcParamsPUT.keySet()) {
            log.info("key " + key);
            Class<?>[] kl = funcParamsPUT.get(key);
            for (Class<?> k : kl) {
                log.info(k.getName());
            }
        }

    }

    // view_show actions - deprecated
    protected void createActions(Class<?> processedEntity) throws NoSuchMethodException {
        ctr = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, DetailDepth.class});
        EntityProviderMethodStore epms = entityProviderManager.getEntityProviderMethodStore();
        // scan for methods;
        Method[] CommunityMethods = processedEntity.getMethods();

        for (Method m : CommunityMethods) {
            log.info("====Analyzed method " + m.getName());
        }
        for (Method m : CommunityMethods) {
            String field = func2actionMapGET.get(m.getName());
            if (field != null) {
                log.info("===Field found " + field);
                //  CustomAction locCA = new CustomAction(field, EntityView.VIEW_SHOW, "testAction");

                //try {
                //  locCA.setMethod(this.getClass().getMethod("testAction", new Class<?>[]{EntityReference.class, EntityView.class}));
                //  epms.addCustomAction(getEntityPrefix(), locCA);
                addParameters(field, m.getParameterTypes(), funcParamsGET);
                addMethod(field, m.getName(), func2actionMapGET_rev);
                //} catch (NoSuchMethodException ex) {
                //    ex.printStackTrace();
                // }
            }
        }
    }

    // next two actions for put
    public Object putAction() {
        return new ActionReturn(new EntityData("2", "resField", "hotresult"), (String) null);
    }

    public Object putAction(EntityReference reference, EntityView view) throws SQLException, RecentSubmissionsException {
        String input = "";
        try {
            input = readIStoString(requestGetter.getRequest().getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Map<String, Object> decodedInput = new HashMap<String, Object>();
        EntityEncodingManager em = new EntityEncodingManager(null, null);
        decodedInput = em.decodeData(input, Formats.JSON); //TODO other formats

        for (String key : decodedInput.keySet()) {
            log.info("key - " + decodedInput.get(key));
        }

        log.info("PUT INPUT " + input);
        //String result = "none";
        Object result = new String("");
        String resField = "nofield";


        if (view.getPathSegments().length > 1) {
            String function = getMethod(view.getPathSegment(2), func2actionMapPUT_rev);
            log.info("working on function " + function);
            if (function != null) {
                resField = func2actionMapPUT.get(view.getPathSegment(2));
                log.info("resfield " + resField);
                final Context context = context();

                log.info("refreshing done,reference " + reference.getId());
                //if (entityExists(reference.getId())) {
                //  CommunityEntity CE = new CommunityEntity(reference.getId(), context);
                Object CE = new Object();
                try {
                    CE = ctr.newInstance(reference.getId(), context);
                } catch (Exception ex) {
                    log.info("ne valja");
                }
//
//                try{ log.info("parameters found");
//                Class<?>[] kl = funcParamsPUT.get(view.getPathSegment(2));
//                for (Class<?> k : kl) {
//                    log.info(k.getName());
//                }} catch (Exception ex) {ex.printStackTrace(); }


                // ova metoda ovaj sistem za uzimanje parametara je ispravan
                try {
                    Method method = CE.getClass().getMethod(function, funcParamsPUT.get(view.getPathSegment(2)));
                    //Method method = CE.getClass().getMethod(function, new Class<?>[]{EntityView.class, Map.class, Context.class});
                    result = method.invoke(CE, view, decodedInput, context); // TODO more flexible for other param types
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    complete(context);
                } catch (NullPointerException ex) {
                    // context already closed, ok
                }
            }
        }
        return new ActionReturn(new EntityData(reference.toString(), "resField", result), (String) null);
    }

    // the next two actions for view
    public Object testAction() {
        return new ActionReturn(new EntityData("2", "resField", "hotresult"), (String) null);
    }

    public void setRequestStorage(RequestStorage rStor) {
        this.requestStore = rStor;
    }

    public Object testAction(EntityReference reference, EntityView view) throws SQLException, RecentSubmissionsException {
        //String result = "none";
        Object result = new String("");
        String resField = "nofield";
        for (String segment : view.getPathSegments()) {
            log.info(segment);
        }
        log.info("areferenceee " + reference.getId());

        if (view.getPathSegments().length > 1) {
            String function = getMethod(view.getPathSegment(2), func2actionMapGET_rev);
            log.info("working on function " + function);
            if (function != null) {
                resField = func2actionMapGET.get(view.getPathSegment(2));
                log.info("resfield " + resField);
                final Context context = context();
                log.info("refreshing done,reference " + reference.getId());
                //if (entityExists(reference.getId())) {
                //  CommunityEntity CE = new CommunityEntity(reference.getId(), context);
                Object CE = new Object();
                try {
                    CE = ctr.newInstance(reference.getId(), context);
                } catch (Exception ex) {
                    log.info("nevalja");
                }
                try {
                    Method method = CE.getClass().getMethod(function);
                    result = method.invoke(CE);
                    log.info("result " + result);
                } catch (Exception ex) {
                    log.info("line177");
                    ex.printStackTrace();
                }


                try {
                    complete(context);
                } catch (NullPointerException ex) {
                    // context already close, ok
                }
            }
        }
        return new ActionReturn(new EntityData(reference.toString(), "resField", result), (String) null);
    }
    protected EntityProviderManager entityProviderManager;

    public void setEntityProviderManager(EntityProviderManager entityProviderManager) {
        this.entityProviderManager = entityProviderManager;


    }

    public void init() throws Exception {
        entityProviderManager.registerEntityProvider(this);


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

    public String readIStoString(ServletInputStream is) throws IOException {
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

    public String readIStoString(InputStream is) throws IOException {
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

        //    log.info("aaaaContent length = " + req.getContentLength());
        String input = "";
//        try {
//            ServletInputStream SI = req.getInputStream();
//            int i = SI.read();
//            while (i != -1) {
//                System.out.print((char) i);
//                i = SI.read();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

//        try {
//            input = readIStoString(req.getInputStream());
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }


//        log.info("THIS IS INPUT " + input);

//        while (req.getAttributeNames().hasMoreElements()) {
//            log.info("attr: " + req.getAttributeNames().nextElement());
//        }


        //      log.info("reference " + view.getEntityReference().getId());

//        while (req.getHeaderNames().hasMoreElements()) {
//            log.info("head: " + req.getHeaderNames().nextElement());
//        }
//
//        while (req.getParameterNames().hasMoreElements()) {
//            log.info("para: " + req.getParameterNames().nextElement());
//        }
        log.info(userInfo() + "starting to write for collection adding");
        Map<String, Object> daa = new HashMap<String, Object>();
//        EntityEncodingManager em = new EntityEncodingManager(null, null);
//        daa = em.decodeData(input, Formats.JSON);
//        log.info("OLD REQINPUT ");
//        for (String key : reqInput.keySet()) {
//            log.info(reqInput.get(key));
//        }
//        reqInput = em.decodeData(input, Formats.JSON);
//        log.info("NEW REQINPUT ");
//        for (String key : reqInput.keySet()) {
//            log.info(reqInput.get(key));
//        }
//        log.info("(_)_) daa" + daa.get("cid"));
//        String colid = "";
//        if (daa.get("cid") != null) {
//            colid = daa.get("cid").toString();
//        }
//        if (!colid.equals("")) {
//            Context context;
//            try {
//                context = new Context();
//            } catch (SQLException ex) {
//                throw new EntityException("Internal server error", "SQL error", 500);
//            }
//            refreshParams(context);
//
//            log.info(".adding collection to community." + colid + "int" + Integer.parseInt(colid));
//            try {
//                Collection col = Collection.find(context, Integer.parseInt(colid));
//                Community com = Community.find(context, Integer.parseInt(view.getEntityReference().getId()));
//                if ((com != null) && (col != null)) {
//                    com.addCollection(col);
//                } else {
//                    throw new EntityException("Entity not found", "Not found", 404);
//                }
//                //com.createCollection();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                throw new EntityException("111111Internal server error", "SQL error", 500);
//            } catch (AuthorizeException ex) {
//                ex.printStackTrace();
//            }
//
//
//            try {
//                removeConn(context);
//            } catch (NullPointerException ex) {
//                // context already close, ok
//            }
//        }


        //       Map<Object, Object> para = req.getParameterMap();
        //       for (Object key : para.keySet()) {
        //           log.info("~~~~~ key " + key + " val " + para.get(key));
        //       }

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
            log.info("is+= " + IS);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Map<String, Object> decodedInput = new HashMap<String, Object>();
        EntityEncodingManager em = new EntityEncodingManager(null, null);
        if (format.equals("xml")) {
            decodedInput = em.decodeData(IS, Formats.XML); //TODO other formats
        } else {
            decodedInput = em.decodeData(IS, Formats.JSON); //TODO other formats
        }


        log.info("== translate formated data called");
        log.info("got: \n" + IS + "\ndecoded " + decodedInput);
        return decodedInput;
    }

    /**
     * Remove items from list in order to display only requested items
     * (according to _start, _limit etc.)
     * @param entities
     */
    public void removeTrailing(List<?> entities) {
        final PaginationParameters parameters = new PaginationParameters(requestStore);
        if ((parameters.getStart() > 0) && (parameters.getStart() < entities.size())) {
            for (int x = 0; x
                    < parameters.getStart(); x++) {
                entities.remove(x);
            }
        }
        if (parameters.getPerpage() > 0) {
            entities.subList(0, parameters.getPage() * parameters.getPerpage()).clear();
        }
        if ((parameters.getLimit() > 0) && entities.size() > parameters.getLimit()) {
            entities.subList(parameters.getLimit(), entities.size()).clear();
        }
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

    public void setRequestGetter(RequestGetter requestGetter) {
        this.requestGetter = requestGetter;
    }

    public String[] getHandledOutputFormats() {
        return new String[]{Formats.JSON, Formats.XML, Formats.FORM, Formats.ATOM};

    }

    public void addParameters(String function, Class<?>[] parameters, Map<String, Class<?>[]> mappings_parameters) {
        mappings_parameters.put(function, parameters);
    }

    public Class<?>[] getParameters(String function, Map<String, Class<?>[]> mappings_parameters) {
        return mappings_parameters.get(function);
    }

    public void addMethod(String field, String function, Map<String, String> mappings_rev) {
        mappings_rev.put(field, function);
    }

    public String getMethod(String field, Map<String, String> mappings_rev) {
        return mappings_rev.get(field);
    }

    public Object getEntity(EntityReference reference) {
        String segments[] = {};

        log.info("Abstract get entity");
        if (requestStore.getStoredValue("pathInfo") != null) {
            segments = requestStore.getStoredValue("pathInfo").toString().split("/", 10);
        }

        if (segments[3].lastIndexOf(".") > 0) {
            segments[3] = segments[3].substring(0, segments[3].lastIndexOf("."));
        }

        if (func2actionMapGET_rev.containsKey(segments[3])) {
            Object result;
            String function = getMethod(segments[3], func2actionMapGET_rev);
            final Context context = context();

            Object CE = new Object();
            try {

                CE = entityConstructor.newInstance(reference.getId(), context, 1, DetailDepthParameters.build(requestStore).getDepth());
            } catch (Exception ex) {
                throw new EntityException("Internal server error", "Cannot create entity", 500);
            }

            try {
                Method method = CE.getClass().getMethod(function, new Class<?>[]{});
                //Method method = CE.getClass().getMethod(function, new Class<?>[]{EntityView.class, Map.class, Context.class});
                result = method.invoke(CE); // TODO more flexible for other param types
            } catch (Exception ex) {
                throw new EntityException("Internal server error", "Cannot call method " + function, 500);
            }

            try {
                complete(context);
            } catch (NullPointerException ex) {
                // context already closed, ok
            }
            return result;
        } else {
            throw new EntityException("Bad request", "Method not supported " + segments[3], 400);
        }
    }

    public void deleteEntity(EntityReference ref, Map<String, Object> params) {
        String segments[] = {};
        String action = "";
        Map<String, Object> inputVar = new HashMap<String, Object>();
        log.info("Delete called");

        if (requestStore.getStoredValue("pathInfo") != null) {
            segments = requestStore.getStoredValue("pathInfo").toString().split("/", 32);
        }


        for (int x = 0; x < segments.length; x++) {
            switch (x) {
                case 1:
                    inputVar.put("base", segments[x]);
                    break;
                case 2:
                    inputVar.put("id", segments[x]);
                    break;
                case 3:
                    inputVar.put("element", segments[x]);
                    break;
                case 4:
                    inputVar.put("eid", segments[x]);
                    break;
                case 5:
                    inputVar.put("trail", segments[x]);
                    break;
                default:
                    break;
            }
        }

        if (segments.length > 3) {
            action = segments[3];
        }

        if (func2actionMapDELETE_rev.containsKey(action)) {
            log.info("contains key called");
            String function = getMethod(action, func2actionMapDELETE_rev);
            if (function == null) {
                throw new EntityException("Bad request", "Method not supported - not defined", 400);
            }
            final Context context = context();

            Object CE = new Object();
            try {
                CE = entityConstructor.newInstance(ref.getId(), context, 1, DetailDepthParameters.build(requestStore).getDepth());
            } catch (Exception ex) {
                throw new EntityException("Internal server error", "Cannot create entity", 500);
            }

            try {
                Method method = CE.getClass().getMethod(function, funcParamsDELETE.get(action));
                method.invoke(CE, ref, inputVar, context); // TODO more flexible for other param types
            } catch (NoSuchMethodException nex) {
                log.info("nex");
            } catch (IllegalAccessException iex) {
                log.info("iex");
            } catch (InvocationTargetException itex) {
                EntityException eex = (EntityException) itex.getTargetException();
                if (itex.getTargetException().getClass().equals(EntityException.class)) {
                    throw eex;
                } else {
                    // TODO UNKNOWN ERROR - OTHER CLASS - REPORT
                }
            }

            try {
                complete(context);
            } catch (NullPointerException ex) {
                // context already closed, ok
            }
        } else if (action.equals("")) {
            log.info("action equal");
            String function = getMethod(action, func2actionMapDELETE_rev);
            if (function == null) {
                throw new EntityException("Bad request", "Method not supported - not defined", 400);
            }
            final Context context = context();

            Object CE = new Object();
            try {
                CE = entityConstructor.newInstance(ref.getId(), context, 1, DetailDepthParameters.build(requestStore).getDepth());
            } catch (Exception ex) {
                throw new EntityException("Internal server error", "Cannot create entity", 500);
            }

            try {
                Method method = CE.getClass().getMethod(function, funcParamsDELETE.get(action));
                method.invoke(CE, ref, inputVar, context); // TODO more flexible for other param types
            } catch (Exception ex) {
                throw new EntityException("Internal server error", "Cannot call method " + function, 500);
            }

            try {
                complete(context);
            } catch (NullPointerException ex) {
                // context already closed, ok
            }
        } else {
            throw new EntityException("Bad request", "Method not supported " + action, 400);
        }
    }

    public void updateEntity(EntityReference ref, Object entity, Map<String, Object> params) {
        log.info("update called");
        Map<String, Object> inputVar = (HashMap) entity;
        String segments[] = {};
        if (params.containsKey("pathInfo")) {
            segments = params.get("pathInfo").toString().split("/", 10);
        }
        //log.info("calling update");

        if (segments.length > 3) {


            if (segments[3].lastIndexOf(".") > 0) {
                segments[3] = segments[3].substring(0, segments[3].lastIndexOf("."));
            }

            log.info("length > 3" + segments[2] + ".." + segments[3]);

            if (func2actionMapPUT_rev.containsKey(segments[3])) {
                //log.info("got in");

                String function = getMethod(segments[3], func2actionMapPUT_rev);
                final Context context = context();

                Object CE = new Object();

                try {
                    CE = entityConstructor.newInstance(ref.getId(), context, 1, DetailDepthParameters.build(requestStore).getDepth());
                } catch (Exception ex) {
                    throw new EntityException("Internal server error", "Cannot create entity", 500);
                }

                try {
                    Method method = CE.getClass().getMethod(function, funcParamsPUT.get(segments[3]));
                    method.invoke(CE, ref, inputVar, context); // TODO more flexible for other param types
                    // log.info("invoked");
                } catch (InvocationTargetException ex) {
                    if (ex.getCause() != null) {
                        throw (RuntimeException) ex.getCause();
                    } else {
                        throw new EntityException("Internal server error", "Unknown error", 500);
                    }
                } catch (NoSuchMethodException ex) {
                    throw new EntityException("Not found", "Meethod not supported " + segments[3], 404);
                } catch (IllegalAccessException ex) {
                    throw new EntityException("Internal server error", "Cannot call method " + function, 500);
                }
                try {
                    complete(context);

                } catch (NullPointerException ex) {
                    // context already closed, ok
                }
            } else {
                //  log.info(segments[0] + ":" + segments[1] + ":" + segments[2] + ":" + segments[3]);
                for (String key : func2actionMapPUT_rev.keySet()) {
                    log.info(key + " " + func2actionMapPUT_rev.get(key));
                }
//
                for (String key : func2actionMapPUT.keySet()) {
                    log.info(key + " " + func2actionMapPUT.get(key));
                }
                throw new EntityException("Bad request", "Maethod not supported " + segments[3], 400);
            }
        }
    }

    public String createEntity(EntityReference ref, Object entity, Map<String, Object> params) {
        log.info("creating");
        String result = "x";
        Map<String, Object> inputVar = (HashMap) entity;
        String action = "";
        String function = "";
        String[] mandatory_params = {};
        try {
            inputVar.get("action").getClass().getName();
        } catch (NullPointerException ex) {
            throw new EntityException("Bad request", "Incomplete request [action]", 400);
        }
        try {
            action = (String) inputVar.get("action");
        } catch (ClassCastException ex2) {
            throw new EntityException("Bad request", "Incomplete request [action2]", 400);
        }


        if (func2actionMapPOST_rev.get(action) != null) {
            log.info("found " + func2actionMapPOST_rev.get("create"));
            function = func2actionMapPOST_rev.get(action);
            mandatory_params = inputParamsPOST.get(function);
        }

        for (String param : mandatory_params) {
            if (inputVar.get(param) == null) {
                throw new EntityException("Bad request", "Incomplete request [mandatory param]", 400);
            }
        }

        Context context = context();

        Object CE = new Object();
        log.info("id izabran " + ref.getId());
        try {
            CE = entityConstructor.newInstance(ref.getId(), context, 1, DetailDepthParameters.build(requestStore).getDepth());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new EntityException("Internal server error", "aCannot create entity", 500);
        }

        try {
            Method method = CE.getClass().getMethod(function, funcParamsPOST.get(action));
            result = (String) method.invoke(CE, ref, inputVar, context); // TODO more flexible for other param types
            // log.info("invoked");
        } catch (InvocationTargetException ex) {
            if (ex.getCause() != null) {
                throw (RuntimeException) ex.getCause();
            } else {
                throw new EntityException("Internal server error", "Unknown error", 500);
            }
        } catch (NoSuchMethodException ex) {
            throw new EntityException("Not found", "Method not supported ", 404);
        } catch (IllegalAccessException ex) {
            throw new EntityException("Internal server error", "Cannot call method " + function, 500);
        }
        try {
            complete(context);

        } catch (NullPointerException ex) {
            // context already closed, ok
        }



        return result;



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
}
