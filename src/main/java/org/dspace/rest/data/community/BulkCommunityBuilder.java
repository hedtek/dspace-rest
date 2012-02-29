package org.dspace.rest.data.community;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Community;
import org.dspace.rest.data.base.AbstractBuilder;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.FetchGroup;

public class BulkCommunityBuilder extends AbstractBuilder {

    private final Community[] communities;
    public BulkCommunityBuilder(Community[] communities) {
        super();
        this.communities = communities;
    }

    public BulkCommunityBuilder withIdOnly(boolean idOnly) {
        setIdOnly(idOnly);
        return this;
    }

    public BulkCommunityBuilder withFull(boolean includeFull) {
        return withIdOnly(!includeFull);
    }
    
    public BulkCommunityBuilder with(FetchGroup fetch) {
        setFetchGroup(fetch);
        return this;
    }
    
    public List<Entity> all() throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        for (Community community : communities) {    
            entities.add(new Builder(community).with(getFetchGroup()).build());
        }
        return entities;
    }
    
    public List<Entity> all(int level, final DetailDepth depth) throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        for (Community community : communities) {    
            entities.add(new Builder(community).with(getFetchGroup()).build(level, depth));
        }
        return entities;
    }

    public List<Entity> basic() throws SQLException {
        return with(FetchGroup.BASIC).all();
    }
}