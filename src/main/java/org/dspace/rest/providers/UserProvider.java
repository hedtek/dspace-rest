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
import org.dspace.eperson.EPerson;
import org.dspace.rest.diagnose.EntityNotFoundException;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.entities.UserEntity;
import org.dspace.rest.entities.UserEntityId;
import org.dspace.rest.params.EntityBuildParameters;
import org.dspace.rest.params.Parameters;
import org.dspace.rest.params.Route;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;



/**
 * Provides interface for access to user info entities
 * @see UserEntity
 * @see UserEntityId
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class UserProvider extends AbstractBaseProvider  implements CoreEntityProvider {

    private static Logger log = Logger.getLogger(UserProvider.class);
    private final Binder binder;

    public UserProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        binder = Binder.forUsers();
    }

    public String getEntityPrefix() {
        return "users";
    }

    public boolean entityExists(String id) {

        // sample entity
        if (id.equals(":ID:")) {
            return true;
        }

        final Context context = context();
        try {
            return EPerson.find(context, Integer.parseInt(id)) != null;
        } catch (SQLException ex) {
            log.debug("Failed to find user. Assuming that this means it doesn't exist.", ex);
            return false;
        } finally {
            complete(context);
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
        final Operation operation = Operation.GET_USER_ENTITIES;
        final Context context = context();
        try {
            final Route route = new Route(requestStore);
            final Parameters parameters = new Parameters(requestStore);

            if (route.isAttribute()) {
                log.debug("Using generic entity binding");

                return binder.resolve(id, route, parameters, context);
            } else {

                if (entityExists(id)) {
                    if (parameters.getEntityBuild().isIdOnly()) {
                        return new UserEntityId(id);
                    } else {
                        return new UserEntity(id, context, 1);
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
            complete(context);
        }
    }

    /**
     * Returns the list of users on the system using UserEntity
     * @see UserEntity
     * @param ref
     * @param search
     * @return
     */
    public List<?> getEntities(EntityReference ref, Search search) {
        return getAllUsers();
    }

    private List<?> getAllUsers() {
        final Parameters parameters = new Parameters(requestStore);
        final Context context = context();
        try {

            final List<Object> entities = new ArrayList<Object>();
            final boolean idOnly = parameters.getEntityBuild().isIdOnly();
            final EPerson[] epersons = EPerson.findAll(context, EPerson.ID);
            for (final EPerson ePerson: epersons) {
                entities.add(idOnly ? 
                        new UserEntityId(ePerson.getID()) : 
                            new UserEntity(ePerson));
            }

            // do sorting and limiting if necessary
            parameters.sort(entities);
            parameters.removeTrailing(entities);
            return entities;

        } catch (SQLException ex) {
            throw new SQLFailureEntityException(Operation.GET_USER_ENTITIES, ex);
        } finally {
            complete(context);
        }
    }

    /**
     * Returns an Entity object with sample data
     */
    public Object getSampleEntity() {
        return new UserEntity();
    }
}
