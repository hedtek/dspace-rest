package org.dspace.rest.data.bundle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Bundle;
import org.dspace.rest.data.base.AbstractBuilder;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;

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
    
    public List<Entity> build() throws SQLException {
        List<Entity> entities = new ArrayList<Entity>();
        for (Bundle bundle : bundles) {
            entities.add(new BundleBuilder(bundle).till(depth).on(level).build());
        }
        return entities;
    }

    public List<Entity> noItems() throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        for (Bundle bundle : bundles) {
            entities.add(new BundleBuilder(bundle).noItems());
        }
        return entities;
    }
}
