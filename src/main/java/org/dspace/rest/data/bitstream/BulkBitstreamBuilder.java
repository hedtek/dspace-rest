package org.dspace.rest.data.bitstream;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Bitstream;
import org.dspace.rest.data.base.AbstractBuilder;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;

public class BulkBitstreamBuilder extends AbstractBuilder {

    private final Bitstream[] bitstreams;
    private int level = 1;
    private DetailDepth depth = DetailDepth.STANDARD;

    public BulkBitstreamBuilder(Bitstream[] bitstreams) {
        super();
        this.bitstreams = bitstreams;
    }
    
    public BulkBitstreamBuilder till(final DetailDepth depth) {
        this.depth = depth;
        return this;
    }
    
    public BulkBitstreamBuilder on(final int level) {
        this.level = level;
        return this;
    }
    
    public BulkBitstreamBuilder withFull(boolean includeFullNextLevel) {
        setFull(includeFullNextLevel);
        return this;
    }

    public List<Entity> build() throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        for (Bitstream bitstream : bitstreams) {
            entities.add(depth.includeFullDetails(level) ? 
                    new BitstreamEntity(bitstream, level, depth) : new BitstreamEntityId(bitstream));
        }
        return entities;
    }
    
}
