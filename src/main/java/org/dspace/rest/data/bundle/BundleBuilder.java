package org.dspace.rest.data.bundle;

import java.sql.SQLException;

import org.dspace.content.Bundle;
import org.dspace.rest.data.base.AbstractBuilder;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.FetchGroup;

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
        return isIdOnly() ? new BundleEntityId(bundle) : new BundleEntity(bundle, level, depth);
    }
}
