/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.providers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sakaiproject.entitybus.EntityView;
import org.sakaiproject.entitybus.entityprovider.EntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.capabilities.ActionsExecutable;
import org.sakaiproject.entitybus.entityprovider.capabilities.CollectionResolvable;
import org.sakaiproject.entitybus.entityprovider.capabilities.Describeable;
import org.sakaiproject.entitybus.entityprovider.capabilities.Outputable;
import org.sakaiproject.entitybus.entityprovider.capabilities.Redirectable;
import org.sakaiproject.entitybus.entityprovider.capabilities.RequestInterceptor;
import org.sakaiproject.entitybus.entityprovider.capabilities.RequestStorable;
import org.sakaiproject.entitybus.entityprovider.capabilities.Resolvable;
import org.sakaiproject.entitybus.entityprovider.extension.Formats;
import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

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
public abstract class AbstractBaseProvider implements EntityProvider, Resolvable, CollectionResolvable, Outputable, Describeable, ActionsExecutable, Redirectable, RequestStorable, RequestInterceptor {

    // query parameters used in subclasses
    protected RequestStorage requestStore;

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
    }

    /**
     * Called after processing of request
     * Not relevant in this case but implementation must be available
     * @param view
     * @param req
     * @param res
     */
    public void after(EntityView view, HttpServletRequest req, HttpServletResponse res) {}

    public String[] getHandledInputFormats() {
        return new String[]{Formats.HTML, Formats.XML, Formats.JSON};
    }

    public String[] getHandledOutputFormats() {
        return new String[]{Formats.JSON, Formats.XML, Formats.FORM, Formats.ATOM};

    }
    
    /**
     * Unsupported
     * @return null
     */
    public final Object getSampleEntity() {
        return null;
    }
}
