package org.dspace.rest.data.base;

public class Pagination {
    
    public static class Builder {
        private int startAtPosition = 1;
        private int resultsPerPage = 10;
        
        public Builder startAt(final int startAtPosition) {
            this.startAtPosition = startAtPosition;
            return this;
        }
        
        public Builder perPage(final int resultsPerPage) {
            this.resultsPerPage = resultsPerPage;
            return this;
        }
        
        public Pagination build() {
            return new Pagination(startAtPosition, resultsPerPage);
        }
    }
    
    private final int startAtPosition;
    private final int resultsPerPage;
    
    private Pagination(int startAtPosition, int resultsPerPage) {
        super();
        this.startAtPosition = startAtPosition;
        this.resultsPerPage = resultsPerPage;
    }
    
    public boolean isInPage(final int zeroBasedIndex) {
        return zeroBasedIndex >= zeroBasedStartPosition() && zeroBasedIndex <= zeroBasedEndPosition();
    }

    private int zeroBasedEndPosition() {
        return zeroBasedStartPosition() + resultsPerPage;
    }

    private int zeroBasedStartPosition() {
        return startAtPosition - 1;
    }
}
