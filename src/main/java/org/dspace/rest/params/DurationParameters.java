package org.dspace.rest.params;

import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

public class DurationParameters {
    
    private static String valueInStore(final RequestStorage requestStore, final String key) {
        final String value;
        final Object storedValue = requestStore.getStoredValue(key);
        if (storedValue == null) {
            value = null;
        } else { 
            value = storedValue.toString();
        }
        return value;
    }
    
    private final String start_date;
    private final String end_date;
    
    public DurationParameters(final RequestStorage requestStore) {
        start_date = valueInStore(requestStore, "startdate");
        end_date = valueInStore(requestStore, "enddate");
    }

    public String getStart() {
        return start_date;
    }

    public String getEnd() {
        return end_date;
    }
}
