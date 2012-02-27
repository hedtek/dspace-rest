package org.dspace.rest.data.collection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.core.Context;
import org.dspace.rest.data.Pagination;
import org.dspace.rest.data.base.BasicEntity;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.Entity.Type;
import org.dspace.rest.data.community.CommunityEntity;
import org.dspace.rest.data.community.CommunityEntityId;
import org.dspace.rest.data.item.ItemBuilder;
import org.dspace.rest.data.item.ItemWithMetadataEntity;
import org.dspace.rest.params.Parameters;

public class Collections {
    
    private static Logger log = Logger.getLogger(Collections.class);
    
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

        public CollectionEntity full(final DetailDepth depth) throws SQLException {
            return full(1, depth);
        }
        
        public CollectionEntity full(final int level, final DetailDepth depth) throws SQLException {
            // Only include full when above maximum depth
            final int nextLevel = level + 1;
            if (log.isDebugEnabled()) log.debug("Creating collection entity: DepthDetail is " + depth 
                    + "; include full? " + depth.includeFullDetails(nextLevel) + "; next level " + nextLevel);
            final List<Object> items = items(depth, nextLevel);
            return new CollectionEntity(collection, items, communities(nextLevel, depth), items.size());
        }

        public Entity build(final DetailDepth depth) throws SQLException {
            return build(1, depth);
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

        public Entity light(final Pagination pagination) throws SQLException {
            final List<Object> communities = new ArrayList<Object>();
            for (Community community : collection.getCommunities()) {
                communities.add(new BasicEntity(community.getID(), Type.COMMUNITY, community.getName(), community.getType()));
            }
            final List<Object> items = new ArrayList<Object>();
            int itemCount = 0;
            final ItemIterator it = collection.getItems();
            while (it.hasNext()) {
                if (pagination.isInPage(itemCount)) {
                    final Item item = it.next();
                    items.add(new ItemWithMetadataEntity(item));
                } else {
                    it.skip();
                }
                itemCount++;
            }
            return new CollectionWithItemsEntity(collection.getID(), collection.getName(), collection.getType(), 
                    items, communities, itemCount);
        }

    }
    
    private static Collection fetch(final String uid, final Context context)
            throws SQLException {
        final int id = Integer.parseInt(uid);
        return Collection.find(context, id);
    }

    public static Entity build(final String uid, final Context context,
            final DetailDepth depth, final boolean idOnly) throws SQLException {
        return new Builder(fetch(uid, context)).withIdOnly(idOnly).build(depth);
    }

    public static Entity collectionWithItems(String id, Context context, Pagination pagination) throws SQLException {
        return new Builder(fetch(id, context)).light(pagination);
    }

    public static CollectionEntity collection(String id, Context context,
            final DetailDepth depth) throws SQLException {
        return new Builder(fetch(id, context)).full(depth);
    }

    public static List<Entity> build(final Parameters parameters, final Collection[] collections) throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        final boolean idOnly = parameters.getEntityBuild().isIdOnly();
        for (Collection collection : collections) {
            entities.add(new Builder(collection).withIdOnly(idOnly).build(DetailDepth.FOR_ALL_INDEX));
        }
        return entities;
    }

    public static List<Entity> collections(Community community, int level, final DetailDepth depth)
            throws SQLException {
        final Collection[] collections = community.getCollections();
        final List<Entity> entities = new ArrayList<Entity>();
        for (Collection collection : collections) {
            entities.add(new Builder(collection).withFull(depth.includeFullDetails(level + 1)).build(level, depth));
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