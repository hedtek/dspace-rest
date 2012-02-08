/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;


import java.sql.SQLException;

import org.dspace.content.Collection;
import org.dspace.core.Context;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;

/**
 * Entity describing collection, basic version
 * @see CollectionEntity
 * @see Collection
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CollectionEntityId  {

    @EntityId
    private int id;

    protected CollectionEntityId() {
    }

    public CollectionEntityId(String uid, Context context) throws SQLException {
        Collection res = Collection.find(context, Integer.parseInt(uid));
        this.id = res.getID();
        //context.complete();
    }

    public CollectionEntityId(Collection collection) throws SQLException {
        this.id = collection.getID();
    }

    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof CollectionEntityId)) {
            return false;
        } else {
            CollectionEntityId castObj = (CollectionEntityId) obj;
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
