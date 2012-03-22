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
import org.dspace.content.Community;
import org.dspace.core.Context;
import org.dspace.rest.data.DSpace;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.community.Communities;
import org.dspace.rest.data.community.CommunityEntity;
import org.dspace.rest.data.community.CommunityEntityId;
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
 * Provides interface for access to community entities
 * @see CommunityEntityId
 * @see CommunityEntity
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CommunitiesProvider extends AbstractBaseProvider  implements CoreEntityProvider {

    private static class CommunitiesBinder extends Binder {
        @Override
        protected Object value(String id, Parameters parameters, Context context,
                String attributeSegment) throws SQLException {
            final DetailDepth depth = parameters.getDetailDepth().getDepth();
            if ("id".equals(attributeSegment)) {
                return community(id, context, depth).getId();
            } else if ("name".equals(attributeSegment)) {
                return community(id, context, depth).getName();
            } else if ("countItems".equals(attributeSegment)) {
                return community(id, context, depth).getCountItems();
            } else if ("handle".equals(attributeSegment)) {
                return community(id, context, depth).getHandle();
            } else if ("type".equals(attributeSegment)) {
                return community(id, context, depth).getType();
            } else if ("collections".equals(attributeSegment)) {
                return community(id, context, depth).getCollections();
            } else if ("canedit".equals(attributeSegment)) {
                return community(id, context, depth).getCanEdit();
            } else if ("anchestor".equals(attributeSegment)) {
                return community(id, context, depth).getParentCommunity();
            } else if ("children".equals(attributeSegment)) {
                return community(id, context, depth).getSubCommunities();
            } else if ("recent".equals(attributeSegment)) {
                return community(id, context, depth).getRecentSubmissions();
            } else if ("shortDescription".equals(attributeSegment)) {
                return community(id, context, depth).getShortDescription();
            } else if ("copyrightText".equals(attributeSegment)) {
                return community(id, context, depth).getCopyrightText();
            } else if ("sidebarText".equals(attributeSegment)) {
                return community(id, context, depth).getSidebarText();
            } else if ("introductoryText".equals(attributeSegment)) {
                return community(id, context, depth).getIntroductoryText();
            } else if ("logo".equals(attributeSegment)) {
                return community(id, context, depth).getLogo();
            } else {
                return null;
            }
        }

        private CommunityEntity community(String id, Context context,
                final DetailDepth depth) throws SQLException {
            return new Communities(context).community(id, depth);
        }

        protected Operation operation() {
            return Operation.GET_COMMUNITIES;
        }
    }

    private static Logger log = Logger.getLogger(CommunitiesProvider.class);
    private final Binder binder;
    
    public CommunitiesProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        binder = new CommunitiesProvider.CommunitiesBinder();
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
                    return parameters.community(id, context);
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
        return getAllCommunities();
    }

    
    private List<Entity> getAllCommunities() {
        final Parameters parameters = new Parameters(requestStore);
        final Context context = DSpace.context();
        try {
            return parameters.communities(context);
            
        } catch (SQLException cause) {
            throw new SQLFailureEntityException(Operation.GET_COMMUNITIES, cause);
        } finally {
            DSpace.complete(context);
        }
    }
}
