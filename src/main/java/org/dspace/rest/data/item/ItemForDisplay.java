package org.dspace.rest.data.item;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.Item;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.bundle.BundleEntityId;

public class ItemForDisplay extends ItemWithMetadataEntity {

    private final List<Entity> bundles;
    private final List<Entity> bitstreams;
    private final List<Entity> communities;
    private final Entity owningCollection;
    private final boolean isArchived;

    public ItemForDisplay(Item item, 
            final Entity owningCollection, 
            final List<Entity> bundles, 
            List<Entity> bitstreams, 
            List<Entity> communities) throws SQLException {
        super(item);
        this.owningCollection = owningCollection;
        this.bundles = bundles;
        this.bitstreams = bitstreams;
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

    public final List<Entity> getBitstreams() {
        return this.bitstreams;
    }

    public final List<Entity> getBundles() {
        return this.bundles;
    }

}