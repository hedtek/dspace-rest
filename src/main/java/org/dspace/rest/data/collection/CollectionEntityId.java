/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.data.collection;

import org.dspace.rest.data.base.Entity;

/**
 * Entity describing collection, basic version
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CollectionEntityId extends Entity  {

    public CollectionEntityId(final int id) {
        super(id, Type.COLLECTION);
    }
}
