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
import org.dspace.core.Context;
import org.dspace.eperson.Group;
import org.dspace.rest.data.DSpace;
import org.dspace.rest.diagnose.EntityNotFoundException;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.entities.GroupEntity;
import org.dspace.rest.entities.GroupEntityId;
import org.dspace.rest.params.Parameters;
import org.dspace.rest.params.Route;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;

/**
 * Provides interface for access to user info entities
 * @see GroupEntity
 * @see GroupEntityId
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class GroupProvider extends AbstractBaseProvider  implements CoreEntityProvider {

    public static class GroupBinder extends Binder{
    
        @Override
        protected Object value(String id, Parameters parameters,
                Context context, String attributeSegment) throws SQLException {
            
            if ("handle".equals(attributeSegment)) {
                return group(id, parameters, context).getHandle();
            } else if ("id".equals(attributeSegment)) {
                return group(id, parameters, context).getId();
            } else if ("isEmpty".equals(attributeSegment)) {
                return group(id, parameters, context).getIsEmpty();
            } else if ("members".equals(attributeSegment)) {
                return group(id, parameters, context).getMembers();
            } else if ("memberGroups".equals(attributeSegment)) {
                return group(id, parameters, context).getMemberGroups();
            } else if ("name".equals(attributeSegment)) {
                return group(id, parameters, context).getName();
            } else if ("type".equals(attributeSegment)) {
                return group(id, parameters, context).getType();
            } else {
                return null;
            }
        }

        private GroupEntity group(String id, Parameters parameters,
                Context context) throws SQLException {
            return new GroupEntity(id, context, parameters.getDetailDepth().getDepth());
        }
    
        @Override
        protected Operation operation() {
            return Operation.GET_GROUP_ENTITIES;
        }
        
    }

    private static Logger log = Logger.getLogger(GroupProvider.class);
    private final Binder binder;

    public GroupProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        binder = new GroupProvider.GroupBinder();
    }

    public String getEntityPrefix() {
        return "groups";
    }

    public boolean entityExists(String id) {
        // sample entity
        if (id.equals(":ID:")) {
            return true;
        }

        final Context context = DSpace.context();
        try {
            return Group.find(context, Integer.parseInt(id)) != null;
        } catch (SQLException ex) {
            log.debug("Failed to find group. Assuming that this means it doesn't exist.", ex);
            return false;
        } finally {
            DSpace.complete(context);
        }
    }

    /**
     * Returns entity containing information about particular user
     * @param reference
     * @return
     */
    public Object getEntity(EntityReference reference) {
        final String id = reference.getId();

        // sample entity
        if (id == null || ":ID:".equals(id)) {
            return getSampleEntity();
        }
        
        return entity(id);
    }

    private Object entity(final String id) {
        final Operation operation = Operation.GET_GROUP_ENTITIES;
        final Context context = DSpace.context();
        try {
            final Parameters parameters = new Parameters(requestStore);
            final Route route = new Route(requestStore);

            if (route.isAttribute()) {
                log.debug("Using generic entity binding");
                return binder.resolve(id, route, parameters, context);
            }

            if (entityExists(id)) {
                if (parameters.getEntityBuild().isIdOnly()) {
                    return new GroupEntityId(id, context);
                } else {
                    return new GroupEntity(id, context, parameters.getDetailDepth().getDepth());
                }

            } else {
                if (log.isDebugEnabled()) log.debug("Cannot find entity " + id);
                throw new EntityNotFoundException(operation);
            }
        } catch (SQLException cause) {
            if (log.isDebugEnabled()) log.debug("Cannot find entity " + id);
            throw new SQLFailureEntityException(operation, cause);
        } finally {
            DSpace.complete(context);
        }
    }

    /**
     * Returns the list of groups on the system using GroupEntity
     * @see GroupEntity
     * @param ref
     * @param search
     * @return
     */
    public List<?> getEntities(EntityReference ref, Search search) {
        return getAllGroups();
    }

    private List<?> getAllGroups() {
        final Parameters parameters = new Parameters(requestStore);
        final Context context = DSpace.context();
        try {
            List<Object> entities = new ArrayList<Object>();

            final Group[] groups = Group.findAll(context, Group.NAME);
            if (groups != null) {
                for (int x = 0; x < groups.length; x++) {
                    entities.add(parameters.getEntityBuild().isIdOnly() 
                            ? new GroupEntityId(groups[x]) : 
                                new GroupEntity(groups[x],1, parameters.getDetailDepth().getDepth()));
                }
            }

            // do sorting and limiting if necessary
            parameters.sort(entities);
            parameters.removeTrailing(entities);

            return entities;

        } catch (SQLException cause) {
            throw new SQLFailureEntityException(Operation.GET_GROUP_ENTITIES, cause);

        } finally {
            DSpace.complete(context);
        }
    }
}
