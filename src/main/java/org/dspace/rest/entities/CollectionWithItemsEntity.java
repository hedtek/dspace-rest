package org.dspace.rest.entities;

import java.util.List;

public class CollectionWithItemsEntity extends BasicEntity {

    private final int countItems;
    private final List<Object> items;
    private final List<Object> communities;

    public CollectionWithItemsEntity(int id, String name,
            int dspaceEntityTypeId, final List<Object> items,
            final List<Object> communities, final int itemsCount) {
        super(id, Type.COLLECTION, name, dspaceEntityTypeId);
        
        this.communities = communities;
        this.items = items;
        this.countItems = itemsCount;
    }

    public final List<?> getItems() {
        return this.items;
    }

    public final List<?> getCommunities() {
        return this.communities;
    }

    public final int getCountItems() {
        return this.countItems;
    }

}