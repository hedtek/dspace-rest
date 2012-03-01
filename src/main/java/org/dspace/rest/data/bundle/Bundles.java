package org.dspace.rest.data.bundle;

import java.sql.SQLException;

import org.dspace.content.Bundle;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.FetchGroup;

public class Bundles {

    private final Context context;
    private FetchGroup fetchGroup = FetchGroup.FULL;
    private DetailDepth depth = DetailDepth.STANDARD;
    
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
        return new BundleBuilder(fetch(uid)).with(fetchGroup).till(depth).build();
    }

    public Bundles till(DetailDepth depth) {
        this.depth = depth;
        return this;
    }
}
