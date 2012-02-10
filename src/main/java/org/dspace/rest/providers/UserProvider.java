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

        Context context = context();

        boolean result = false;
        try {
            EPerson eperson = EPerson.find(context, Integer.parseInt(id));
            if (eperson != null) {
                result = true;
            }
        } catch (SQLException ex) {
            result = false;
        }

        complete(context);
        return result;
    }

    /**
     * Returns entity containing information about particular user
     * @param reference
     * @return
     */
    public Object getEntity(EntityReference reference) {
        final Route route = new Route(requestStore);
        if (route.isAttribute()) {
            log.debug("Using generic entity binding");
            final Parameters parameters = new Parameters(requestStore);
            
            
            final Context context1 = context();
            try {
                return binder.resolve(reference.getId(), route, parameters, context1);
            } finally {
                complete(context1);
            }
        }

        Context context = context();
        try {
            // sample entity
            if (reference.getId().equals(":ID:")) {
                return new UserEntity();
            }


            if (reference.getId() == null) {
                return new UserEntity();
            }

            if (entityExists(reference.getId())) {
                try {
                    if (EntityBuildParameters.build(requestStore).isIdOnly()) {
                        return new UserEntityId(reference.getId());
                    } else {
                        return new UserEntity(reference.getId(), context, 1);
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
