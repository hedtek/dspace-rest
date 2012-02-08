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
import org.dspace.rest.entities.ItemEntity;
import org.dspace.rest.entities.ItemEntityId;
import org.dspace.rest.entities.UserEntity;
import org.dspace.rest.entities.UserEntityId;
import org.dspace.search.QueryResults;

public class EntityBuildParameters {

    public static List<Object> build(final Context context,
            final RequestParameters uparams, final QueryResults queryResults,
            final boolean idOnly) throws SQLException {
        final List<Object> entities = new ArrayList<Object>();
        /**
         * check returned objects, recognize them and put in result
         * list as expected
         */
        for (int x = 0; x < queryResults.getHitTypes().size(); x++) {
            switch ((Integer) (queryResults.getHitTypes().get(x))) {
            case Constants.ITEM:
            {
                entities.add(idOnly ? new ItemEntityId(queryResults.getHitIds().get(x).toString(), context) : new ItemEntity(queryResults.getHitIds().get(x).toString(), context,1, uparams));
            }
            break;
    
            case Constants.COMMUNITY:
            {
                entities.add(idOnly ? new CommunityEntityId(queryResults.getHitIds().get(x).toString(), context) : new CommunityEntity(queryResults.getHitIds().get(x).toString(), context,1, uparams));
            }
            break;
    
            case Constants.COLLECTION:
            {
                entities.add(idOnly ? new CollectionEntityId(queryResults.getHitIds().get(x).toString(), context) : new CollectionEntity(queryResults.getHitIds().get(x).toString(), context,1, uparams));
            }
            break;
    
            case Constants.BITSTREAM:
            {
                entities.add(idOnly ? new BitstreamEntityId(queryResults.getHitIds().get(x).toString(), context) : new BitstreamEntity(queryResults.getHitIds().get(x).toString(), context,1, uparams));
            }
            break;
    
            case Constants.BUNDLE:
            {
                entities.add(idOnly ? new BundleEntityId(queryResults.getHitIds().get(x).toString(), context) : new BundleEntity(queryResults.getHitIds().get(x).toString(), context,1, uparams));
            }
            break;
    
            case Constants.EPERSON:
            {
                entities.add(idOnly ? new UserEntityId(queryResults.getHitIds().get(x).toString()) : new UserEntity(queryResults.getHitIds().get(x).toString(), context,1, uparams));
            }
            break;
    
            }
        }
        return entities;
    }

}
