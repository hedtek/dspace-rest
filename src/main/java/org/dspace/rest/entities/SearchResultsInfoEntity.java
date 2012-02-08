/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import java.util.ArrayList;
import java.util.List;

import org.dspace.rest.providers.SearchProvider;

/**
 * Entity decribing search results
 * @see SearchProvider
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class SearchResultsInfoEntity {

    int resultsCount;
    List<Object> resultTypes = new ArrayList<Object>();
    List<Object> resultHandles = new ArrayList<Object>();
    List<Object> resultsIDs = new ArrayList<Object>();

    /**
     * Constructs SearchResultsInfoEntity, which should contain basic info
     * on results of search performed
     * 
     * @param res number of results
     * @param types list including types (DAO entity type) of results
     * @param handles list including handles (DAO handle) of results
     * @param ids list including ids of results
     */
    public SearchResultsInfoEntity(int res, List types, List handles, List ids) {
        this.resultsCount = res;
        this.resultTypes = types;
        this.resultHandles = handles;
        this.resultsIDs = ids;
    }

    public int getResultsCount() {
        return this.resultsCount;
    }

    public List<?> getResultTypes() {
        return this.resultTypes;
    }

    public List<?> getResultHandles() {
        return this.resultHandles;
    }

    public List<?> getResultIDs() {
        return this.resultsIDs;
    }

    // these are added for sorting management
    public String getName() {
        return "";
    }

    public int getId() {
        return 0;
    }
}
