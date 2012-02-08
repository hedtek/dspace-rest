/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import java.sql.SQLException;

import org.dspace.core.Context;
import org.dspace.eperson.Group;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;

/**
 * Entity describing users registered on the system, basic version
 * @see GroupEntity
 * @see EGroup
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class GroupEntityId implements Comparable {

    @EntityId
    protected int id;
    protected Group res;

    protected GroupEntityId() {
    }

    public GroupEntityId(String uid, Context context) throws SQLException {
        res = Group.find(context, Integer.parseInt(uid));
        this.id = res.getID();
        //context.complete();
    }

    public GroupEntityId(Group eperson) throws SQLException {
        this.id = eperson.getID();
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof GroupEntityId)) {
            return false;
        } else {
            GroupEntityId castObj = (GroupEntityId) obj;
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

    public int compareTo(Object o1) {
        if (((GroupEntityId) (o1)).getId() > this.getId()) {
            return -1;
        } else if (((GroupEntityId) (o1)).getId() < this.getId()) {
            return 1;
        } else {
            return 0;
        }
    }
}
