package org.dspace.rest.params;

import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

public class StatusParameters {

    private final boolean withdrawn;

    public StatusParameters(final RequestStorage requestStore) {
        final Object storedValue = requestStore.getStoredValue("withdrawn");
        if (storedValue == null) {
            withdrawn = false;
        } else {
            withdrawn = "true".equalsIgnoreCase(storedValue.toString());
        }
    }

    public boolean isWithdrawn() {
        return withdrawn;
    }
}
