package org.dspace.rest.data.item;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.Item;
import org.dspace.rest.data.base.AbstractBuilder;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.FetchGroup;
import org.dspace.rest.data.bitstream.BitstreamEntityId;
import org.dspace.rest.data.bitstream.BulkBitstreamBuilder;
import org.dspace.rest.data.bundle.BulkBundleBuilder;
import org.dspace.rest.data.bundle.BundleEntityId;
import org.dspace.rest.data.collection.Collections;
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
        final List<Entity> bitstreams = BulkBitstreamBuilder.bitstreams(nextLevel, depth,
                depth.includeFullDetails(nextLevel), item.getNonInternalBitstreams());
        final List<Entity> communities = Communities.toEntities(level + 1, depth, item.getCommunities());
        return new ItemEntity(item, level, depth, Collections.buildOwningCollection(item, nextLevel, depth), bundles, bitstreams, communities);
    }

    public ItemBuilder with(FetchGroup fetchGroup) {
        setFetch(fetchGroup);
        return this;
    }
    
    public Entity build() throws SQLException {
        switch (getFetch()) {
        case MINIMAL:
            return new ItemEntityId(item);
        case LIGHT:
            return new ItemWithMetadataEntity(item);
        default:
            return buildFull();
        }
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
