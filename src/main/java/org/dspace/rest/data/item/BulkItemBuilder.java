package org.dspace.rest.data.item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;

public class BulkItemBuilder {
    private final static Logger log = Logger.getLogger(BulkItemBuilder.class);

    /**
     * Returning a large number of items causes memory problems for the 
     * current implementation. 
     */
    private static final int HARD_LIMIT = 10000;
    
    public static BulkItemBuilder builder(boolean idOnly, DetailDepth depth) {
        return new BulkItemBuilder(idOnly, depth);
    }
    
    private final boolean idOnly; 
    private final DetailDepth depth;
    
    private BulkItemBuilder(boolean idOnly, DetailDepth depth) {
        super();
        this.idOnly = idOnly;
        this.depth = depth;
    }

    public List<Entity> build(final ItemIterator items) throws SQLException {
        return build(items, 1);
    }

    public List<Entity> build(final ItemIterator items, final int level)
            throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>(HARD_LIMIT);
        int limit = HARD_LIMIT;
        while (items.hasNext() && (--limit >= 0)) {
            Item next = items.next();
            entities.add(new ItemBuilder(next).withIdOnly(idOnly).till(depth).on(level).build());
        }
        if(limit<=0) {
            if (log.isInfoEnabled()) {
                log.info("Hard Limit (" + HARD_LIMIT + ") exceeded for item fetch. Returned only " + entities.size());
            }
        }
        return entities;
    }
}
