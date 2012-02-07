/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.providers;

//rev
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.io.OutputStream;

import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.EntityView;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityCustomAction;
import org.dspace.content.Community;
import org.dspace.content.Collection;
import org.dspace.core.Context;
import org.apache.log4j.Logger;
import org.sakaiproject.entitybus.exception.EntityException;
import java.sql.SQLException;
import org.dspace.rest.entities.*;
import org.dspace.rest.util.UtilHelper;
import java.util.Collections;
import org.dspace.rest.util.GenComparator;
import org.dspace.handle.HandleManager;
import org.dspace.content.DSpaceObject;
import org.sakaiproject.entitybus.exception.EntityException;
import java.sql.SQLException;
import org.dspace.rest.entities.*;
import org.dspace.rest.util.UtilHelper;
import org.dspace.rest.util.RecentSubmissionsException;
import java.util.Collections;
import org.dspace.rest.util.GenComparator;
import org.sakaiproject.entitybus.entityprovider.EntityProviderMethodStore;
import org.sakaiproject.entitybus.entityprovider.extension.CustomAction;
import org.sakaiproject.entitybus.entityprovider.extension.*;
import org.sakaiproject.entitybus.entityprovider.capabilities.*;
import java.lang.reflect.*;
import java.util.Map;
import java.util.HashMap;
import org.dspace.authorize.AuthorizeException;
import org.dspace.rest.util.RequestParameters;
import java.io.IOException;

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
        entityConstructor = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, RequestParameters.class});
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

        // extract query parameters
        refreshParams(context);
        boolean result = false;
        try {
            Community comm = Community.find(context, Integer.parseInt(id));
            if (comm != null) {
                result = true;
            }
        } catch (SQLException ex) {
            result = false;
        }

        removeConn(context);
        return result;
    }

    @Override
        public Object getEntity(EntityReference reference) {
            System.out.println("My reference is " +reference.getId());
            log.info(userInfo() + "get_entity:" + reference.getId());
            String segments[] = {};

            log.info("Community get entity");
            if (reqStor.getStoredValue("pathInfo") != null) {
                segments = reqStor.getStoredValue("pathInfo").toString().split("/", 10);
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
                    RequestParameters uparams;
                    uparams = refreshParams(context);
                    if (entityExists(reference.getId())) {
                        try {
                            // return just entity containg id or full info
                            if (idOnly) {
                                return new CommunityEntityId(reference.getId(), context);
                            } else {
                                return new CommunityEntity(reference.getId(), context, 1, uparams);
                            }
                        } catch (SQLException ex) {
                            throw new IllegalArgumentException("Invalid id:" + reference.getId());
                        } catch (NullPointerException ne) {
                            ne.printStackTrace();
                        }
                    }
                    throw new IllegalArgumentException("Invalid id:" + reference.getId());
                } finally {

                    removeConn(context);
                }
            }
        }

    public List<?> getEntities(EntityReference ref, Search search) {
        log.info(userInfo() + "list_entities");

        log.info("stor2" + reqStor.getStoredValue("pathInfo").toString());

        Context context = context();

        RequestParameters uparams;
        uparams = refreshParams(context);
        List<Object> entities = new ArrayList<Object>();

        try {
            Community[] communities = null;
            communities = topLevelOnly ? Community.findAllTop(context) : Community.findAll(context);
            for (Community c : communities) {
                entities.add(idOnly ? new CommunityEntityId(c) : new CommunityEntity(c, 1, uparams));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        removeConn(context);

        // sort and limit if necessary
        if (!idOnly && uparams.getSortOptions().size() > 0) {
            Collections.sort(entities, new GenComparator(uparams.getSortOptions()));

        }

        removeTrailing(entities);
        return entities;
    }

    // not necessarly anymore
    public void adeleteEntity(EntityReference ref, Map<String, Object> params) {
        Context context = context();

        RequestParameters uparams;
        uparams = refreshParams(context);
        try {
            Community comm = Community.find(context, Integer.parseInt(ref.getId()));
            if (comm != null) {
                comm.delete();
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ae) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        } catch (IOException ie) {
            throw new EntityException("Internal server error", "SQL error, cannot delete community", 500);
        }
        removeConn(context);
    }

    public String[] importData(String reference, InputStream data, String encodingKey, Map<String, Object> params) {
        log.info("===== importdata called");
        return new String[]{"123", "45678"};
    }

    public String exportData(String reference, Search search, OutputStream data, boolean destructive, Map<String, Object> params) {
        log.info("---EXPORT");
        return "sadasdasq3123";
    }

    /**
     * Prepare sample entity
     * @return
     */
    public Object getSampleEntity() {
        return new CollectionEntity();


    }
}
