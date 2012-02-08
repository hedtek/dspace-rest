package org.dspace.rest.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Item;
import org.dspace.content.ItemIterator;

public class ItemBuilder {
    
    public static ItemBuilder builder(boolean idOnly, DetailDepth depth) {
        return new ItemBuilder(idOnly, depth);
    }
    
    private final boolean idOnly; 
    private final DetailDepth depth;
    
    private ItemBuilder(boolean idOnly, DetailDepth depth) {
        super();
        this.idOnly = idOnly;
        this.depth = depth;
    }

    public List<Object> build(final ItemIterator items) throws SQLException {
        return build(items, 1);
    }

    public List<Object> build(final ItemIterator items, final int level)
            throws SQLException {
        final List<Object> entities = new ArrayList<Object>();
        while (items.hasNext()) {
            entities.add(build(items.next(), level));
        }
        return entities;
    }

    private ItemEntityId build(final Item next, final int level)
            throws SQLException {
        return idOnly ? new ItemEntityId(next) : new ItemEntity(next, level, depth);
    }
}
