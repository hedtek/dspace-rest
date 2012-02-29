package org.dspace.rest.data.bitstream;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Bitstream;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;

public class BulkBitstreamBuilder {

    public static List<Entity> bitstreams(int level,
            final DetailDepth depth, final boolean includeFullNextLevel,
            final Bitstream[] bst) throws SQLException {
        final List<Entity> bitstreams = new ArrayList<Entity>();
        for (Bitstream b : bst) {
            bitstreams.add(includeFullNextLevel ? new BitstreamEntity(b, level, depth) : new BitstreamEntityId(b));
        }
        return bitstreams;
    }

}
