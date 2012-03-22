package org.dspace.rest.data.base;

public class Pagination {
    
    public static class ZeroBasedBuilder {
        private int startAtPosition = 1;
        private int resultsPerPage = 10;
        
        public ZeroBasedBuilder startAt(final int startAtPosition) {
            this.startAtPosition = startAtPosition;
            return this;
        }
        
        public ZeroBasedBuilder perPage(final int resultsPerPage) {
            this.resultsPerPage = resultsPerPage;
            return this;
        }
        
        public Pagination build() {
            return new Pagination(startAtPosition + 1, resultsPerPage);
        }
    }
    
    private final int startAtPosition;
    private final int resultsPerPage;
    
    private Pagination(int startAtPosition, int resultsPerPage) {
        super();
        this.startAtPosition = startAtPosition;
        this.resultsPerPage = resultsPerPage;
    }
    
    public boolean isInPage(final int oneBasedIndex) {
        return oneBasedIndex >= startAtPosition && oneBasedIndex < oneBasedEndPosition();
    }

    private int oneBasedEndPosition() {
        return startAtPosition + resultsPerPage;
    }
}
