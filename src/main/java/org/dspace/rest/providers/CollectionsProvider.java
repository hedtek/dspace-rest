/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package org.dspace.rest.providers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.content.Collection;
import org.dspace.core.Context;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.entities.CollectionEntity;
import org.dspace.rest.entities.CollectionEntityId;
import org.dspace.rest.entities.DetailDepth;
import org.dspace.rest.params.DetailDepthParameters;
import org.dspace.rest.params.EntityBuildParameters;
import org.dspace.rest.params.Parameters;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;

/**
 * Provides interface for access to collections entities
 * @see CollectionEntity
 * @see CollectionEntityId
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CollectionsProvider extends AbstractBindingProvider implements CoreEntityProvider {

    private static Logger log = Logger.getLogger(CollectionsProvider.class);

    public CollectionsProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager, Binder.forCollections());
    }

    public String getEntityPrefix() {
        return "collections";
    }

    public boolean entityExists(String id) {
        // sample entity
        if (id.equals(":ID:")) {
            return true;
        }
        
        final Context context = context();
        boolean result = false;
        try {
            final Collection col = Collection.find(context, Integer.parseInt(id));
            if (col != null) {
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

    /**
     * Returns information about particular entity
     * @param reference
     * @return
     */
    @Override
    public Object getEntity(EntityReference reference) {
        String segments[] = {};

        if (requestStore.getStoredValue("pathInfo") != null) {
            segments = requestStore.getStoredValue("pathInfo").toString().split("/", 10);
        }

        // first check if there is sub-field requested
        // if so then invoke appropriate method inside of entity
        if (segments.length > 3) {
            return super.getEntity(reference);
        } else {

            final Context context = context();

            try {
                // sample entity
                if (reference.getId().equals(":ID:")) {
                    return new CollectionEntity();
                }

                if (reference.getId() == null) {
                    return new CollectionEntity();
                }

                if (entityExists(reference.getId())) {
                    try {
                        // return basic entity or full info
                        if (EntityBuildParameters.build(requestStore).isIdOnly()) {
                            return new CollectionEntityId(reference.getId(), context);
                        } else {
                            return new CollectionEntity(reference.getId(), context, 1, DetailDepthParameters.build(requestStore).getDepth());
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
    }

    /**
     * List all collection in the system, sort and format if requested
     * @param ref
     * @param search
     * @return
     */
    public List<?> getEntities(EntityReference ref, Search search) {
        return getAllCollections();
    }

    private List<?> getAllCollections() {
        Operation operation = Operation.GET_COLLECTIONS;
        final Parameters parameters = new Parameters(requestStore);
        final Context context = context();
        try {
            
            final List<Object> entities = new ArrayList<Object>();
            final Collection[] collections = Collection.findAll(context);
            final boolean idOnly = parameters.getEntityBuild().isIdOnly();
            for (Collection c : collections) {
                entities.add(idOnly ? new CollectionEntityId(c) : new CollectionEntity(c, 1, DetailDepth.FOR_ALL_INDEX));
            }
            
            parameters.sort(entities);
            parameters.removeTrailing(entities);

            return entities;
            
        } catch (SQLException cause) {
            throw new SQLFailureEntityException(operation, cause);
        } finally {
            complete(context);
        }
    }
    
    /**
     * Here is sample collection entity defined
     */
    public Object getSampleEntity() {
        return new CollectionEntity();
    }
}
