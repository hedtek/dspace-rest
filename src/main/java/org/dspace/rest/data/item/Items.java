package org.dspace.rest.data.item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Entity fetch(final DetailDepth depth, final String uid, final FetchGroup fetchGroup)
            throws SQLException {
        return new ItemBuilder(fetch(uid)).till(depth).with(fetchGroup).build();
    }

    private Item fetch(final String uid) throws NumberFormatException, SQLException {
        return Item.find(context, Integer.parseInt(uid));
    }

    public ItemEntity fetch(DetailDepth depth, String uid) throws NumberFormatException, SQLException {
        return new ItemBuilder(fetch(uid)).till(depth).buildFull();
    }

    public static List<Object> build(int level, final DetailDepth depth, final Item[] dspaceItems) throws SQLException {
        final List<Object> items = new ArrayList<Object>();
        final boolean includeFullNextLevel = depth.includeFullDetails(level);
        for (Item item : dspaceItems) {
            items.add(new ItemBuilder(item).till(depth).withFull(includeFullNextLevel).on(level).build());
        }
        return items;
    }
}
