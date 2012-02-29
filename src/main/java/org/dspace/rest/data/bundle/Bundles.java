package org.dspace.rest.data.bundle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Bundle;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.FetchGroup;
import org.dspace.rest.data.item.ItemBuilder;

public class Bundles {

    private final Context context;
    private FetchGroup fetchGroup = FetchGroup.FULL;
    
    public Bundles(Context context) {
        super();
        this.context = context;
    }

    public Bundles with(FetchGroup fetchGroup) {
        this.fetchGroup  = fetchGroup;
        return this;
    }

    private Bundle fetch(String uid) throws NumberFormatException, SQLException {
        return Bundle.find(context, Integer.parseInt(uid));
    }
    
    public BundleEntityId build(String uid) throws SQLException {
        return new BundleBuilder(fetch(uid)).with(fetchGroup).build();
    }

    public static List<Object> bundles(int level, final DetailDepth depth,
            final boolean includeFullNextLevel, final Bundle[] bundles)
            throws SQLException {
        final List<Object> entities = new ArrayList<Object>();
        for (Bundle bundle : bundles) {
            entities.add(new BundleBuilder(bundle).withFull(includeFullNextLevel).on(level).till(depth).build());
        }
        return entities;
    }
}
