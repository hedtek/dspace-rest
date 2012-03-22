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
import org.dspace.core.Context;
import org.dspace.rest.data.DSpace;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.item.ItemEntity;
import org.dspace.rest.data.item.ItemEntityId;
import org.dspace.rest.data.item.Items;
import org.dspace.rest.diagnose.EntityNotFoundException;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.params.Parameters;
import org.dspace.rest.params.Route;
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

    public static class ItemBinder extends Binder{
    
        @Override
        protected Object value(String id, Parameters parameters,
                Context context, String attributeSegment) throws SQLException {
            if("metadata".equals(attributeSegment)) {
                return item(id, parameters, context).getMetadata();
            } else if("submitter".equals(attributeSegment)) {
                return item(id, parameters, context).getSubmitter();
            } else if("isArchived".equals(attributeSegment)) {
                return item(id, parameters, context).getIsArchived();
            } else if("isWithdrawn".equals(attributeSegment)) {
                return item(id, parameters, context).getIsWithdrawn();
            } else if("owningCollection".equals(attributeSegment)) {
                return item(id, parameters, context).getOwningCollection();
            } else if("lastModified".equals(attributeSegment)) {
                return item(id, parameters, context).getLastModified();
            } else if("collections".equals(attributeSegment)) {
                return item(id, parameters, context).getCollections();
            } else if("communities".equals(attributeSegment)) {
                return item(id, parameters, context).getCommunities();
            } else if("name".equals(attributeSegment)) {
                return item(id, parameters, context).getName();
            } else if("bitstreams".equals(attributeSegment)) {
                return item(id, parameters, context).getBitstreams();
            } else if("handle".equals(attributeSegment)) {
                return item(id, parameters, context).getHandle();
            } else if("canedit".equals(attributeSegment)) {
                return item(id, parameters, context).getCanEdit();
            } else if("id".equals(attributeSegment)) {
                return item(id, parameters, context).getId();
            } else if("type".equals(attributeSegment)) {
                return item(id, parameters, context).getType();
            } else if("bundles".equals(attributeSegment)) {
                return item(id, parameters, context).getBundles();
            } else {
                return null;
            }
        }

        private ItemEntity item(String uid, Parameters parameters,
                Context context) throws SQLException {
            return new Items(context).fetch(parameters.getDetailDepth().getDepth(), uid);
        }
    
        @Override
        protected Operation operation() {
            return Operation.GET_ITEMS;
        }
        
    }

    private final static Logger log = Logger.getLogger(ItemsProvider.class);
    private final Binder binder;

    /**
     * Constructor handles registration of provider
     * @param entityProviderManager
     * @throws java.sql.SQLException
     */
    public ItemsProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        binder = new ItemsProvider.ItemBinder();
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

        final Context context = DSpace.context();
        try {
            return Item.find(context, Integer.parseInt(id)) != null;
        } catch (SQLException ex) {
            log.debug("Failed to find item. Assuming that this means it doesn't exist.", ex);
            return false;
        } finally {
            DSpace.complete(context);
        }
    }

    public Object getEntity(EntityReference reference) {
        final String id = reference.getId();
        // sample entity
        if (id == null || ":ID:".equals(id)) {
            return getSampleEntity();
        }
        return entity(id);
    }

    private Object entity(final String id) {
        final Parameters parameters = new Parameters(requestStore);
        final Route route = new Route(requestStore);
        final Operation operation = Operation.GET_ITEMS;
        final Context context = DSpace.context();
        try {
            if (route.isAttribute()) {
                log.debug("Using generic entity binding");
                return binder.resolve(id, route, parameters, context);
            } else {
                if (entityExists(id)) {
                    return parameters.item(id, context);
                } else {
                    if (log.isDebugEnabled()) log.debug("Cannot find entity " + id);
                    throw new EntityNotFoundException(operation);
                }
            }
        } catch (SQLException cause) {
            if (log.isDebugEnabled()) log.debug("Cannot find entity " + id);
            throw new SQLFailureEntityException(operation, cause);

        } finally {
            DSpace.complete(context);
        }
    }

    public List<Entity> getEntities(EntityReference ref, Search search) {
        return get();
    }

    private List<Entity> get() {
        final Context context = DSpace.context();
        try {
            
            final Parameters parameters = new Parameters(requestStore);
            return parameters.items(context);
            
        } catch (SQLException cause) {
            throw new SQLFailureEntityException(Operation.GET_ITEMS, cause);

        } finally {
            DSpace.complete(context);
        }
    }
}

