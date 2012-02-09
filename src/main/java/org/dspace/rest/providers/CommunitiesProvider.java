/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.providers;

//rev
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Community;
import org.dspace.core.Context;
import org.dspace.rest.entities.CollectionEntity;
import org.dspace.rest.entities.CommunityEntity;
import org.dspace.rest.entities.CommunityEntityId;
import org.dspace.rest.entities.DetailDepth;
import org.dspace.rest.params.DetailDepthParameters;
import org.dspace.rest.params.EntityBuildParameters;
import org.dspace.rest.util.RecentSubmissionsException;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.EntityView;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.EntityProviderMethodStore;
import org.sakaiproject.entitybus.entityprovider.extension.ActionReturn;
import org.sakaiproject.entitybus.entityprovider.extension.EntityData;
import org.sakaiproject.entitybus.entityprovider.search.Search;
import org.sakaiproject.entitybus.exception.EntityException;

/**
 * Provides interface for access to community entities
 * @see CommunityEntityId
 * @see CommunityEntity
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CommunitiesProvider extends AbstractBaseProvider implements CoreEntityProvider {

    private static Logger log = Logger.getLogger(CommunitiesProvider.class);

    public CommunitiesProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        entityProviderManager.registerEntityProvider(this);
        EntityProviderMethodStore epms = entityProviderManager.getEntityProviderMethodStore();
        processedEntity = CommunityEntity.class;
        func2actionMapGET.put("getId", "id");
        func2actionMapGET.put("getName", "name");
        func2actionMapGET.put("getCountItems", "countItems");
        func2actionMapGET.put("getHandle", "handle");
        func2actionMapGET.put("getType", "type");
        func2actionMapGET.put("getCollections", "collections");
        func2actionMapGET.put("getCanEdit", "canedit");
        func2actionMapGET.put("getParentCommunity", "anchestor");
        func2actionMapGET.put("getSubCommunities", "children");
        func2actionMapGET.put("getRecentSubmissions", "recent");
        func2actionMapGET.put("getShortDescription", "shortDescription");
        func2actionMapGET.put("getCopyrightText", "copyrightText");
        func2actionMapGET.put("getSidebarText", "sidebarText");
        func2actionMapGET.put("getIntroductoryText", "introductoryText");
        func2actionMapGET.put("getLogo", "logo");
//        func2actionMapPUT.put("setName", "name");
//        func2actionMapPUT.put("setShortDescription", "shortDescription");
//        func2actionMapPUT.put("setCopyrightText", "copyrightText");
//        func2actionMapPUT.put("setSidebarText", "sidebarText");
//        func2actionMapPUT.put("setIntroductoryText", "introductoryText");
//        func2actionMapPUT.put("addCollection", "collections");
//        func2actionMapPUT.put("addSubcommunity", "children");
//        func2actionMapPOST.put("createCollection", "createCollection");
//        inputParamsPOST.put("createCollection", new String[]{"name", "id"});
//        func2actionMapPOST.put("createSubcommunity", "createSubcommunity");
//        inputParamsPOST.put("createSubcommunity", new String[]{"name", "id"});
//        func2actionMapDELETE.put("removeChildren", "children");
//        func2actionMapDELETE.put("removeSubcollections", "collections");
//        func2actionMapDELETE.put("delete", "");
        entityConstructor = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, DetailDepth.class});
        initMappings(processedEntity);
        //createActions(processedEntity);
        //createPUTActions(processedEntity);
    }

    public Object testAction5(EntityReference reference, EntityView view) throws SQLException, RecentSubmissionsException {
        return new ActionReturn(new EntityData(reference.toString(), "name", "value"), (String) null);
    }

    public String getEntityPrefix() {
        return "communities";
    }

    public boolean entityExists(String id) {
        log.info(userInfo() + "entity_exists:" + id);

        // sample entity
        if (id.equals(":ID:")) {
            return true;
        }

        Context context = context();
        boolean result = false;
        try {
            Community comm = Community.find(context, Integer.parseInt(id));
            if (comm != null) {
                result = true;
            }
        } catch (SQLException ex) {
            result = false;
        }

        complete(context);
        return result;
    }

    @Override
        public Object getEntity(EntityReference reference) {
            System.out.println("My reference is " +reference.getId());
            log.info(userInfo() + "get_entity:" + reference.getId());
            String segments[] = {};

            log.info("Community get entity");
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

                Context context = context();

                try {
                    if (entityExists(reference.getId())) {
                        try {
                            // return just entity containg id or full info
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
        log.info(userInfo() + "list_entities");

        log.info("stor2" + requestStore.getStoredValue("pathInfo").toString());

        Context context = context();
        
        List<Object> entities = new ArrayList<Object>();

        try {
            Community[] communities = null;
            communities = EntityBuildParameters.build(requestStore).isTopLevelOnly() ? Community.findAllTop(context) : Community.findAll(context);
            for (Community c : communities) {
                entities.add(EntityBuildParameters.build(requestStore).isIdOnly() ? new CommunityEntityId(c) : new CommunityEntity(c, 1, DetailDepthParameters.build(requestStore).getDepth()));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        complete(context);

        // sort and limit if necessary
        sort(entities);

        removeTrailing(entities);
        return entities;
    }

    /**
     * Prepare sample entity
     * @return
     */
    public Object getSampleEntity() {
        return new CollectionEntity();


    }
}
