/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.providers;

import java.util.ArrayList;
import java.util.List;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;
import org.dspace.core.Context;
import org.sakaiproject.entitybus.exception.EntityException;
import org.dspace.eperson.EPerson;
import java.sql.SQLException;

import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.entities.*;
import org.apache.log4j.Logger;
import java.util.Collections;
import org.dspace.rest.util.UserRequestParams;
import org.dspace.rest.util.GenComparator;



/**
 * Provides interface for access to user info entities
 * @see UserEntity
 * @see UserEntityId
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class UserProvider extends AbstractBaseProvider implements CoreEntityProvider {

    private static Logger log = Logger.getLogger(UserProvider.class);

    public UserProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        entityProviderManager.registerEntityProvider(this);
        processedEntity = UserEntity.class;
        func2actionMapGET.put("getEmail", "email");
        func2actionMapGET.put("getFirstName", "firstName");
        func2actionMapGET.put("getFullName", "fullName");
        func2actionMapGET.put("getHandle", "handle");
        func2actionMapGET.put("getId", "id");
        func2actionMapGET.put("getLanguage", "language");
        func2actionMapGET.put("getLastName", "lastName");
        func2actionMapGET.put("getName", "name");
        func2actionMapGET.put("getNetId", "netId");
        func2actionMapGET.put("getRequireCertificate", "requireCertificate");
        func2actionMapGET.put("getSelfRegistered", "selfRegistered");
        func2actionMapGET.put("getType", "type");
        entityConstructor = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, UserRequestParams.class});
        initMappings(processedEntity);
    }

    public String getEntityPrefix() {
        return "users";
    }

    public boolean entityExists(String id) {
        log.info(userInfo() + "entity_exists:" + id);

        // sample entity
        if (id.equals(":ID:")) {
            return true;
        }

        Context context = context();

        refreshParams(context);

        boolean result = false;
        try {
            EPerson eperson = EPerson.find(context, Integer.parseInt(id));
            if (eperson != null) {
                result = true;
            }
        } catch (SQLException ex) {
            result = false;
        }

        removeConn(context);
        return result;
    }

    /**
     * Returns entity containing information about particular user
     * @param reference
     * @return
     */
    public Object getEntity(EntityReference reference) {
        log.info(userInfo() + "get_entity:" + reference.getId());
        String segments[] = {};

        if (reqStor.getStoredValue("pathInfo") != null) {
            segments = reqStor.getStoredValue("pathInfo").toString().split("/", 10);
        }

        // first check if there is sub-field requested
        // if so then invoke appropriate method inside of entity
        if (segments.length > 3) {
            return super.getEntity(reference);
        }

        Context context = context();
        try {

            UserRequestParams uparams;
            uparams = refreshParams(context);

            // sample entity
            if (reference.getId().equals(":ID:")) {
                return new UserEntity();
            }


            if (reference.getId() == null) {
                return new UserEntity();
            }

            if (entityExists(reference.getId())) {
                try {
                    if (idOnly) {
                        return new UserEntityId(reference.getId());
                    } else {
                        return new UserEntity(reference.getId(), context, 1, uparams);
                    }
                } catch (SQLException ex) {
                    throw new IllegalArgumentException("Invalid id:" + reference.getId());
                }
            }

            throw new IllegalArgumentException("Invalid id:" + reference.getId());
        } finally {
            removeConn(context);
        }
    }

    private Context context() {
        Context context;
        try {
            context = new Context();
        } catch (SQLException ex) {
            throw new SQLFailureEntityException(Operation.CREATE_CONTEXT, ex);
        }
        return context;
    }

    /**
     * Returns the list of users on the system using UserEntity
     * @see UserEntity
     * @param ref
     * @param search
     * @return
     */
    public List<?> getEntities(EntityReference ref, Search search) {
        log.info(userInfo() + "list_entities:");

        Context context = context();
        try {

            // extract and prepare query parameters
            refreshParams(context);
            List<Object> entities = new ArrayList<Object>();

            try {
                EPerson[] epersons = EPerson.findAll(context, EPerson.ID);
                for (int x = 0; x < epersons.length; x++) {
                    entities.add(idOnly ? new UserEntityId(epersons[x].getID()) : new UserEntity(epersons[x]));
                }
            } catch (SQLException ex) {
                throw new SQLFailureEntityException(Operation.CANNOT_FIND_USER_ENTITIES, ex);
            }

            // do sorting and limiting if necessary
            if (!idOnly && sortOptions.size() > 0) {
                Collections.sort(entities, new GenComparator(sortOptions));
            }
            removeTrailing(entities);

            return entities;
        } finally {
            removeConn(context);
        }
    }

    /**
     * Returns an Entity object with sample data
     */
    public Object getSampleEntity() {
        return new UserEntity();
    }
}
