/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.providers;

import org.sakaiproject.entitybus.entityprovider.EntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.dspace.core.Context;
import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

/**
 * Makes it easier to write {@link EntityProvider}s in webapps <br/>
 * A class to extend that gets rid of some of the redundant code that has
 * to be written over and over, causes this provider to be registered when it
 * is created and unregistered when it is destroyed
 *
 * @author Aaron Zeckoski (aaron@caret.cam.ac.uk)
 */
public abstract class AbstractRESTProvider implements EntityProvider {
    protected Context context;
    protected RequestStorage reqStor;
    boolean idOnly;

    public AbstractRESTProvider(EntityProviderManager entityProviderManager) {
        this.entityProviderManager = entityProviderManager;
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException("Unable to register the provider ("+this+"): " + e, e);
        }
        
        try {
            idOnly = reqStor.getStoredValue("idOnly").equals("true");
        } catch (NullPointerException ex) { idOnly = false; };
    }

    private EntityProviderManager entityProviderManager;
    public void setEntityProviderManager(EntityProviderManager entityProviderManager) {
        this.entityProviderManager = entityProviderManager;
    }

    public void init() throws Exception {
        entityProviderManager.registerEntityProvider(this);
    }

    public void destroy() throws Exception {
        entityProviderManager.unregisterEntityProvider(this);
    }

}
