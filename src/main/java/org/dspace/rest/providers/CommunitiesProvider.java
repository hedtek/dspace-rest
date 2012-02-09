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
import org.dspace.content.Community;
import org.dspace.core.Context;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.entities.CommunityEntity;
import org.dspace.rest.entities.CommunityEntityId;
import org.dspace.rest.params.DetailDepthParameters;
import org.dspace.rest.params.EntityBuildParameters;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;

/**
 * Provides interface for access to community entities
 * @see CommunityEntityId
 * @see CommunityEntity
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CommunitiesProvider extends AbstractBindingProvider  implements CoreEntityProvider {

    private static Logger log = Logger.getLogger(CommunitiesProvider.class);
    
    public CommunitiesProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager, Binder.forCommunities());
    }

    public String getEntityPrefix() {
        return "communities";
    }

    public boolean entityExists(String id) {
        if (id.equals(":ID:")) {
            return true;
        }

        final Context context = context();
        boolean result = false;
        try {
            Community comm = Community.find(context, Integer.parseInt(id));
            if (comm != null) {
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
            // if there is complete entity requested then continue with other checks

            // sample entity
            if (reference.getId().equals(":ID:")) {
                return new CommunityEntity();
            }

            if (reference.getId() == null) {
                return new CommunityEntity();
            }

            final Context context = context();
            try {
                if (entityExists(reference.getId())) {
                    try {
                        // return just entity containing id or full info
                        if (EntityBuildParameters.build(requestStore).isIdOnly()) {
                            return new CommunityEntityId(reference.getId(), context);
                        } else {
                            return new CommunityEntity(reference.getId(), context, 1, DetailDepthParameters.build(requestStore).getDepth());
                        }
                    } catch (SQLException ex) {
                        throw new IllegalArgumentException("Invalid id:" + reference.getId());
                    } catch (NullPointerException ne) {
                        ne.printStackTrace();
                    }
                }
                throw new IllegalArgumentException("Invalid id:" + reference.getId());
                
            } finally {

                complete(context);
            }
        }
    }

    public List<?> getEntities(EntityReference ref, Search search) {
        return getAllCommunities();
    }

    
    private List<?> getAllCommunities() {
        final Context context = context();
        try {
            final List<Object> entities = new ArrayList<Object>();
            EntityBuildParameters build = EntityBuildParameters.build(requestStore);
            
            final Community[] communities = build.isTopLevelOnly() ? Community.findAllTop(context) : Community.findAll(context);
            for (Community c : communities) {
                entities.add(build.isIdOnly() ? new CommunityEntityId(c) : new CommunityEntity(c, 1, DetailDepthParameters.build(requestStore).getDepth()));
            }

            sort(entities);
            removeTrailing(entities);
            return entities;
            
        } catch (SQLException cause) {
            throw new SQLFailureEntityException(Operation.GET_COMMUNITIES, cause);
        } finally {
            complete(context);
        }
    }

    /**
     * Prepare sample entity
     * @return
     */
    public Object getSampleEntity() {
        return null;
    }
}
