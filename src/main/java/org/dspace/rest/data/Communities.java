package org.dspace.rest.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Community;
import org.dspace.core.Context;
import org.dspace.rest.entities.CommunityEntity;
import org.dspace.rest.entities.CommunityEntityId;
import org.dspace.rest.entities.DetailDepth;
import org.dspace.rest.entities.Entity;

public class Communities {

    private static class Builder {
        private final Community community;

        private boolean idOnly = false;
        
        public Builder(final Community community) {
            super();
            this.community = community;
        }

        public boolean isIdOnly() {
            return idOnly;
        }

        public void setIdOnly(boolean idOnly) {
            this.idOnly = idOnly;
        }
        
        public Builder withIdOnly(boolean idOnly) {
            setIdOnly(idOnly);
            return this;
        }

        public Builder withFull(final boolean includeFull) {
            return withIdOnly(!includeFull);
        }
        
        public CommunityEntityId build() throws SQLException {
            return build(DetailDepth.FOR_ALL_INDEX);
        }
        
        public CommunityEntityId build(final DetailDepth depth) throws SQLException {
            return build(1, depth);
        }

        public CommunityEntityId build(final int level, final DetailDepth depth) throws SQLException {
            if (isIdOnly()) {
                return new CommunityEntityId(community);
            } else {
                return new CommunityEntity(community, level, depth);
            }
        }
        
        public List<Entity> subcommunities(int level, final DetailDepth depth)
                throws SQLException {
            final List<Entity> subCommunities = new ArrayList<Entity>();
            final Community[] subcommunities = community.getSubcommunities();
            for (Community subcommunity : subcommunities) {
                subCommunities.add(depth.includeFullDetails(level) ? new CommunityEntity(subcommunity, level, depth) : new CommunityEntityId(subcommunity));
            }
            return subCommunities;
        }

        public Entity parent(int level, final DetailDepth depth) throws SQLException {
            final Entity parent;
            final Community parentCommunity = community.getParentCommunity();
            if(parentCommunity == null) {
                parent = null;
            } else {
                parent = new Builder(parentCommunity).withFull(depth.includeFullDetails(level)).build(level, depth);
            }
            return parent;
        }
    }
    
    public static List<Object> build(final Context context,
            final boolean topLevelOnly, final boolean idOnly)
            throws SQLException {
        return build(idOnly, all(context, topLevelOnly));
    }

    private static List<Object> build(final boolean idOnly,
            final Community[] communities) throws SQLException {
        final List<Object> entities = new ArrayList<Object>();
        for (Community community : communities) {    
            entities.add(new Builder(community).withIdOnly(idOnly).build());
        }
        return entities;
    }

    private static Community[] all(final Context context,
            final boolean topLevelOnly) throws SQLException {
        return topLevelOnly ? Community.findAllTop(context) : Community.findAll(context);
    }

    public static CommunityEntityId build(final String uid, final Context context,
            final DetailDepth depth, final boolean idOnly) throws SQLException {
        if (idOnly) {
            final Community community = Community.find(context, Integer.parseInt(uid));
            return new CommunityEntityId(community);
        } else {
            return build(uid, context, 1, depth);
        }
    }

    public static CommunityEntity community(String id, Context context,
            final DetailDepth depth) throws SQLException {
        return build(id, context, 1, depth);
    }

    public static CommunityEntity build(String uid, Context context, int level, final DetailDepth depth) throws SQLException {
        final Community community = Community.find(context, Integer.parseInt(uid));
        return new CommunityEntity(community, context, level, depth);
    }

    public static List<Entity> subcommunities(Community community, int level, final DetailDepth depth) throws SQLException {
        return new Builder(community).subcommunities(level, depth);
    }

    public static Entity parent(final int level, final DetailDepth depth, final Community community) throws SQLException {
        return new Builder(community).parent(level, depth);
    }
}
