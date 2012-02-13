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
import org.dspace.rest.data.DSpace;
import org.dspace.rest.diagnose.EntityNotFoundException;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.entities.CommunityEntity;
import org.dspace.rest.entities.CommunityEntityId;
import org.dspace.rest.entities.DetailDepth;
import org.dspace.rest.params.Parameters;
import org.dspace.rest.params.Route;
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
public class CommunitiesProvider extends AbstractBaseProvider  implements CoreEntityProvider {

    private static Logger log = Logger.getLogger(CommunitiesProvider.class);
    private final Binder binder;
    
    public CommunitiesProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        binder = Binder.forCommunities();
    }

    public String getEntityPrefix() {
        return "communities";
    }

    public boolean entityExists(String id) {
        if (id.equals(":ID:")) {
            return true;
        }

        final Context context = DSpace.context();
        try {
            return Community.find(context, Integer.parseInt(id)) != null;
        } catch (SQLException ex) {
            log.debug("Failed to find community. Assuming that this means it doesn't exist.", ex);
            return false;
        } finally {
            DSpace.complete(context);
        }
    }

    @Override
    public Object getEntity(EntityReference reference) {
        final String id = reference.getId();
        if (id == null || ":ID:".equals(id)) {
            return getSampleEntity();
        }

        return entity(id);
    }

    private Object entity(final String id) {
        final Operation operation = Operation.GET_COMMUNITIES;
        final Context context = DSpace.context();
        try {
            final Parameters parameters = new Parameters(requestStore);
            final Route route = new Route(requestStore);
            if (route.isAttribute()) {
                log.debug("Using generic entity binding");
                return binder.resolve(id, route, parameters, context);
            } else {
                if (entityExists(id)) {
                    // return just entity containing id or full info
                    if (parameters.getEntityBuild().isIdOnly()) {
                        return new CommunityEntityId(id, context);
                    } else {
                        return new CommunityEntity(id, context, 1, parameters.getDetailDepth().getDepth());
                    }
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

    public List<?> getEntities(EntityReference ref, Search search) {
        return getAllCommunities();
    }

    
    private List<?> getAllCommunities() {
        final Parameters parameters = new Parameters(requestStore);
        final Context context = DSpace.context();
        try {
            final List<Object> entities = new ArrayList<Object>();
            
            final Community[] communities = parameters.getEntityBuild().isTopLevelOnly() ? Community.findAllTop(context) : Community.findAll(context);
            for (Community c : communities) {
                entities.add(parameters.getEntityBuild().isIdOnly() ? new CommunityEntityId(c) : new CommunityEntity(c, 1, DetailDepth.FOR_ALL_INDEX));
            }

            parameters.sort(entities);
            parameters.removeTrailing(entities);
            return entities;
            
        } catch (SQLException cause) {
            throw new SQLFailureEntityException(Operation.GET_COMMUNITIES, cause);
        } finally {
            DSpace.complete(context);
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
