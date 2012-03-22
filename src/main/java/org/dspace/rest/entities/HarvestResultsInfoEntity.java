/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;


/**
 * Entity describing Harvesting results
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class HarvestResultsInfoEntity {

    int resultsCount;

    /**
     * Contains basic informaton on harvesting performed, in this case only
     * results count
     * @param res number of items founded
     */
    public HarvestResultsInfoEntity(int res) {
        this.resultsCount = res;
    }

    public int getResultsCount() {
        return this.resultsCount;
    }


    // these are added for sorting
    public String getName() {
        return "";
    }

    public int getId() {
        return 0;
    }
}
