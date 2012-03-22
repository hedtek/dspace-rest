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
            entities.add(builderFor(bitstream).full(depth.includeFullDetails(level)).build());
        }
        return entities;
    }

    private BitstreamBuilder builderFor(Bitstream bitstream) {
        return new BitstreamBuilder(bitstream).till(depth).on(level);
    }


    public List<Entity> idOnly() throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        for (Bitstream bitstream : bitstreams) {
            entities.add(builderFor(bitstream).idOnly());
        }
        return entities;
    }

    public List<Entity> full() throws SQLException {
        final List<Entity> entities = new ArrayList<Entity>();
        for (Bitstream bitstream : bitstreams) {
            entities.add(builderFor(bitstream).full());
        }
        return entities;
    }

    public List<Entity> noBundles() {
        final List<Entity> entities = new ArrayList<Entity>();
        for (Bitstream bitstream : bitstreams) {
            entities.add(new BitstreamNoBundles(bitstream));
        }
        return entities;    
    }
}
