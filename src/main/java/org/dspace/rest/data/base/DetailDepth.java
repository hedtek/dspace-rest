package org.dspace.rest.data.base;

public enum DetailDepth {
    
    EXTENDED(3), STANDARD (2), MINIMAL(1), FOR_ALL_INDEX(1);
    
    private final int maxLevelForDetails;

    private DetailDepth(final int maxLevelForDetails) {
        this.maxLevelForDetails = maxLevelForDetails;
    }
    
    public boolean includeFullDetails(final int forLevel) {
        return forLevel <= maxLevelForDetails;
    }
}
