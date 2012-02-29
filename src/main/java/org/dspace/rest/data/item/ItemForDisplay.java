package org.dspace.rest.data.item;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.Item;
import org.dspace.rest.data.base.Entity;

public class ItemForDisplay extends ItemWithMetadataEntity {

    private final List<Entity> bundles;
    private final List<Entity> communities;
    private final Entity owningCollection;
    private final boolean isArchived;

    public ItemForDisplay(Item item, 
            final Entity owningCollection, 
            final List<Entity> bundles, 
            List<Entity> communities) throws SQLException {
        super(item);
        this.owningCollection = owningCollection;
        this.bundles = bundles;
        this.communities = communities;
        this.isArchived = item.isArchived();
    }

    public final boolean getIsArchived() {
        return this.isArchived;
    }

    public final Entity getOwningCollection() {
        return this.owningCollection;
    }

    public final List<Entity> getCommunities() {
        return this.communities;
    }

    public final List<Entity> getBundles() {
        return this.bundles;
    }

}