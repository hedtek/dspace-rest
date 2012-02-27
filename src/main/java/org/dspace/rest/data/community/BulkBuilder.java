package org.dspace.rest.data.community;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Community;
import org.dspace.rest.data.base.Entity;

class BulkBuilder {

    private final Community[] communities;
    private boolean idOnly = false;
    
    BulkBuilder(Community[] communities) {
        super();
        this.communities = communities;
    }


    public boolean isIdOnly() {
        return idOnly;
    }

    public void setIdOnly(boolean idOnly) {
        this.idOnly = idOnly;
    }
    
    public BulkBuilder withIdOnly(boolean idOnly) {
        setIdOnly(idOnly);
        return this;
    }
    
    
    public List<Entity> all() throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        for (Community community : communities) {    
            entities.add(new Builder(community).withIdOnly(idOnly).build());
        }
        return entities;
    }
    
}