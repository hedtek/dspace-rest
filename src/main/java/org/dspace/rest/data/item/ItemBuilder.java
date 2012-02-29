package org.dspace.rest.data.item;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.Item;
import org.dspace.rest.data.base.AbstractBuilder;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.FetchGroup;
import org.dspace.rest.data.bitstream.BulkBitstreamBuilder;
import org.dspace.rest.data.bundle.BulkBundleBuilder;
import org.dspace.rest.data.bundle.BundleEntityId;
import org.dspace.rest.data.collection.CollectionBuilder;
import org.dspace.rest.data.collection.Collections;
import org.dspace.rest.data.community.BulkCommunityBuilder;
import org.dspace.rest.data.community.Communities;

public class ItemBuilder extends AbstractBuilder {
    
    private final Item item;

    private DetailDepth depth  = DetailDepth.STANDARD;
    private int level = 1;
    
    public ItemBuilder(Item item) {
        super();
        this.item = item;
    }
    
    public ItemBuilder till(final DetailDepth depth) {
        this.depth = depth;
        return this;
    }
    
    public ItemBuilder on(final int level) {
        this.level = level;
        return this;
    }
    
    public ItemEntity buildFull() throws SQLException {
        final int nextLevel = level + 1;
        final List<BundleEntityId> bundles 
            = new BulkBundleBuilder(item.getBundles()).till(depth).on(nextLevel).withFull(depth.includeFullDetails(nextLevel)).build();
        final List<Entity> bitstreams = new BulkBitstreamBuilder(item.getNonInternalBitstreams()).on(nextLevel).till(depth).build();
        final List<Entity> communities = Communities.toEntities(nextLevel, depth, item.getCommunities());
        final Entity owningCollection = Collections.owner(item, nextLevel, depth);
        return new ItemEntity(item, level, depth, owningCollection, bundles, bitstreams, communities);
    }

    public ItemBuilder with(FetchGroup fetchGroup) {
        setFetchGroup(fetchGroup);
        return this;
    }
    
    public Entity build() throws SQLException {
        switch (getFetchGroup()) {
        case MINIMAL:
            return new ItemEntityId(item);
        case LIGHT:
            return new ItemWithMetadataEntity(item);
        case DISPLAY:
            return forDisplay();
        default:
            return buildFull();
        }
    }

    private Entity forDisplay() throws SQLException {
        final int nextLevel = level + 1;
        final List<BundleEntityId> bundles 
            = new BulkBundleBuilder(item.getBundles()).till(depth).on(nextLevel).withFull(depth.includeFullDetails(nextLevel)).build();
        final List<Entity> bitstreams = new BulkBitstreamBuilder(item.getNonInternalBitstreams()).on(nextLevel).till(depth).build();
        final List<Entity> communities = new BulkCommunityBuilder(item.getCommunities()).basic();
        final Entity owningCollection = new CollectionBuilder(item.getOwningCollection()).minimal();
        return new ItemForDisplay(item, owningCollection, bundles, bitstreams, communities);
    }


    
    public ItemBuilder withFull(boolean includeFullDetails) {
        setFull(includeFullDetails);
        return this;
    }

    public ItemBuilder withIdOnly(boolean idOnly) {
        setIdOnly(idOnly);
        return this;
    }
}
