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

/**
 * Entity describing collection, basic version
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CollectionEntityId  {


    public static CollectionEntityId build(String uid, Context context) throws SQLException {
        return new CollectionEntityId(Collection.find(context, Integer.parseInt(uid)));
    }
    
    private final int id;

    public CollectionEntityId(Collection collection) {
        this.id = collection.getID();
    }

    public final int getId() {
        return this.id;
    }

    @Override
    public final boolean equals(Object obj) {
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
    public final int hashCode() {
        return id;
    }

    @Override
    public final String toString() {
        return "id:" + this.id;
    }
}
