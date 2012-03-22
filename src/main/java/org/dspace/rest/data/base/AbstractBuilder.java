package org.dspace.rest.data.base;


public class AbstractBuilder {

    private FetchGroup fetch;

    public AbstractBuilder() {
        super();
    }
    
    public final boolean isIdOnly() {
        return fetch == FetchGroup.MINIMAL;
    }

    public final void setFull(boolean includeFull) {
        setIdOnly(!includeFull);
    }
    
    public final void setIdOnly(boolean idOnly) {
        if (idOnly) {
            fetch = FetchGroup.MINIMAL;
        } else {
            fetch = FetchGroup.FULL;
        }
    }

    public final FetchGroup getFetchGroup() {
        return fetch;
    }

    public final void setFetchGroup(FetchGroup fetch) {
        this.fetch = fetch;
    }
}