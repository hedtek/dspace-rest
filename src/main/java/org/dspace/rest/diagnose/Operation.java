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
    GET_USER_ENTITIES("Cannot get user data.", "finding users"),
    GET_GROUP_ENTITIES("Cannot get group data.", "finding groups"),
    SEARCH("Cannot complete search.", "searching"),
    GET_ITEMS("Cannot get item data.", "getting items"),
    GET_COMMUNITIES("Cannot get community data", "getting communities"),
    GET_COLLECTIONS("Cannot get collection data", "getting collections"), 
    GET_HARVEST("Cannot get harvesting data", "getting harvest"), 
    GET_BITSTREAM("Cannot get bitstream data", "getting bitstream");
    
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
