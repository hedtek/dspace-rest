/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package org.dspace.rest.data.item;

import org.dspace.content.Item;
import org.dspace.rest.data.base.Entity;

/**
 * Entity describing item, basic version
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class ItemEntityId extends Entity {

    public ItemEntityId(Item item) {
        super(item.getID(), Type.ITEM);
    }
}
