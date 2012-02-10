package org.dspace.rest.providers;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.dspace.rest.params.Parameters;
import org.dspace.rest.params.Routes;
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

    public Object resolve(final String id) {
        log.debug("Using generic entity binding");
        final Parameters parameters = new Parameters(requestStore);
        final Routes routes = new Routes(requestStore);
        
        final Context context = context();
        try {
            return binder.resolve(id, routes, parameters, context);
        } finally {
            complete(context);
        }
    }
}
