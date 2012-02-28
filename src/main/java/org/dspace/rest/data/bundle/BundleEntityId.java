/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.data.bundle;

import java.sql.SQLException;

import org.dspace.content.Bundle;
import org.dspace.core.Context;

/**
 * Entity describing bundle, basic version
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BundleEntityId {

    private final int id;

    public BundleEntityId(String uid, Context context) throws SQLException {
        this(Bundle.find(context, Integer.parseInt(uid)));
    }

    public BundleEntityId(Bundle bundle) throws SQLException {
        this.id = bundle.getID();
    }

    public final int getId() {
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
