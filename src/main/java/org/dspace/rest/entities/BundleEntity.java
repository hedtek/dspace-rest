/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Bitstream;
import org.dspace.content.Bundle;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityFieldRequired;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;

/**
 * Entity describing bundle
 * @see BundleEntityId
 * @see Bundle
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BundleEntity extends BundleEntityId {

    @EntityId
    private int id;
    @EntityFieldRequired
    private String name;
    private String handle;
    private int type, pid;
    List<Object> bitstreams = new ArrayList<Object>();
    List<Object> items = new ArrayList<Object>();

    public BundleEntity(String uid, Context context, int level, final DetailDepth depth) throws SQLException {
        this(Bundle.find(context, Integer.parseInt(uid)), level, depth);
    }

    public BundleEntity(Bundle bundle, int level, final DetailDepth depth) throws SQLException {
        // Only include full when above maximum depth
        final boolean includeFullNextLevel = depth.includeFullDetails(++level);

        this.handle = bundle.getHandle();
        this.name = bundle.getName();
        this.type = bundle.getType();
        this.id = bundle.getID();
        this.pid = bundle.getPrimaryBitstreamID();
        Bitstream[] bst = bundle.getBitstreams();
        Item[] itm = bundle.getItems();
        for (Bitstream b : bst) {
            this.bitstreams.add(includeFullNextLevel ? new BitstreamEntity(b, level, depth) : new BitstreamEntityId(b));
        }
        for (Item i : itm) {
            this.items.add(includeFullNextLevel ? new ItemEntity(i, level, depth) : new ItemEntityId(i));
        }
    }

    public List<?> getItems() {
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

    @Override
    public int getId() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }

    public List getBitstreams() {
        return this.bitstreams;
    }

    @Override
    public String toString() {
        return "id:" + this.id + ", stuff.....";
    }
}
