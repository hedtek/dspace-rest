/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.diagnose;

/**
 * Operations which may fail in a fatal fashion.
 */
public enum Operation {

    CREATE_CONTEXT("Cannot create context.", "creating context"),
    CANNOT_FIND_USER_ENTITIES("Cannot find User Entities.", "finding users"),
    SEARCH("Cannot complete search.", "searching"),
    GET_ITEMS("Cannot get item data.", "getting items"),
    GET_COMMUNITIES("Cannot get community data", "getting communities");
    
    private final String failureMessage;
    private final String description;
    
    private Operation(final String failureMessage, final String description) {
        this.failureMessage = failureMessage;
        this.description = description;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public String getDescription() {
        return description;
    }
}
