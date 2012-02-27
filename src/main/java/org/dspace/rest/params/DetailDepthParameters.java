package org.dspace.rest.params;

import org.dspace.rest.data.base.DetailDepth;
import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

public class DetailDepthParameters {
    
    private final static DetailDepth DEFAULT_DEPTH = DetailDepth.STANDARD;
    
    public static DetailDepthParameters build(RequestStorage requestStorage) {
        final DetailDepth depth;
        final Object storedValue = requestStorage.getStoredValue("detail");
        if (storedValue == null) {
            depth = DEFAULT_DEPTH;
        } else {
            String detail = storedValue.toString();
            if (detail.equals("minimum")) {
                depth = DetailDepth.MINIMAL;
            } else if (detail.equals("standard")) {
                depth = DetailDepth.STANDARD;
            } else if (detail.equals("extended")) {
                depth = DetailDepth.EXTENDED;
            } else {
                depth = DEFAULT_DEPTH;
            }
        }
        return new DetailDepthParameters(depth);
    }
    
    private final DetailDepth depth;
    private DetailDepthParameters(DetailDepth depth) {
        super();
        this.depth = depth;
    }

    public DetailDepth getDepth() {
        return depth;
    }
}
