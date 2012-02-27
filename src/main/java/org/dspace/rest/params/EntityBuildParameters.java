package org.dspace.rest.params;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.core.Constants;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Fetch;
import org.dspace.rest.data.collection.Collections;
import org.dspace.rest.data.community.Communities;
import org.dspace.rest.data.item.ItemEntity;
import org.dspace.rest.data.item.ItemEntityId;
import org.dspace.rest.entities.BitstreamEntity;
import org.dspace.rest.entities.BitstreamEntityId;
import org.dspace.rest.entities.BundleEntity;
import org.dspace.rest.entities.BundleEntityId;
import org.dspace.rest.entities.UserEntity;
import org.dspace.rest.entities.UserEntityId;
import org.dspace.search.QueryResults;
import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

public class EntityBuildParameters {
    
    public static EntityBuildParameters build(RequestStorage requestStore) {
        final boolean idOnly = valueInStoreIsTrue(requestStore, "idOnly");
        final boolean immediateOnly = !valueInStoreIsFalse(requestStore, "immediateOnly");
        final boolean topLevelOnly = !valueInStoreIsFalse(requestStore, "topLevelOnly");
        final Fetch fetch;
        fetch = fetch(requestStore, idOnly);
        return new EntityBuildParameters(idOnly, immediateOnly, topLevelOnly, fetch);
    }
    private static Fetch fetch(RequestStorage requestStore, final boolean isIdOnly) {
        final Fetch defaultFetch;
        if (isIdOnly) {
            defaultFetch = Fetch.MINIMAL;
        } else {
            defaultFetch = Fetch.DEFAULT;
        }
        
        final Object storedValue = requestStore.getStoredValue("fetch");
        if (storedValue == null) {
            return defaultFetch;
        } else {
            try {
                return Fetch.valueOf(storedValue.toString().toUpperCase());
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

    private final Fetch fetchGroup;
    private final boolean idOnly;
    private final boolean topLevelOnly; 
    
    private EntityBuildParameters(boolean idOnly, boolean topLevelOnly,
            boolean immediateOnly, final Fetch fetchGroup) {
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

    public Fetch getFetchGroup() {
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
                hit = (idOnly ? new ItemEntityId(hitId, context) : new ItemEntity(hitId, context, depth));
                break;
            }
    
            case Constants.COMMUNITY:
            {
                hit = Communities.fetch(hitId, context, depth, idOnly);
                break;
            }
    
            case Constants.COLLECTION:
            {
                hit = Collections.build(hitId, context, depth, idOnly);
                break;
            }
    
            case Constants.BITSTREAM:
            {
                hit = (idOnly ? new BitstreamEntityId(hitId, context) : new BitstreamEntity(hitId, context, depth));
                break;
            }
    
            case Constants.BUNDLE:
            {
                hit = (idOnly ? new BundleEntityId(hitId, context) : new BundleEntity(hitId, context, depth));
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
}
