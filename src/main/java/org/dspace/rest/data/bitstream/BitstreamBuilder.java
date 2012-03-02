package org.dspace.rest.data.bitstream;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.Bitstream;
import org.dspace.rest.data.base.AbstractBuilder;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.FetchGroup;
import org.dspace.rest.data.bundle.BulkBundleBuilder;

public class BitstreamBuilder extends AbstractBuilder {

    private final Bitstream bitstream;
    private DetailDepth depth = DetailDepth.STANDARD;
    private int level = 1;

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

    public BitstreamBuilder on(int level) {
        this.level = level;
        return this;
    }

    public BitstreamEntityId build() throws SQLException {
        switch (getFetchGroup()) {
        case MINIMAL:
            return idOnly();
        default:
            return full();
        }
    }

    public BitstreamEntity full() throws SQLException {
        final List<Entity> bundles = new BulkBundleBuilder(bitstream.getBundles()).on(level).till(depth)
                .withFull(depth.includeFullDetails(++level)).build();
        return new BitstreamEntity(bitstream, bundles);
    }

    public BitstreamBuilder full(boolean includeFullDetails) {
        setFull(includeFullDetails);
        return this;
    }

    public BitstreamEntityId idOnly() {
        return new BitstreamEntityId(bitstream);
    }
}
