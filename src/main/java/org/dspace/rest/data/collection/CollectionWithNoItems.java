package org.dspace.rest.data.collection;

import org.dspace.rest.data.base.BasicEntity;

public class CollectionWithNoItems extends BasicEntity {

    private final int countItems;

    public CollectionWithNoItems(int id, String name,
            int dspaceEntityTypeId, final int itemsCount) {
        super(id, Type.COLLECTION, name, dspaceEntityTypeId);
        this.countItems = itemsCount;
    }


    public final int getCountItems() {
        return this.countItems;
    }

}