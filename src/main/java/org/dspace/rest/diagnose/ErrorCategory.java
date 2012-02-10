/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.diagnose;

/**
 * The broad category of error displayed in the response to the user.
 */
public enum ErrorCategory {

    INTERNAL("Internal server error"),
    BAD_REQUEST("Bad request"), 
    NOT_FOUND("Not Found");
    
    private final String messageForUser;
    
    private ErrorCategory(final String userMessage) {
        this.messageForUser = userMessage;
    }

    public String getUserMessage() {
        return messageForUser;
    }
}
