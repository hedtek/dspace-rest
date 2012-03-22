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
import org.dspace.rest.data.base.Entity;

/**
 * Entity describing bundle
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BundleEntity extends BundleNoItems {
    
    private final String handle;
    private final int pid;
    private final List<Object> items;

    BundleEntity(Bundle bundle, List<Object> items, List<Entity> bitstreams) {
        super(bundle, bitstreams);
        this.items = items;
        this.handle = bundle.getHandle();
        this.pid = bundle.getPrimaryBitstreamID();
    }
    
    public List<Object> getItems() {
        return this.items;
    }

    public int getPrimaryBitstreamId() {
        return this.pid;
    }

    public String getHandle() {
        return this.handle;
    }
}
