package org.dspace.rest.data.community;

import java.util.List;

import org.dspace.content.Community;
import org.dspace.rest.data.base.Entity;

public class LightCommunity extends CommunityEntityId {

    private final String name;
    private final int type;
    private final List<Entity> collections;

    LightCommunity(Community community, final List<Entity> collections) {
        super(community.getID());
        this.name = community.getName();
        this.type = community.getType();
        this.collections = collections;
    }

    public final List<Entity> getCollections() {
        return this.collections;
    }

    public final String getName() {
        return this.name;
    }

    public final int getType() {
        return this.type;
    }

}