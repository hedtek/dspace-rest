package org.dspace.rest.data.community;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Community;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;

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
                subCommunities.add(toEntity(level, depth, subcommunity));
            }
            return subCommunities;
        }

        public Entity parent(int level, final DetailDepth depth) throws SQLException {
            final Entity parent;
            final Community parentCommunity = community.getParentCommunity();
            if(parentCommunity == null) {
                parent = null;
            } else {
                parent = toEntity(level, depth, parentCommunity);
            }
            return parent;
        }

        private Entity toEntity(int level, final DetailDepth depth, final Community community) throws SQLException {
            return new Builder(community).withFull(depth.includeFullDetails(level)).build(level, depth);
        }
    }
    
    public static List<Entity> build(final Context context, final boolean topLevelOnly, final boolean idOnly)
            throws SQLException {
        return build(idOnly, all(context, topLevelOnly));
    }

    private static List<Entity> build(final boolean idOnly, final Community[] communities) throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        for (Community community : communities) {    
            entities.add(new Builder(community).withIdOnly(idOnly).build());
        }
        return entities;
    }

    private static Community[] all(final Context context,
            final boolean topLevelOnly) throws SQLException {
        return topLevelOnly ? Community.findAllTop(context) : Community.findAll(context);
    }

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
}
