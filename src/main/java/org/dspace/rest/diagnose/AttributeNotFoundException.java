package org.dspace.rest.diagnose;

public class AttributeNotFoundException extends DSpaceEntityException {

    private static final long serialVersionUID = -9206281743124364668L;

    public AttributeNotFoundException(Operation failedOperation) {
        super(null, ErrorCategory.NOT_FOUND, ErrorDetail.ATTRIBUTE_NOT_FOUND, HTTPStatusCode.NOT_FOUND, failedOperation);
    }
}
