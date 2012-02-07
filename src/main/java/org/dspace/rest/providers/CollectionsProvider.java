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
import java.util.Map;

import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.EntityView;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityCustomAction;
import org.dspace.content.Collection;
import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.sakaiproject.entitybus.exception.EntityException;
import java.sql.SQLException;
import org.dspace.rest.entities.*;
import org.dspace.rest.util.UtilHelper;
import org.dspace.rest.util.RequestParameters;
import org.dspace.rest.util.RecentSubmissionsException;
import org.dspace.rest.util.GenComparator;
import org.sakaiproject.entitybus.entityprovider.capabilities.*;
import java.util.Collections;

/**
 * Provides interface for access to collections entities
 * @see CollectionEntity
 * @see CollectionEntityId
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CollectionsProvider extends AbstractBaseProvider implements CoreEntityProvider {

    private static Logger log = Logger.getLogger(CollectionsProvider.class);

    public CollectionsProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        entityProviderManager.registerEntityProvider(this);
        processedEntity = CollectionEntity.class;
        func2actionMapGET.put("getId", "id");
        func2actionMapGET.put("getName", "name");
        func2actionMapGET.put("getLicence", "licence");
        func2actionMapGET.put("getItems", "items");
        func2actionMapGET.put("getHandle", "handle");
        func2actionMapGET.put("getCanEdit", "canedit");
        func2actionMapGET.put("getCommunities", "communities");
        func2actionMapGET.put("getCountItems", "countItems");
        func2actionMapGET.put("getType", "type");
        func2actionMapGET.put("getShortDescription", "shortDescription");
        func2actionMapGET.put("getIntroText", "introText");
        func2actionMapGET.put("getCopyrightText", "copyrightText");
        func2actionMapGET.put("getSidebarText", "sidebarText");
        func2actionMapGET.put("getProvenance", "provenance");
        func2actionMapGET.put("getLogo", "logo");
//        func2actionMapPUT.put("setShortDescription", "shortDescription");
//        func2actionMapPUT.put("setIntroText", "introText");
//        func2actionMapPUT.put("setCopyrightText", "copyrightText");
//        func2actionMapPUT.put("setSidebarText", "sidebarText");
//        func2actionMapPUT.put("setProvenance", "provenance");
//        func2actionMapPUT.put("setLicence", "licence");
//        func2actionMapPUT.put("setName", "name");
//        func2actionMapPOST.put("createSubmitters", "createSubmitters");
//        inputParamsPOST.put("createSubmitters", new String[]{"id"});
//        func2actionMapPOST.put("createTemplateItem", "createTemplateItem");
//        inputParamsPOST.put("createTemplateItem", new String[]{"id"});
//        func2actionMapPOST.put("createWorkflowGroup", "createWorkflowGroup");
//        inputParamsPOST.put("createWorkflowGroup", new String[]{"id", "step"});
//        func2actionMapDELETE.put("removeTemplateItem", "templateitem");
//        func2actionMapDELETE.put("removeSubmitters", "submitters");
        entityConstructor = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, RequestParameters.class});
        initMappings(processedEntity);

    }

    public String getEntityPrefix() {
        return "collections";
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
            Collection col = Collection.find(context, Integer.parseInt(id));
            if (col != null) {
                result = true;
            }
        } catch (SQLException ex) {
            result = false;
        }

        // close connection to prevent connection problems
        removeConn(context);
        return result;
    }

    /**
     * Returns information about particular entity
     * @param reference
     * @return
     */
    @Override
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
            } else {

                Context context = context();

                try {
                    RequestParameters uparams;
                    uparams = refreshParams(context);

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
                            if (idOnly) {
                                return new CollectionEntityId(reference.getId(), context);
                            } else {
                                return new CollectionEntity(reference.getId(), context, 1, uparams);
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
        }

    /**
     * List all collection in the system, sort and format if requested
     * @param ref
     * @param search
     * @return
     */
    public List<?> getEntities(EntityReference ref, Search search) {
        log.info(userInfo() + "list_entities");

        Context context = context();

        RequestParameters uparams;
        uparams = refreshParams(context);

        List<Object> entities = new ArrayList<Object>();

        try {
            Collection[] collections = null;
            collections = Collection.findAll(context);
            //            System.out.println(" number of collections " + Collection.getNumCollections(context));
            for (Collection c : collections) {
                entities.add(idOnly ? new CollectionEntityId(c) : new CollectionEntity(c, 1, uparams));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        removeConn(context);
        
        sort(entities);
        removeTrailing(entities);

        return entities;
    }

    /*
     * Here is sample collection entity defined
     */
    public Object getSampleEntity() {
        return new CollectionEntity();
    }
}
