/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package org.dspace.rest.entities;

import java.sql.SQLException;

import org.dspace.content.Community;
import org.dspace.core.Context;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;

/**
 * Entity describing community, basic version
 * @see CommunityEntity
 * @see Community
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CommunityEntityId {

    @EntityId
    private int id;

    protected CommunityEntityId() {
    }

    public CommunityEntityId(String uid, Context context) throws SQLException {
        Community res = Community.find(context, Integer.parseInt(uid));
        this.id = res.getID();
        //context.complete();
    }

    public CommunityEntityId(Community community) throws SQLException {
        this.id = community.getID();
    }

    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof CommunityEntityId)) {
            return false;
        } else {
            CommunityEntityId castObj = (CommunityEntityId) obj;
            return (this.id == castObj.id);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }


    @Override
    public String toString() {
        return "id:" + this.id;
    }
}
