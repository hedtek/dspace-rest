package org.dspace.rest.data.collection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.Pagination;
import org.dspace.rest.params.Parameters;

public class Collections {
    
    private static Logger log = Logger.getLogger(Collections.class);
    
    private static Collection fetch(final String uid, final Context context)
            throws SQLException {
        final int id = Integer.parseInt(uid);
        return Collection.find(context, id);
    }

    public static Entity build(final String uid, final Context context,
            final DetailDepth depth, final boolean idOnly) throws SQLException {
        return new CollectionBuilder(fetch(uid, context)).withIdOnly(idOnly).build(depth);
    }

    public static Entity collectionWithItems(String id, Context context, Pagination pagination) throws SQLException {
        return new CollectionBuilder(fetch(id, context)).light(pagination);
    }

    public static CollectionEntity collection(String id, Context context,
            final DetailDepth depth) throws SQLException {
        return new CollectionBuilder(fetch(id, context)).full(depth);
    }

    public static List<Entity> build(final Parameters parameters, final Collection[] collections) throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        final boolean idOnly = parameters.getEntityBuild().isIdOnly();
        for (Collection collection : collections) {
            entities.add(new CollectionBuilder(collection).withIdOnly(idOnly).build(DetailDepth.FOR_ALL_INDEX));
        }
        return entities;
    }

    public static List<Entity> toNoItemEntities(Collection[] collections)
            throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        for (Collection collection : collections) {
            entities.add(new CollectionBuilder(collection).withNoItems());
        }
        return entities;
    }

    
    public static List<Entity> collections(Community community, int level, final DetailDepth depth)
            throws SQLException {
        final Collection[] collections = community.getCollections();
        final List<Entity> entities = new ArrayList<Entity>();
        for (Collection collection : collections) {
            entities.add(new CollectionBuilder(collection).withFull(depth.includeFullDetails(level + 1)).build(level, depth));
        }
        return entities;
    }

    public static Entity owner(Item item, int level, final DetailDepth depth)
            throws SQLException {
        Entity owningCollection;
        final Collection parent = item.getOwningCollection();
        if (parent == null) {
            owningCollection = null;
        } else {
            owningCollection = new CollectionBuilder(parent).withFull(depth.includeFullDetails(level)).build(level, depth);
        }
        return owningCollection;
    }

    public static List<Entity> build(final int level, final DetailDepth depth, final Collection[] collections)
            throws SQLException {
        final boolean includeFullNextLevel = depth.includeFullDetails(level);
        final List<Entity> entities = new ArrayList<Entity>();
        for (Collection collection : collections) {
            entities.add(new CollectionBuilder(collection).withFull(includeFullNextLevel).build(level, depth));
        }
        return entities;
    }
    
}