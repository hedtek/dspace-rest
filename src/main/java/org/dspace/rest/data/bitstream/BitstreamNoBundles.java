package org.dspace.rest.data.bitstream;

import org.dspace.content.Bitstream;

public class BitstreamNoBundles extends BitstreamEntityId {

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

    public BitstreamNoBundles(Bitstream bitstream) {
        super(bitstream);
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
        this.mimeType = bitstream.getFormat().getMIMEType();
    }

    public final String getMimeType() {
        return this.mimeType;
    }

    public final String getCheckSum() {
        return this.checkSum;
    }

    public final String getCheckSumAlgorithm() {
        return this.checkSumAlgorithm;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getFormatDescription() {
        return this.formatDescription;
    }

    public final long getSequenceId() {
        return this.sequenceId;
    }

    public final long getSize() {
        return this.size;
    }

    public final String getSource() {
        return this.source;
    }

    public final int getStoreNumber() {
        return this.storeNumber;
    }

    public final String getUserFormatDescription() {
        return this.userFormatDescription;
    }

    public final String getName() {
        return this.name;
    }

    public final String getHandle() {
        return this.handle;
    }

    public final int getType() {
        return this.type;
    }

}