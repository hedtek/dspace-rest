package org.dspace.rest.params;

import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

public class Routes {
    private final Object pathInfo;
    public Routes(final RequestStorage requestStore) {
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
}
