/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.servlet;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.content.Bundle;
import org.dspace.core.ConfigurationManager;
import org.dspace.rest.data.bundle.BundleEntityId;
import org.dspace.rest.providers.AbstractBaseProvider;
import org.dspace.rest.providers.BitstreamProvider;
import org.dspace.rest.providers.CollectionsProvider;
import org.dspace.rest.providers.CommunitiesProvider;
import org.dspace.rest.providers.GroupProvider;
import org.dspace.rest.providers.HarvestProvider;
import org.dspace.rest.providers.ItemsProvider;
import org.dspace.rest.providers.SearchProvider;
import org.dspace.rest.providers.StatsProvider;
import org.dspace.rest.providers.UserProvider;
import org.sakaiproject.entitybus.EntityBrokerManager;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.impl.EntityBrokerCoreServiceManager;
import org.sakaiproject.entitybus.providers.EntityRequestHandler;
import org.sakaiproject.entitybus.rest.EntityBrokerRESTServiceManager;
import org.sakaiproject.entitybus.util.servlet.DirectServlet;

/**
 * Main class, here is started and initialized servlet, providers registered
 * @see BundleEntityId
 * @see Bundle
 * @author Bojan Suzic, bojan.suzic@gmail.com
 * Based on Aaron Zeckoski's SakaiProject.EntityBus
 */
public class DS16DirectServlet extends DirectServlet {

    private static final long serialVersionUID = 2L;
    private transient EntityBrokerCoreServiceManager entityBrokerCoreServiceManager;
    private transient EntityBrokerRESTServiceManager entityRESTServiceManager;
    private transient List<AbstractBaseProvider> entityProviders;

    /**
     * Starts up all the entity providers, new providers should be added
     * to the list
     * @param entityProviderManager the provider manager
     */
    protected void startProviders(EntityProviderManager entityProviderManager) throws java.sql.SQLException, NoSuchMethodException {
        String config = getServletContext().getInitParameter("dspace-config");

        // for dev testing only COMMENT IN WORKING ENVIRONMENT
        if (config.contains("dspace.dir")) {
            config = "/devel/dspace/config/dspace.cfg";
        }

        ConfigurationManager.loadConfig(config);
        this.entityProviders = new Vector<AbstractBaseProvider>();
        this.entityProviders.add(new BitstreamProvider(entityProviderManager));
        this.entityProviders.add(new CommunitiesProvider(entityProviderManager));
        this.entityProviders.add(new CollectionsProvider(entityProviderManager));
        this.entityProviders.add(new ItemsProvider(entityProviderManager));
        this.entityProviders.add(new StatsProvider(entityProviderManager));
        this.entityProviders.add(new UserProvider(entityProviderManager));
        this.entityProviders.add(new SearchProvider(entityProviderManager));
        this.entityProviders.add(new HarvestProvider(entityProviderManager));
        this.entityProviders.add(new GroupProvider(entityProviderManager));
    }

    @Override
    public EntityRequestHandler initializeEntityRequestHandler() {
        EntityRequestHandler erh;
        try {
            this.entityBrokerCoreServiceManager = new EntityBrokerCoreServiceManager();
            EntityBrokerManager ebm = this.entityBrokerCoreServiceManager.getEntityBrokerManager();

            // create the EB REST services
            this.entityRESTServiceManager = new EntityBrokerRESTServiceManager(ebm);

            erh = this.entityRESTServiceManager.getEntityRequestHandler();
            if (erh == null) {
                throw new RuntimeException("FAILED to load EB EntityRequestHandler");
            }

            EntityProviderManager epm = this.entityBrokerCoreServiceManager.getEntityProviderManager();

            // start the providers
            startProviders(epm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("FAILURE during init of direct servlet: " + e, e);
        }
        return erh;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (this.entityProviders != null) {
            for (AbstractBaseProvider provider : entityProviders) {
                if (provider != null) {
                    try {
                        provider.destroy();
                    } catch (Exception e) {
                        System.err.println("WARN Could not clean up provider (" + provider + ") on destroy: " + e);
                    }
                }
            }
            this.entityProviders.clear();
            this.entityProviders = null;
        }
        if (this.entityRESTServiceManager != null) {
            this.entityRESTServiceManager.destroy();
            this.entityRESTServiceManager = null;
        }
        if (this.entityBrokerCoreServiceManager != null) {
            this.entityBrokerCoreServiceManager.destroy();
            this.entityBrokerCoreServiceManager = null;
        }
    }

    @Override
    public String getCurrentLoggedInUserId() {
        return "tester";
    }

    @Override
    public void handleUserLogin(HttpServletRequest req, HttpServletResponse res, String path) {
        // login is implemented in AbstractBaseProvider, per request
    }    
}
