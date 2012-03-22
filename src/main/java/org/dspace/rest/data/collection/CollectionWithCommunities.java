package org.dspace.rest.data.collection;

import java.util.List;

import org.dspace.rest.data.base.BasicEntity;
import org.dspace.rest.data.base.Entity;

public class CollectionWithCommunities extends BasicEntity {

    private final List<Entity> communities;

    public CollectionWithCommunities(int id, String name,
            int dspaceEntityTypeId,
            final List<Entity> communities ) {
        super(id, Type.COLLECTION, name, dspaceEntityTypeId);
        this.communities = communities;
    }

    public final List<Entity> getCommunities() {
        return this.communities;
    }
}