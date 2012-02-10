package org.dspace.rest.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;

public class ItemBuilder {
    private final static Logger log = Logger.getLogger(ItemBuilder.class);

    /**
     * Returning a large number of items causes memory problems for the 
     * current implementation. 
     */
    private static final int HARD_LIMIT = 10000;
    
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
        final List<Object> entities = new ArrayList<Object>(HARD_LIMIT);
        int limit = HARD_LIMIT;
        while (items.hasNext() && (--limit >= 0)) {
            entities.add(build(items.next(), level));
        }
        if(limit<=0) {
            if (log.isInfoEnabled()) {
                log.info("Hard Limit (" + HARD_LIMIT + ") exceeded for item fetch. Returned only " + entities.size());
            }
        }
        return entities;
    }

    private ItemEntityId build(final Item next, final int level)
            throws SQLException {
        return idOnly ? new ItemEntityId(next) : new ItemEntity(next, level, depth);
    }
}
