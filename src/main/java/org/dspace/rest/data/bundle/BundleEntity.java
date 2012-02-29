/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.data.bundle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Bitstream;
import org.dspace.content.Bundle;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.bitstream.BitstreamEntity;
import org.dspace.rest.data.bitstream.BitstreamEntityId;
import org.dspace.rest.data.item.Items;

/**
 * Entity describing bundle
 * @see BundleEntityId
 * @see Bundle
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BundleEntity extends BundleEntityId {
    
    private static List<Object> build(int level, final DetailDepth depth,
            final boolean includeFullNextLevel, Bitstream[] bst)
            throws SQLException {
        List<Object> bitstreams = new ArrayList<Object>();
        for (Bitstream b : bst) {
            bitstreams.add(includeFullNextLevel ? new BitstreamEntity(b, level, depth) : new BitstreamEntityId(b));
        }
        return bitstreams;
    }

    private final String name;
    private final String handle;
    private final int type;
    private final int pid;
    private final List<Object> bitstreams;
    private final List<Object> items;

    public BundleEntity(String uid, Context context, final DetailDepth depth) throws SQLException {
        this(Bundle.find(context, Integer.parseInt(uid)), 1, depth);
    }

    public BundleEntity(Bundle bundle, int level, final DetailDepth depth) throws SQLException {
        super(bundle);
        // Only include full when above maximum depth
        final boolean includeFullNextLevel = depth.includeFullDetails(++level);

        this.handle = bundle.getHandle();
        this.name = bundle.getName();
        this.type = bundle.getType();
        this.pid = bundle.getPrimaryBitstreamID();
        
        this.bitstreams = build(level, depth, includeFullNextLevel, bundle.getBitstreams());
        this.items = Items.build(level, depth, includeFullNextLevel, bundle.getItems());
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