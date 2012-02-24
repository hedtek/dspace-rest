package org.dspace.rest.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.rest.entities.CollectionEntity;
import org.dspace.rest.entities.CollectionEntityId;
import org.dspace.rest.entities.CommunityEntity;
import org.dspace.rest.entities.CommunityEntityId;
import org.dspace.rest.entities.DetailDepth;
import org.dspace.rest.entities.Entity;
import org.dspace.rest.entities.ItemBuilder;
import org.dspace.rest.params.Parameters;

public class Collections {
    
    private static class Builder {
        private final Collection collection;
        private boolean isIdOnly = false;

        private Builder(Collection collection) {
            super();
            this.collection = collection;
        }
        
        public boolean isIdOnly() {
            return isIdOnly;
        }

        public void setIdOnly(boolean isIdOnly) {
            this.isIdOnly = isIdOnly;
        }

        public Builder withIdOnly(boolean isIdOnly) {
            this.isIdOnly = isIdOnly;
            return this;
        }
        
        public Builder withFull(boolean isFullNextLevel) {
            setIdOnly(!isFullNextLevel);
            return this;
        }

        public Entity idOnly() {
            return new CollectionEntityId(collection.getID());
        }

        public CollectionEntity full(final int level, final DetailDepth depth) throws SQLException {
            final List<Object> items = items(depth, level);
            return new CollectionEntity(collection, level, depth, items, communities(level, depth), items.size());
        }

        public Entity build(final int level, final DetailDepth depth) throws SQLException {
            if (isIdOnly()) {
                return idOnly();
            } else {
                return full(level, depth);
            }
        } 

        private List<Object> items(final DetailDepth depth, final int nextLevel) throws SQLException {
            final boolean includeFullNextLevel = depth.includeFullDetails(nextLevel);
            return ItemBuilder.builder(!includeFullNextLevel, depth).build(collection.getItems(), nextLevel);
        }

        private List<Object> communities(final int level, final DetailDepth depth) throws SQLException {
            final boolean includeFullNextLevel = depth.includeFullDetails(level);
            final List<Object> communities = new ArrayList<Object>();
            for (Community community : collection.getCommunities()) {
                communities.add(includeFullNextLevel ? new CommunityEntity(community, level, depth) : new CommunityEntityId(community));
            }
            return communities;
        }

    }
    
    private static Collection fetch(final String uid, final Context context)
            throws SQLException {
        final int id = Integer.parseInt(uid);
        return Collection.find(context, id);
    }

    public static Entity build(final Context context, final DetailDepth depth,
            final String uid, final boolean idOnly) throws SQLException {
        return new Builder(fetch(uid, context)).withIdOnly(idOnly).build(1, depth);
    }

    public static Object items(String id, Parameters parameters,
            Context context) throws SQLException {
        switch (parameters.getEntityBuild().getFetchGroup()) {
            case LIGHT:
                return lightCollectionWithItems(id, context);
            default:
                return collection(id, parameters, context).getItems();
        }
    }

    private static Entity lightCollectionWithItems(String id, Context context) {
        return null;
    }

    public static CollectionEntity collection(String id, Parameters parameters, Context context) throws SQLException {
        return new Builder(fetch(id, context)).full(1, parameters.getDetailDepth().getDepth());
    }

    public static Entity build(final String id, final Context context, final Parameters parameters) throws SQLException {
        final boolean idOnly = parameters.getEntityBuild().isIdOnly();
        final DetailDepth depth = parameters.getDetailDepth().getDepth();
        return new Builder(fetch(id, context)).withIdOnly(idOnly).build(1, depth);
    }

    public static List<Entity> build(final Parameters parameters, final Collection[] collections) throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        final boolean idOnly = parameters.getEntityBuild().isIdOnly();
        for (Collection collection : collections) {
            entities.add(new Builder(collection).withIdOnly(idOnly).build(1, DetailDepth.FOR_ALL_INDEX));
        }
        return entities;
    }

    public static List<Entity> collections(Community community, int level,
            final DetailDepth depth, final boolean includeFullNextLevel)
            throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        final Collection[] collections = community.getCollections();
        for (Collection collection : collections) {
            entities.add(new Builder(collection).withFull(includeFullNextLevel).build(level, depth));
        }
        return entities;
    }

    public static Entity buildOwningCollection(Item item, int level,
            final DetailDepth depth, final boolean includeFullNextLevel)
            throws SQLException {
        Entity owningCollection;
        final Collection parent = item.getOwningCollection();
        if (parent == null) {
            owningCollection = null;
        } else {
            owningCollection = new Builder(parent).withFull(includeFullNextLevel).build(level, depth);
        }
        return owningCollection;
    }

    public static List<Entity> build(final int level, final DetailDepth depth, final Collection[] collections)
            throws SQLException {
        final boolean includeFullNextLevel = depth.includeFullDetails(level);
        final List<Entity> entities = new ArrayList<Entity>();
        for (Collection collection : collections) {
            entities.add(new Builder(collection).withFull(includeFullNextLevel).build(level, depth));
        }
        return entities;
    }
    
}