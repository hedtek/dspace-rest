/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import org.dspace.eperson.EPerson;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;

/**
 * Entity describing users registered on the system, basic version
 * @see UserEntity
 * @see EPerson
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class UserEntityId {
	@EntityId
    final protected int id;

    public UserEntityId(final String id) {
    	this(Integer.parseInt(id));
    }
	
    public UserEntityId(final int id) {
    	this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(final Object obj) {
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
        return id;
    }

    @Override
    public String toString() {
        return "id:" + this.id;
    }
}
