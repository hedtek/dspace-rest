/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.providers;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.core.Context;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.entities.DetailDepth;
import org.dspace.rest.entities.ItemEntity;
import org.dspace.rest.entities.ItemEntityId;
import org.dspace.rest.params.DetailDepthParameters;
import org.dspace.rest.params.EntityBuildParameters;
import org.dspace.rest.params.Parameters;
import org.dspace.rest.params.Routes;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;

/**
 * Provides interface for access to item entities
 * @see ItemEntityId
 * @see ItemEntity
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class ItemsProvider extends AbstractBaseProvider  implements CoreEntityProvider {

    private final static Logger log = Logger.getLogger(ItemsProvider.class);
    private final Binder binder;

    /**
     * Constructor handles registration of provider
     * @param entityProviderManager
     * @throws java.sql.SQLException
     */
    public ItemsProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        binder = Binder.forItem();
    }

    // this is the prefix where provider is registered (URL path)
    public String getEntityPrefix() {
        return "items";
    }

    public boolean entityExists(String id) {
        // sample entity
        if (id.equals(":ID:")) {
            return true;
        }

        final Context context = context();
        try {
            boolean result = false;
            final Item item = Item.find(context, Integer.parseInt(id));
            if (item != null) {
                result = true;
            }
            return result;
        } catch (SQLException ex) {
            log.debug("Failed to find community. Assuming that this means it doesn't exist.", ex);
            return false;
        } finally {
            complete(context);
        }
    }

    public Object getEntity(EntityReference reference) {
        String segments[] = {};

        if (requestStore.getStoredValue("pathInfo") != null) {
            segments = requestStore.getStoredValue("pathInfo").toString().split("/", 10);
        }

        // first check if there is sub-field requested
        // if so then invoke appropriate method inside of entity
        if (segments.length > 3) {
            log.debug("Using generic entity binding");
            final Parameters parameters = new Parameters(requestStore);
            final Routes routes = new Routes(requestStore);
            
            final Context context1 = context();
            try {
                return binder.resolve(reference.getId(), routes, parameters, context1);
            } finally {
                complete(context1);
            }
        }

        Context context = context();
        try {

            // sample entity
            if (reference.getId().equals(":ID:")) {
                return new ItemEntity();
            }

            if (entityExists(reference.getId())) {
                try {

                    // return basic or full info, according to requirements
                    if (EntityBuildParameters.build(requestStore).isIdOnly()) {
                        return new ItemEntityId(reference.getId(), context);
                    } else {
                        return new ItemEntity(reference.getId(), context, 1, DetailDepthParameters.build(requestStore).getDepth());
                    }
                } catch (SQLException ex) {
                    throw new IllegalArgumentException("Invalid id:" + reference.getId());
                }
            }

            throw new IllegalArgumentException("Invalid id:" + reference.getId());
        } finally {
            complete(context);
        }
    }

    public List<?> getEntities(EntityReference ref, Search search) {
        return getAllItems();
    }

    private List<?> getAllItems() {
        final Context context = context();
        try {
            final Parameters parameters = new Parameters(requestStore);
            
            final ItemIterator items = Item.findAll(context);
            final List<Object> entities = parameters.itemBuilder(DetailDepth.FOR_ALL_INDEX).build(items);

            parameters.sort(entities);
            parameters.removeTrailing(entities);
            return entities;
        } catch (SQLException cause) {
            throw new SQLFailureEntityException(Operation.GET_ITEMS, cause);

        } finally {
            complete(context);
        }
    }

    /**
     * Return sample entity
     * @return
     */
    public Object getSampleEntity() {
        return new ItemEntity();
    }
}

