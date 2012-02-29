package org.dspace.rest.data.bitstream;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Bitstream;
import org.dspace.rest.data.base.DetailDepth;

public class Bitstreams {

    public static List<Object> build(final int level, final DetailDepth depth, Bitstream[] bst)
            throws SQLException {
        List<Object> bitstreams = new ArrayList<Object>();
        for (Bitstream b : bst) {
            bitstreams.add(depth.includeFullDetails(level) ? new BitstreamEntity(b, level, depth) : new BitstreamEntityId(b));
        }
        return bitstreams;
    }

}
