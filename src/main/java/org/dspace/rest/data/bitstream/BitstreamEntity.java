/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.data.bitstream;

import java.util.List;

import org.dspace.content.Bitstream;
import org.dspace.rest.data.base.Entity;

/**
 * Entity describing Bitstreams
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BitstreamEntity extends BitstreamNoBundles {

    private final List<Entity> bundles;

    public BitstreamEntity(Bitstream bitstream, final List<Entity> bundles) {
        super(bitstream);
        this.bundles = bundles;
    }


    public List<Entity> getBundles() {
        return this.bundles;
    }
}
