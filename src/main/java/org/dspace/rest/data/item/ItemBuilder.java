package org.dspace.rest.data.item;

import java.sql.SQLException;

import org.dspace.content.Item;
import org.dspace.rest.data.base.AbstractBuilder;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.FetchGroup;
import org.dspace.rest.data.collection.Collections;

public class ItemBuilder extends AbstractBuilder {
    
    private final Item item;

    private DetailDepth depth  = DetailDepth.STANDARD;
    private int level = 1;
    
    public ItemBuilder(Item item) {
        super();
        this.item = item;
    }
    
    public ItemBuilder till(final DetailDepth depth) {
        this.depth = depth;
        return this;
    }
    
    public ItemBuilder on(final int level) {
        this.level = level;
        return this;
    }
    
    public ItemEntity buildFull() throws SQLException {
        final int nextLevel = level + 1;
        return new ItemEntity(item, level, depth, Collections.buildOwningCollection(item, nextLevel, depth));
    }

    public ItemBuilder with(FetchGroup fetchGroup) {
        setFetch(fetchGroup);
        return this;
    }
    
    public Entity build() throws SQLException {
        switch (getFetch()) {
        case MINIMAL:
            return new ItemEntityId(item);
        case LIGHT:
            return new ItemWithMetadataEntity(item);
        default:
            return buildFull();
        }
    }

    public ItemBuilder withFull(boolean includeFullDetails) {
        setFull(includeFullDetails);
        return this;
    }

    public ItemBuilder withIdOnly(boolean idOnly) {
        setIdOnly(idOnly);
        return this;
    }
}
