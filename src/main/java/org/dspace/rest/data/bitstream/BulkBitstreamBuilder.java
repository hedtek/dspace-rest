package org.dspace.rest.data.bitstream;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Bitstream;
import org.dspace.rest.data.base.DetailDepth;

public class BulkBitstreamBuilder {

    public static List<BitstreamEntityId> bitstreams(int level,
            final DetailDepth depth, final boolean includeFullNextLevel,
            final Bitstream[] bst) throws SQLException {
        final List<BitstreamEntityId> bitstreams = new ArrayList<BitstreamEntityId>();
        for (Bitstream b : bst) {
            bitstreams.add(includeFullNextLevel ? new BitstreamEntity(b, level, depth) : new BitstreamEntityId(b));
        }
        return bitstreams;
    }

}
