package org.dspace.rest.data.bitstream;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Bitstream;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.FetchGroup;

public class Bitstreams {

    public static List<Entity> build(final int level, final DetailDepth depth, Bitstream[] bst)
            throws SQLException {
        final List<Entity> bitstreams = new ArrayList<Entity>();
        for (Bitstream b : bst) {
            bitstreams.add(depth.includeFullDetails(level) ? new BitstreamEntity(b, level, depth) : new BitstreamEntityId(b));
        }
        return bitstreams;
    }

    private final Context context;
    
    private FetchGroup fetchGroup = FetchGroup.FULL;
    private DetailDepth depth = DetailDepth.STANDARD;
    
    public Bitstreams(Context context) {
        super();
        this.context = context;
    }

    public Bitstreams with(FetchGroup fetchGroup) {
        this.fetchGroup = fetchGroup;
        return this;
    }
    
    private Bitstream fetch(String uid) throws NumberFormatException, SQLException {
        return Bitstream.find(context, Integer.parseInt(uid));
    }

    public BitstreamEntityId build(String uid) throws NumberFormatException, SQLException {
        return new BitstreamBuilder(fetch(uid)).till(depth).with(fetchGroup).build();
    }

    public Bitstreams till(DetailDepth depth) {
        this.depth = depth;
        return this;
    }

}
