/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.diagnose;

import org.apache.log4j.Logger;
import org.sakaiproject.entitybus.exception.EntityException;

/**
 * Framework for improved exception handling and reporting for entity exceptions
 * passed to the entity bus. This provides logging services and retains
 * exception information.
 */
public abstract class DSpaceEntityException extends EntityException {

    private static final long serialVersionUID = 18934751234755L;

    protected static Logger LOGGER = Logger.getLogger(DSpaceEntityException.class);

    private final ErrorCategory category;
    private final ErrorDetail details;
    
    protected DSpaceEntityException(final Throwable cause, final ErrorCategory category, final ErrorDetail details,
            final HTTPStatusCode code, final Operation failedOperation) {
        super(category.getUserMessage(), details.getDetailsMessage(), code.getCode());
        LOGGER.error(failedOperation.getFailureMessage() + "[" + cause.getMessage() + "]");
        if (cause != null) {
            LOGGER.debug(failedOperation.getFailureMessage() + "[" + category.getUserMessage() + "]", cause);
        }
        initCause(cause);
        this.details = details;
        this.category = category;
    }

    public ErrorCategory getCategory() {
        return category;
    }

    public ErrorDetail getDetails() {
        return details;
    }
}
