/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;
import org.dspace.eperson.EPerson;
import org.dspace.core.Context;
import java.sql.SQLException;

/**
 * Entity describing users registered on the system, basic version
 * @see UserEntity
 * @see EPerson
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class UserEntityId implements Comparable {

    @EntityId
    protected int id;
    protected EPerson res;

    protected UserEntityId() {
    }

    public UserEntityId(String uid, Context context) throws SQLException {
        res = EPerson.find(context, Integer.parseInt(uid));
        this.id = res.getID();
        //context.complete();
    }

    public UserEntityId(EPerson eperson) throws SQLException {
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
        if (!(obj instanceof UserEntityId)) {
            return false;
        } else {
            UserEntityId castObj = (UserEntityId) obj;
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
        if (((UserEntityId) (o1)).getId() > this.getId()) {
            return -1;
        } else if (((UserEntityId) (o1)).getId() < this.getId()) {
            return 1;
        } else {
            return 0;
        }
    }
}
