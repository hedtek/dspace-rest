/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.data.bundle;

import org.dspace.rest.data.base.Entity;

/**
 * Entity describing bundle, basic version
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BundleEntityId extends Entity {

    BundleEntityId(final int id){
        super(id, Type.BUNDLE);
    }
}
