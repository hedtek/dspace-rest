package org.dspace.rest.data.community;

import org.dspace.rest.data.base.FetchGroup;

class AbstractBuilder {

    private FetchGroup fetch;

    public AbstractBuilder() {
        super();
    }
    
    public final boolean isIdOnly() {
        return fetch == FetchGroup.MINIMAL;
    }

    public final void setIdOnly(boolean idOnly) {
        if (idOnly) {
            fetch = FetchGroup.MINIMAL;
        } else {
            fetch = FetchGroup.DEFAULT;
        }
    }

    public final FetchGroup getFetch() {
        return fetch;
    }

    public final void setFetch(FetchGroup fetch) {
        this.fetch = fetch;
    }
}