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

    CREATE_CONTEXT("Cannot create context."),
    CANNOT_FIND_USER_ENTITIES("Cannot find User Entities."),
    SEARCH("Cannot complete search.");
    
    private final String failureMessage;
    
    private Operation(final String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }
}
