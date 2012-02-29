package org.dspace.rest.data.collection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.rest.data.base.BasicEntity;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.Entity.Type;
import org.dspace.rest.data.base.Pagination;
import org.dspace.rest.data.community.Communities;
import org.dspace.rest.data.item.BulkItemBuilder;
import org.dspace.rest.data.item.ItemWithMetadataEntity;

public class CollectionBuilder {
    
    private static Logger log = Logger.getLogger(CollectionBuilder.class);
    
    private final Collection collection;
    private boolean isIdOnly = false;

    public CollectionBuilder(Collection collection) {
        super();
        this.collection = collection;
    }
    
    public boolean isIdOnly() {
        return isIdOnly;
    }

    public void setIdOnly(boolean isIdOnly) {
        this.isIdOnly = isIdOnly;
    }

    public CollectionBuilder withIdOnly(boolean isIdOnly) {
        this.isIdOnly = isIdOnly;
        return this;
    }
    
    public CollectionBuilder withFull(boolean isFullNextLevel) {
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
        final List<Entity> items = items(depth, nextLevel);
        return new CollectionEntity(collection, items, Communities.toEntities(nextLevel, depth, collection.getCommunities()), items.size());
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

    private List<Entity> items(final DetailDepth depth, final int nextLevel) throws SQLException {
        final boolean includeFullNextLevel = depth.includeFullDetails(nextLevel);
        return BulkItemBuilder.builder(!includeFullNextLevel, depth).build(collection.getItems(), nextLevel);
    }

    public Entity light(final Pagination pagination) throws SQLException {
        final List<Entity> communities = new ArrayList<Entity>();
        for (Community community : collection.getCommunities()) {
            communities.add(new BasicEntity(community.getID(), Type.COMMUNITY, community.getName(), community.getType()));
        }
        final List<Entity> items = new ArrayList<Entity>();
        int itemCount = 0;
        final ItemIterator it = collection.getItems();
        while (it.hasNext()) {
            if (pagination.isInPage(++itemCount)) {
                final Item item = it.next();
                items.add(new ItemWithMetadataEntity(item));
            } else {
                it.skip();
            }
        }
        return new CollectionWithItemsEntity(collection.getID(), collection.getName(), collection.getType(), 
                items, communities, itemCount);
    }

    public Entity withNoItems() throws SQLException {
        int itemCount = 0;
        final ItemIterator it = collection.getItems();
        while (it.hasNext()) {
            it.skip();
            itemCount++;
        }
        return new CollectionWithNoItems(collection.getID(), collection.getName(), collection.getType(), itemCount);
    }

    public Entity minimal() throws SQLException {
        if (collection == null) {
            return null;
        }
        return new BasicEntity(collection.getID(), Type.COLLECTION, collection.getName(), collection.getType());
    }
    

    public Entity basicCommunities() throws SQLException {
        if (collection == null) {
            return null;
        }
        final List<Entity> communities = new ArrayList<Entity>();
        for (Community community : collection.getCommunities()) {
            communities.add(new BasicEntity(community.getID(), Type.COMMUNITY, community.getName(), community.getType()));
        }
        return new CollectionWithCommunities(collection.getID(), collection.getName(), collection.getType(), communities);
    }
}