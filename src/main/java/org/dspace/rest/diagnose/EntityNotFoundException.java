package org.dspace.rest.diagnose;

public class EntityNotFoundException extends DSpaceEntityException {

    private static final long serialVersionUID = -9206281743124364668L;

    public EntityNotFoundException(Operation failedOperation) {
        super(null, ErrorCategory.NOT_FOUND, ErrorDetail.ENTITY_NOT_FOUND, HTTPStatusCode.NOT_FOUND, failedOperation);
    }
}
