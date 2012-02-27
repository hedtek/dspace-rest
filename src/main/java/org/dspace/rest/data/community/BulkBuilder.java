package org.dspace.rest.data.community;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Community;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.Fetch;

class BulkBuilder extends AbstractBuilder {

    private final Community[] communities;
    BulkBuilder(Community[] communities) {
        super();
        this.communities = communities;
    }

    public BulkBuilder withIdOnly(boolean idOnly) {
        setIdOnly(idOnly);
        return this;
    }

    public BulkBuilder with(Fetch fetch) {
        setFetch(fetch);
        return this;
    }
    
    public List<Entity> all() throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        for (Community community : communities) {    
            entities.add(new Builder(community).withIdOnly(isIdOnly()).build());
        }
        return entities;
    }
    
}