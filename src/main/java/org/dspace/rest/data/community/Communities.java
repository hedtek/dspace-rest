package org.dspace.rest.data.community;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.Community;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.Fetch;

public class Communities {
    
    public static Entity fetch(final String uid, final Context context,
            final DetailDepth depth, final boolean idOnly) throws SQLException {
        return new Builder(fetch(uid, context)).withIdOnly(idOnly).build();
    }

    private static Community fetch(final String uid, final Context context) throws SQLException {
        return Community.find(context, Integer.parseInt(uid));
    }

    public static CommunityEntity community(String id, Context context,
            final DetailDepth depth) throws SQLException {
        return build(id, context, 1, depth);
    }

    public static CommunityEntity build(String uid, Context context, int level, final DetailDepth depth) throws SQLException {
        final Community community = fetch(uid, context);
        return new CommunityEntity(community, context, level, depth);
    }

    public static List<Entity> subcommunities(Community community, int level, final DetailDepth depth) throws SQLException {
        return new Builder(community).subcommunities(level, depth);
    }

    public static Entity parent(final int level, final DetailDepth depth, final Community community) throws SQLException {
        return new Builder(community).parent(level, depth);
    }
    
    private final Context context;

    public Communities(Context context) {
        super();
        this.context = context;
    }
    
    public List<Entity> get(final boolean onlyTopLevel, final Fetch fetch) throws SQLException {
        return new BulkBuilder(get(onlyTopLevel)).with(fetch).all();
    }

    private Community[] get(final boolean onlyTopLevel) throws SQLException {
        return onlyTopLevel ? Community.findAllTop(context) : Community.findAll(context);
    }
}
