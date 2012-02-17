/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import java.util.List;

/**
 * Entity decribing search results
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class SearchResultsInfoEntity {

    private final int resultsCount;
    private final List<Object> resultTypes;
    private final List<Object> resultHandles;
    private final List<Object> resultsIDs;

    /**
     * Constructs SearchResultsInfoEntity, which should contain basic info
     * on results of search performed
     * 
     * @param res number of results
     * @param types list including types (DAO entity type) of results
     * @param handles list including handles (DAO handle) of results
     * @param ids list including ids of results
     */
    public SearchResultsInfoEntity(int res, List<Object> types, List<Object> handles, List<Object> ids) {
        this.resultsCount = res;
        this.resultTypes = types;
        this.resultHandles = handles;
        this.resultsIDs = ids;
    }

    public int getResultsCount() {
        return this.resultsCount;
    }

    public List<Object> getResultTypes() {
        return this.resultTypes;
    }

    public List<Object> getResultHandles() {
        return this.resultHandles;
    }

    public List<Object> getResultIDs() {
        return this.resultsIDs;
    }
}
