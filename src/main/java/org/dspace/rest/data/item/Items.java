package org.dspace.rest.data.item;

import java.sql.SQLException;

import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.FetchGroup;

public class Items {

    private final Context context;

    public Items(final Context context) {
        super();
        this.context = context;
    }

    public Entity build(final DetailDepth depth, final String uid, final FetchGroup fetchGroup)
            throws SQLException {
        return fetchGroup == FetchGroup.MINIMAL ? new ItemEntityId(fetch(uid)) : new ItemEntity(uid, context, depth);
    }
    
    private Item fetch(final String uid) throws NumberFormatException, SQLException {
        return Item.find(context, Integer.parseInt(uid));
    }
}
