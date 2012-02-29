/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.data.bundle;

import java.util.List;

import org.dspace.content.Bundle;
import org.dspace.rest.data.base.DetailDepth;

/**
 * Entity describing bundle
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BundleEntity extends BundleEntityId {
    
    private final String name;
    private final String handle;
    private final int type;
    private final int pid;
    private final List<Object> bitstreams;
    private final List<Object> items;

    BundleEntity(Bundle bundle, int level, final DetailDepth depth, List<Object> items, List<Object> bitstreams) {
        super(bundle);

        this.bitstreams = bitstreams;
        this.items = items;
        this.handle = bundle.getHandle();
        this.name = bundle.getName();
        this.type = bundle.getType();
        this.pid = bundle.getPrimaryBitstreamID();
    }
    
    public List<Object> getItems() {
        return this.items;
    }

    public int getPrimaryBitstreamId() {
        return this.pid;
    }

    public String getName() {
        return this.name;
    }

    public String getHandle() {
        return this.handle;
    }

    public int getType() {
        return this.type;
    }
    
    public List<Object> getBitstreams() {
        return this.bitstreams;
    }
}
