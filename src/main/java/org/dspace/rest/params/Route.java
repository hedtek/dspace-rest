package org.dspace.rest.params;

import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

public class Route {
    private final Object pathInfo;
    public Route(final RequestStorage requestStore) {
        pathInfo = requestStore.getStoredValue("pathInfo");        
    }
    
    public String[] routeSegments() {
        String segments[] = {};
        if (pathInfo != null) {
            segments = pathInfo.toString().split("/", 10);
        }
        if (segments[3].lastIndexOf(".") > 0) {
            segments[3] = segments[3].substring(0, segments[3].lastIndexOf("."));
        }
        return segments;
    }

    public boolean isAttribute() {
        String segments[] = {};
        if (pathInfo != null) {
            segments = pathInfo.toString().split("/", 10);
        }
        // first check if there is sub-field requested
        // if so then invoke appropriate method inside of entity
        return segments.length > 3;
    }

    public String attributeSegment() {
        final String[] segments = routeSegments();
        final String attributeSegment = segments[3];
        return attributeSegment;
    }
}
