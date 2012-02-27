package org.dspace.rest.data.community;

import org.dspace.rest.data.base.Fetch;

class AbstractBuilder {

    private Fetch fetch;

    public AbstractBuilder() {
        super();
    }
    
    public final boolean isIdOnly() {
        return fetch == Fetch.MINIMAL;
    }

    public final void setIdOnly(boolean idOnly) {
        if (idOnly) {
            fetch = Fetch.MINIMAL;
        } else {
            fetch = Fetch.DEFAULT;
        }
    }

    public final Fetch getFetch() {
        return fetch;
    }

    public final void setFetch(Fetch fetch) {
        this.fetch = fetch;
    }

}