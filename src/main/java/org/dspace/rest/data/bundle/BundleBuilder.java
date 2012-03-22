package org.dspace.rest.data.bundle;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.Bundle;
import org.dspace.rest.data.base.AbstractBuilder;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.FetchGroup;
import org.dspace.rest.data.bitstream.BulkBitstreamBuilder;
import org.dspace.rest.data.item.Items;

public class BundleBuilder extends AbstractBuilder {

    private final Bundle bundle;
        
    private int level = 1;
    private DetailDepth depth = DetailDepth.STANDARD;

    public BundleBuilder(Bundle bundle) {
        super();
        this.bundle = bundle;
    }
    
    public BundleBuilder with(final FetchGroup fetchGroup){
        setFetchGroup(fetchGroup);
        return this;
    }
    
    public BundleBuilder till(final DetailDepth depth) {
        this.depth = depth;
        return this;
    }
    
    public BundleBuilder on(final int level) {
        this.level = level;
        return this;
    }
    
    public BundleEntityId build() throws SQLException {
        return isIdOnly() ? new BundleEntityId(bundle.getID()) : full();
    }

    private BundleEntity full() throws SQLException {
        final int nextLevel = level + 1;
        final List<Object> items = Items.build(nextLevel, depth, bundle.getItems());
        final List<Entity> bitstreams =  new BulkBitstreamBuilder(bundle.getBitstreams()).till(depth).on(nextLevel).build();
        return new BundleEntity(bundle, items, bitstreams);
    }

    public BundleBuilder withFull(boolean includeFullNextLevel) {
        setFull(includeFullNextLevel);
        return this;
    }

    public Entity noItems() throws SQLException {
        final List<Entity> bitstreams = new BulkBitstreamBuilder(bundle.getBitstreams()).noBundles();
        return new BundleNoItems(bundle, bitstreams);
    }
}
