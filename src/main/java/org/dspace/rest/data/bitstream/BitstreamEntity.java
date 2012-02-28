/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.data.bitstream;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.Bitstream;
import org.dspace.content.Bundle;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.bundle.BundleEntity;
import org.dspace.rest.data.bundle.BundleEntityId;

/**
 * Entity describing Bitstreams
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BitstreamEntity extends BitstreamEntityId {

    private static List<Object> bundles(int level, final DetailDepth depth,
            final boolean includeFullNextLevel, final Bundle[] bnd)
            throws SQLException {
        final List<Object> bundles = new ArrayList<Object>();
        for (Bundle b : bnd) {
            bundles.add(includeFullNextLevel ? new BundleEntity(b, level, depth) : new BundleEntityId(b));
        }
        return bundles;
    }

    
    private final String name;
    private final String handle;
    private final int type;
    private final int storeNumber;
    private final long sequenceId;
    private final long size;
    private final String checkSumAlgorithm;
    private final String description;
    private final String checkSum;
    private final String formatDescription;
    private final String source;
    private final String userFormatDescription;
    private final String mimeType;
    private final List<Object> bundles;

    public BitstreamEntity(String uid, Context context, final DetailDepth depth) throws SQLException {
        this(Bitstream.find(context, Integer.parseInt(uid)), 1, depth);
    }

    public BitstreamEntity(Bitstream bitstream, int level, final DetailDepth depth) throws SQLException {
        super(bitstream);
        // Only include full when above maximum depth
        final boolean includeFullNextLevel = depth.includeFullDetails(++level);
        
        this.handle = bitstream.getHandle();
        this.name = bitstream.getName();
        this.type = bitstream.getType();
        this.checkSum = bitstream.getChecksum();
        this.checkSumAlgorithm = bitstream.getChecksumAlgorithm();
        this.description = bitstream.getDescription();
        this.formatDescription = bitstream.getFormatDescription();
        this.sequenceId = bitstream.getSequenceID();
        this.size = bitstream.getSize();
        this.source = bitstream.getSource();
        this.storeNumber = bitstream.getStoreNumber();
        this.userFormatDescription = bitstream.getUserFormatDescription();
        this.bundles = bundles(level, depth, includeFullNextLevel, bitstream.getBundles());
        this.mimeType = bitstream.getFormat().getMIMEType();
    }


    public String getMimeType() {
        return this.mimeType;
    }

    public List<?> getBundles() {
        return this.bundles;
    }

    public String getCheckSum() {
        return this.checkSum;
    }

    public String getCheckSumAlgorithm() {
        return this.checkSumAlgorithm;
    }

    public String getDescription() {
        return this.description;
    }

    public String getFormatDescription() {
        return this.formatDescription;
    }

    public long getSequenceId() {
        return this.sequenceId;
    }

    public long getSize() {
        return this.size;
    }

    public String getSource() {
        return this.source;
    }

    public int getStoreNumber() {
        return this.storeNumber;
    }

    public String getUserFormatDescription() {
        return this.userFormatDescription;
    }

    public String getName() {
        return this.name;
    }

    public String getHandle() {
        return this.handle;
    }

    public int getType() {
        return this.type;
    }
}
