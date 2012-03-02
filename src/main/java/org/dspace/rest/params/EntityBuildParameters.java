package org.dspace.rest.params;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.core.Constants;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.FetchGroup;
import org.dspace.rest.data.bitstream.BitstreamEntityId;
import org.dspace.rest.data.bitstream.Bitstreams;
import org.dspace.rest.data.bundle.BundleEntityId;
import org.dspace.rest.data.bundle.Bundles;
import org.dspace.rest.data.collection.Collections;
import org.dspace.rest.data.community.Communities;
import org.dspace.rest.data.item.BulkItemBuilder;
import org.dspace.rest.data.item.ItemBuilder;
import org.dspace.rest.data.item.Items;
import org.dspace.rest.entities.UserEntity;
import org.dspace.rest.entities.UserEntityId;
import org.dspace.search.QueryResults;
import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

public class EntityBuildParameters {
    
    public static EntityBuildParameters build(RequestStorage requestStore) {
        final boolean idOnly = valueInStoreIsTrue(requestStore, "idOnly");
        final boolean immediateOnly = !valueInStoreIsFalse(requestStore, "immediateOnly");
        final boolean topLevelOnly = !valueInStoreIsFalse(requestStore, "topLevelOnly");
        final FetchGroup fetch;
        fetch = fetch(requestStore, idOnly);
        return new EntityBuildParameters(idOnly, immediateOnly, topLevelOnly, fetch);
    }
    private static FetchGroup fetch(RequestStorage requestStore, final boolean isIdOnly) {
        final FetchGroup defaultFetch;
        if (isIdOnly) {
            defaultFetch = FetchGroup.MINIMAL;
        } else {
            defaultFetch = FetchGroup.FULL;
        }
        
        final Object storedValue = requestStore.getStoredValue("fetch");
        if (storedValue == null) {
            return defaultFetch;
        } else {
            try {
                return FetchGroup.valueOf(storedValue.toString().toUpperCase());
            } catch (IllegalArgumentException e) {
                // value not recognized
                return defaultFetch;
            }
        }
    }

    private static boolean valueInStoreIsFalse(RequestStorage requestStore,
            final String key) {
        return valueInStore(requestStore, key, "false");
    }

    private static boolean valueInStoreIsTrue(RequestStorage requestStore,
            final String key) {
        return valueInStore(requestStore, key, "true");
    }

    private static boolean valueInStore(RequestStorage requestStore,
            final String key, final String expectedValue) {
        return expectedValue.equals(requestStore.getStoredValue(key));
    }

    private final FetchGroup fetchGroup;
    private final boolean idOnly;
    private final boolean topLevelOnly; 
    
    private EntityBuildParameters(boolean idOnly, boolean topLevelOnly,
            boolean immediateOnly, final FetchGroup fetchGroup) {
        super();
        this.idOnly = idOnly;
        this.topLevelOnly = topLevelOnly;
        this.fetchGroup = fetchGroup;
        // immediate only is unsupported
    }

    public boolean isIdOnly() {
        return idOnly;
    }

    public boolean isTopLevelOnly() {
        return topLevelOnly;
    }

    public FetchGroup getFetchGroup() {
        return fetchGroup;
    }

    public List<Object> build(final Context context, final DetailDepth depth, final QueryResults queryResults) throws SQLException {
        final List<Object> entities = new ArrayList<Object>();
        /**
         * check returned objects, recognize them and put in result
         * list as expected
         */
        for (int x = 0; x < queryResults.getHitTypes().size(); x++) {
            final String hitId = queryResults.getHitIds().get(x).toString();
            final Integer hitType = (Integer) (queryResults.getHitTypes().get(x));
            entities.add(fetch(context, depth, hitId, hitType));
        }
        return entities;
    }

    private Object fetch(final Context context, final DetailDepth depth,
            final String hitId, final Integer hitType) throws SQLException {
        final Object hit;
        switch (hitType) {
            case Constants.ITEM:
            {
                hit = item(context, hitId, depth);
                break;
            }
    
            case Constants.COMMUNITY:
            {
                hit = community(hitId, context, depth);
                break;
            }
    
            case Constants.COLLECTION:
            {
                hit = Collections.build(hitId, context, depth, idOnly);
                break;
            }
    
            case Constants.BITSTREAM:
            {
                hit = bitstream(context, depth, hitId);
                break;
            }
    
            case Constants.BUNDLE:
            {
                hit = bundle(context, depth, hitId);
                break;
            }
    
            case Constants.EPERSON:
            {
                hit = (idOnly ? new UserEntityId(hitId) : new UserEntity(hitId, context));
                break;
            }
    
            default: {
                hit = null;
            }

        }
        return hit;
    }
    
    BitstreamEntityId bitstream(final Context context, final DetailDepth depth, final String hitId) throws SQLException {
        return new Bitstreams(context).with(getFetchGroup()).till(depth).build(hitId);
    }
    
    private BundleEntityId bundle(final Context context,
            final DetailDepth depth, final String uid) throws SQLException {
        return new Bundles(context).with(getFetchGroup()).till(depth).build(uid);
    }
    

    Entity item(final Item item, final DetailDepth depth) throws SQLException {
        return new ItemBuilder(item).till(depth).with(getFetchGroup()).build();
    }
    
    Entity item(final Context context, final String uid,
            final DetailDepth depth) throws SQLException {
        return new Items(context).fetch(depth, uid, getFetchGroup());
    }
    
    Entity community(String uid, Context context, DetailDepth depth) throws SQLException {
        return Communities.fetch(uid, context, depth, getFetchGroup());
    }
    

    List<Entity> items(final Context context) throws SQLException {
        final ItemIterator items = Item.findAll(context);
        final List<Entity> entities = BulkItemBuilder.builder(isIdOnly(), DetailDepth.FOR_ALL_INDEX).build(items);
        return entities;
    }

}
