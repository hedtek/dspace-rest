package org.dspace.rest.data.bitstream;

import java.sql.SQLException;

import org.dspace.content.Bitstream;
import org.dspace.rest.data.base.AbstractBuilder;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.FetchGroup;

public class BitstreamBuilder extends AbstractBuilder {

    private final Bitstream bitstream;
    private DetailDepth depth = DetailDepth.STANDARD;

    public BitstreamBuilder(Bitstream bitstream) {
        super();
        this.bitstream = bitstream;
    }

    public BitstreamBuilder till(DetailDepth depth) {
        this.depth = depth;
        return this;
    }

    public BitstreamBuilder with(FetchGroup fetchGroup) {
        setFetchGroup(fetchGroup);
        return this;
    }

    public BitstreamEntityId build() throws SQLException {
        switch (getFetchGroup()) {
        case MINIMAL:
            return new BitstreamEntityId(bitstream);
        default:
            return new BitstreamEntity(bitstream, 1, depth);
        }
    }
}
