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
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.core.Context;
import org.dspace.rest.entities.DetailDepth;
import org.dspace.rest.entities.ItemEntity;
import org.dspace.rest.entities.ItemEntityId;
import org.dspace.rest.params.DetailDepthParameters;
import org.dspace.rest.params.EntityBuildParameters;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;
import org.sakaiproject.entitybus.exception.EntityException;

/**
 * Provides interface for access to item entities
 * @see ItemEntityId
 * @see ItemEntity
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class ItemsProvider extends AbstractBaseProvider implements CoreEntityProvider {

    private static Logger log = Logger.getLogger(ItemsProvider.class);

    /**
     * Constructor handles registration of provider
     * @param entityProviderManager
     * @throws java.sql.SQLException
     */
    public ItemsProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        entityProviderManager.registerEntityProvider(this);
        processedEntity = ItemEntity.class;
        func2actionMapGET.put("getMetadata", "metadata");
        func2actionMapGET.put("getSubmitter", "submitter");
        func2actionMapGET.put("getIsArchived", "isArchived");
        func2actionMapGET.put("getIsWithdrawn", "isWithdrawn");
        func2actionMapGET.put("getOwningCollection", "owningCollection");
        func2actionMapGET.put("getLastModified", "lastModified");
        func2actionMapGET.put("getCollections", "collections");
        func2actionMapGET.put("getCommunities", "communities");
        func2actionMapGET.put("getName", "name");
        func2actionMapGET.put("getBitstreams", "bitstreams");
        func2actionMapGET.put("getHandle", "handle");
        func2actionMapGET.put("getCanEdit", "canedit");
        func2actionMapGET.put("getId", "id");
        func2actionMapGET.put("getType", "type");
        func2actionMapGET.put("getBundles", "bundles");
//        func2actionMapPUT.put("addBundle", "bundles");
//        func2actionMapPOST.put("createBundle", "createBundle");
//        inputParamsPOST.put("createBundle", new String[]{"name", "id"});

        entityConstructor = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, DetailDepth.class});
        initMappings(processedEntity);
    }

    // this is the prefix where provider is registered (URL path)
    public String getEntityPrefix() {
        return "items";
    }

    public boolean entityExists(String id) {
        log.info(userInfo() + "entity_exists:" + id);

        // sample entity
        if (id.equals(":ID:")) {
            return true;
        }

        Context context = context();

        boolean result = false;

        // search for existence for particular item
        try {
            Item col = Item.find(context, Integer.parseInt(id));
            if (col != null) {
                result = true;
            }
        } catch (SQLException ex) {
            result = false;
        }

        // handles manual deregistration by sql server to lower load
        complete(context);
        return result;
    }

    public Object getEntity(EntityReference reference) {
        log.info(userInfo() + "get_entity:" + reference.getId());
        String segments[] = {};

        if (requestStore.getStoredValue("pathInfo") != null) {
            segments = requestStore.getStoredValue("pathInfo").toString().split("/", 10);
        }

        // first check if there is sub-field requested
        // if so then invoke appropriate method inside of entity
        if (segments.length > 3) {
            return super.getEntity(reference);
        }

        Context context = context();
        try {

            // sample entity
            if (reference.getId().equals(":ID:")) {
                return new ItemEntity();
            }

            if (entityExists(reference.getId())) {
                try {

                    // return basic or full info, according to requirements
                    if (EntityBuildParameters.build(requestStore).isIdOnly()) {
                        return new ItemEntityId(reference.getId(), context);
                    } else {
                        return new ItemEntity(reference.getId(), context, 1, DetailDepthParameters.build(requestStore).getDepth());
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

    public List<?> getEntities(EntityReference ref, Search search) {
        log.info(userInfo() + "list_entities");

        Context context = context();
        try {
            List<Object> entities = new ArrayList<Object>();

            try {
                ItemIterator items = Item.findAll(context);
                while (items.hasNext()) {
                    entities.add(EntityBuildParameters.build(requestStore).isIdOnly() ? new ItemEntityId(items.next()) : new ItemEntity(items.next(), 1, DetailDepthParameters.build(requestStore).getDepth()));
                }
            } catch (SQLException ex) {
                throw new EntityException("Internal server error", "SQL error", 500);
            }

            sort(entities);

            removeTrailing(entities);
            return entities;
        } finally {
            complete(context);
        }
    }

    /**
     * Return sample entity
     * @return
     */
    public Object getSampleEntity() {
        return new ItemEntity();
    }
}

