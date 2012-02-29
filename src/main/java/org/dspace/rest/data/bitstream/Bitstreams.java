package org.dspace.rest.data.bitstream;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Bitstream;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;

public class Bitstreams {

    public static List<Entity> build(final int level, final DetailDepth depth, Bitstream[] bst)
            throws SQLException {
        final List<Entity> bitstreams = new ArrayList<Entity>();
        for (Bitstream b : bst) {
            bitstreams.add(depth.includeFullDetails(level) ? new BitstreamEntity(b, level, depth) : new BitstreamEntityId(b));
        }
        return bitstreams;
    }

}
