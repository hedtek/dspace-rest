package org.dspace.rest.params;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.core.Constants;
import org.dspace.core.Context;
import org.dspace.rest.entities.BitstreamEntity;
import org.dspace.rest.entities.BitstreamEntityId;
import org.dspace.rest.entities.BundleEntity;
import org.dspace.rest.entities.BundleEntityId;
import org.dspace.rest.entities.CollectionEntity;
import org.dspace.rest.entities.CollectionEntityId;
import org.dspace.rest.entities.CommunityEntity;
import org.dspace.rest.entities.CommunityEntityId;
import org.dspace.rest.entities.DetailDepth;
import org.dspace.rest.entities.ItemEntity;
import org.dspace.rest.entities.ItemEntityId;
import org.dspace.rest.entities.UserEntity;
import org.dspace.rest.entities.UserEntityId;
import org.dspace.search.QueryResults;
import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

public class EntityBuildParameters {
    
    public static EntityBuildParameters build(RequestStorage requestStore) {
        final boolean idOnly = valueInStoreIsTrue(requestStore, "idOnly");
        final boolean immediateOnly = !valueInStoreIsFalse(requestStore, "immediateOnly");
        final boolean topLevelOnly = !valueInStoreIsFalse(requestStore, "topLevelOnly");
        return new EntityBuildParameters(idOnly, immediateOnly, topLevelOnly);
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

    private final boolean idOnly;
    private final boolean topLevelOnly; 
    
    private EntityBuildParameters(boolean idOnly, boolean topLevelOnly,
            boolean immediateOnly) {
        super();
        this.idOnly = idOnly;
        this.topLevelOnly = topLevelOnly;
        // immediate only is unsupported
    }

    public boolean isIdOnly() {
        return idOnly;
    }

    public boolean isTopLevelOnly() {
        return topLevelOnly;
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
            entities.add(build(context, depth, hitId, hitType));
        }
        return entities;
    }

    private Object build(final Context context, final DetailDepth depth,
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
                hit = (idOnly ? new CommunityEntityId(hitId, context) : new CommunityEntity(hitId, context,1, depth));
                break;
            }
    
            case Constants.COLLECTION:
            {
                hit = (idOnly ? new CollectionEntityId(hitId, context) : new CollectionEntity(hitId, context, depth));
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
