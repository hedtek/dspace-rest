/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package org.dspace.rest.data.item;

import java.sql.SQLException;

import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.rest.data.base.Entity;

/**
 * Entity describing item, basic version
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class ItemEntityId extends Entity {

    public ItemEntityId(String uid, Context context) throws SQLException {
        this(Item.find(context, Integer.parseInt(uid)));
    }

    public ItemEntityId(Item item) {
        super(item.getID(), Type.ITEM);
    }
}
