package org.dspace.rest.providers;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.dspace.rest.params.DetailDepthParameters;
import org.dspace.rest.params.Parameters;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;

public abstract class AbstractBindingProvider extends AbstractBaseProvider {
    private static Logger log = Logger.getLogger(AbstractBaseProvider.class);

    private final Binder binder;

    /**
     * Handle registration of EntityProvider
     * @param entityProviderManager
     * @throws java.sql.SQLException
     */
    public AbstractBindingProvider(EntityProviderManager entityProviderManager, Binder binder) throws SQLException {
        super(entityProviderManager);
        this.binder = binder;
    }

    public Object getEntity(EntityReference reference) {
        final String id = reference.getId();
        return resolve(id);
    }

    private Object resolve(final String id) {
        final Parameters parameters = new Parameters(requestStore);
        final Object pathInfo = requestStore.getStoredValue("pathInfo");
        
        final Context context = context();
        try {
            String segments[] = {};
            log.debug("Using generic entity binding");
            if (pathInfo != null) {
                segments = pathInfo.toString().split("/", 10);
            }
            if (segments[3].lastIndexOf(".") > 0) {
                segments[3] = segments[3].substring(0, segments[3].lastIndexOf("."));
            }
            return binder.resolve(id, segments, parameters, context);
        } finally {
            complete(context);
        }
    }
    
}
