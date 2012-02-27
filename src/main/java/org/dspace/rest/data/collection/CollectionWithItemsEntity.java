package org.dspace.rest.data.collection;

import java.util.List;

import org.dspace.rest.data.base.Entity;

public class CollectionWithItemsEntity extends CollectionWithNoItems {

    private final List<Object> items;
    private final List<Entity> communities;

    public CollectionWithItemsEntity(int id, String name,
            int dspaceEntityTypeId, final List<Object> items,
            final List<Entity> communities, final int itemsCount) {
        super(id, name, dspaceEntityTypeId, itemsCount);
        
        this.items = items;
        this.communities = communities;
    }

    public final List<Entity> getCommunities() {
        return this.communities;
    }

    public final List<?> getItems() {
        return this.items;
    }
}