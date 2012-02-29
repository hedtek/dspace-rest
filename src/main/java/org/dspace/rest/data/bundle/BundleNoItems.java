package org.dspace.rest.data.bundle;

import java.util.List;

import org.dspace.content.Bundle;

public class BundleNoItems extends BundleEntityId {

    private final String name;
    private final int type;
    private final List<Object> bitstreams;

    public BundleNoItems(Bundle bundle, List<Object> bitstreams) {
        super(bundle.getID());
        this.bitstreams = bitstreams;
        this.name = bundle.getName();
        this.type = bundle.getType();
    }

    public String getName() {
        return this.name;
    }

    public int getType() {
        return this.type;
    }

    public List<Object> getBitstreams() {
        return this.bitstreams;
    }

}