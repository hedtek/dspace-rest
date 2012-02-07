package org.dspace.rest.params;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.DSpaceObject;
import org.dspace.core.Context;
import org.dspace.search.DSQuery;
import org.dspace.search.QueryArgs;
import org.dspace.search.QueryResults;
import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;
import org.sakaiproject.entitybus.exception.EntityException;

public class ScopeParameters {

    private static final int DEFAULT_VALUE = 0;

    private static Logger log = Logger.getLogger(ScopeParameters.class);

    private static final String COLLECTION_KEY = "collection";
    private static final String COMMUNITY_KEY = "community";

    public static ScopeParameters build(final RequestStorage requestStorage, final Context context) {

        final int intcommunity = valueInStore(requestStorage, COMMUNITY_KEY);
        final int intcollection = valueInStore(requestStorage, COLLECTION_KEY);
        
        if ((intcommunity > 0) && (intcollection > 0)) {
            throw new EntityException("Bad request", "Community and collection selected", 400);
        }

        return new ScopeParameters(community(context, intcommunity), collection(context, intcollection));
    }

    private static int valueInStore(final RequestStorage requestStorage,
            final String key) {
        int value;
        final Object storedValue = requestStorage.getStoredValue(key);
        if (storedValue == null) {
            value = DEFAULT_VALUE;
        } else {
            try {
                value = Integer.parseInt(storedValue.toString());
            } catch (NumberFormatException e) {
                log.debug("Ignoring malformed '" + key + "' parameter value - expected a number but was: " + storedValue);
                value = DEFAULT_VALUE;
            }
        }
        return value;
    }

    private static Collection collection(final Context context,
            final int intcollection) {
        Collection collection;
        try {
            collection = Collection.find(context, intcollection);

        } catch (NullPointerException nul) {
            collection = null;
        } catch (SQLException sql) {
            collection = null;
        }

        if ((intcollection > 0) && (collection == null)) {
            throw new EntityException("Bad request", "Unknown collection", 400);
        }
        return collection;
    }

    private static Community community(final Context context, final int intcommunity) {
        Community community;
        try {
            community = Community.find(context, intcommunity);
        } catch (NullPointerException nul) {
            community = null;
        } catch (SQLException sql) {
            community = null;
        }

        if ((intcommunity > 0) && (community == null)) {
            throw new EntityException("Bad request", "Unknown community", 400);
        }
        return community;
    }

    private final Community community;
    private final Collection collection;
    
    public ScopeParameters(Community community, Collection collection) {
        this.community = community;
        this.collection = collection;
    }
    

    public DSpaceObject scope() {
        final DSpaceObject scope;
        if (community != null) {
            scope = community;
        } else if (collection != null) {
            scope = collection;
        } else {
            scope = null;
        }
        return scope;
    }
    
    public QueryResults doQuery(final Context context, final QueryArgs args) throws IOException {
        final QueryResults queryResults;

        /**
         * search can be performed only on community or collection selected
         * or all, not on the both in same time; check this requirement
         */
        if (community != null) {
            queryResults = DSQuery.doQuery(context, args, community);
        } else if (collection != null) {
            queryResults = DSQuery.doQuery(context, args, collection);
        } else {
            queryResults = DSQuery.doQuery(context, args);
        }
        return queryResults;
    }
}
