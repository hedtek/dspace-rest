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
import org.dspace.rest.data.base.Entity;

/**
 * Entity describing bundle, basic version
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BundleEntityId extends Entity {

    public BundleEntityId(Bundle bundle) throws SQLException {
        super(bundle.getID(), Type.BUNDLE);
    }
}
