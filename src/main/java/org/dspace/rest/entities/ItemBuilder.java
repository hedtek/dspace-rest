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
        final List<Object> entities = new ArrayList<Object>();
        while (items.hasNext()) {
            final Item next = items.next();
            entities.add(build(next));
        }
        return entities;
    }

    private ItemEntityId build(final Item next) throws SQLException {
        return idOnly ? new ItemEntityId(next) : new ItemEntity(next, 1, depth);
    }
}
