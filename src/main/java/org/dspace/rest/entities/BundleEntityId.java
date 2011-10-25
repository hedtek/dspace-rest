/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;
import org.dspace.content.Bundle;
import org.dspace.core.Context;
import java.sql.SQLException;

/**
 * Entity describing bundle, basic version
 * @see BundleEntityId
 * @see Bundle
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BundleEntityId {

    @EntityId
    private int id;

    protected BundleEntityId() {
    }

    public BundleEntityId(String uid, Context context) throws SQLException {
        Bundle res = Bundle.find(context, Integer.parseInt(uid));
        this.id = res.getID();
        //context.complete();
    }

    public BundleEntityId(Bundle bundle) throws SQLException {
        this.id = bundle.getID();
    }

    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof BundleEntityId)) {
            return false;
        } else {
            BundleEntityId castObj = (BundleEntityId) obj;
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
