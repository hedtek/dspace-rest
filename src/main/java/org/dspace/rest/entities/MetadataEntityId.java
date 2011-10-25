/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package org.dspace.rest.entities;

import org.dspace.content.DCValue;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;

import java.sql.SQLException;

/**
 * Entity describing DCValue, basic version
 * @see org.dspace.rest.entities.MetadataEntityId
 * @see org.dspace.content.DCValue
 * @author Bojan Suzic, bojan.suzic@gmail.com
 * @author Robin Taylor
 */
public class MetadataEntityId
{

    @EntityId
    private int id;

    protected MetadataEntityId() {
    }

    public MetadataEntityId(String uid, Context context) throws SQLException {
        // DCValue is not directly related a a database table so we have no persistent id to return.

    }

    public MetadataEntityId(DCValue dcValue) throws SQLException {
        // DCValue is not directly related a a database table so we have no persistent id to return.

    }

    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof MetadataEntityId)) {
            return false;
        } else {
            MetadataEntityId castObj = (MetadataEntityId) obj;
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
