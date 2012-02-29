/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.data.bitstream;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.Bitstream;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.bundle.Bundles;

/**
 * Entity describing Bitstreams
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BitstreamEntity extends BitstreamsNoBundles {

    private final List<Object> bundles;

    public BitstreamEntity(String uid, Context context, final DetailDepth depth) throws SQLException {
        this(Bitstream.find(context, Integer.parseInt(uid)), 1, depth);
    }

    public BitstreamEntity(Bitstream bitstream, int level, final DetailDepth depth) throws SQLException {
        super(bitstream);
        // Only include full when above maximum depth
        final boolean includeFullNextLevel = depth.includeFullDetails(++level);        
        this.bundles = Bundles.bundles(level, depth, includeFullNextLevel, bitstream.getBundles());
    }


    public List<?> getBundles() {
        return this.bundles;
    }
}
