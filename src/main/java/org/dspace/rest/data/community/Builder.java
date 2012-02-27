package org.dspace.rest.data.community;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Community;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;

class Builder extends AbstractBuilder {
    
    private final Community community;
    
    public Builder(final Community community) {
        super();
        this.community = community;
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