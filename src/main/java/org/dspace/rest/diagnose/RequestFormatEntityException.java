/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.diagnose;

import java.text.ParseException;

/**
 * Provides standard adaption for parse exceptions caught when parsing requests
 * and rethrown to the entity bus framework as EntityException.
 */
public class RequestFormatEntityException extends DSpaceEntityException {

    private static final long serialVersionUID = 1345892305765812L;
    private final ParseException causalParseException;
    
    private RequestFormatEntityException(ParseException cause, ErrorCategory category,
            ErrorDetail details, HTTPStatusCode code, Operation failedOperation) {
        super(cause, category, details, code, failedOperation);
        this.causalParseException = cause;
    }

    /**
     * Convenience constructor builds a 500 Internal Server Error.
     * @param operation describes the operation which failed, for logging, not null
     * @param cause not null
     */
    public RequestFormatEntityException(final Operation operation, final ParseException cause, final ErrorDetail detail) {
        this(cause, ErrorCategory.BAD_REQUEST, detail, HTTPStatusCode.BAD_REQUEST, operation);
    }

    public ParseException getCausalParseException() {
        return causalParseException;
    }   
}
