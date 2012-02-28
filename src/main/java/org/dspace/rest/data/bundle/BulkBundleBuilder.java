package org.dspace.rest.data.bundle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Bundle;
import org.dspace.rest.data.base.AbstractBuilder;
import org.dspace.rest.data.base.DetailDepth;

public class BulkBundleBuilder extends AbstractBuilder {

    private final Bundle[] bundles;
    
    private int level = 1;
    private DetailDepth depth = DetailDepth.STANDARD;

    public BulkBundleBuilder(final Bundle[] bundles) {
        super();
        this.bundles = bundles;
    }
    
    public BulkBundleBuilder till(final DetailDepth depth) {
        this.depth = depth;
        return this;
    }
    
    public BulkBundleBuilder on(final int level) {
        this.level = level;
        return this;
    }
    
    public BulkBundleBuilder withFull(boolean includeFullNextLevel) {
        setFull(includeFullNextLevel);
        return this;
    }
    
    public List<BundleEntityId> build() throws SQLException {
        List<BundleEntityId> entities = new ArrayList<BundleEntityId>();
        for (Bundle b : bundles) {
            entities.add(isIdOnly() ? new BundleEntityId(b) : new BundleEntity(b, level, depth));
        }
        return entities;
    }

}
