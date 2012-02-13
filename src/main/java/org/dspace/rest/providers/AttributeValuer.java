package org.dspace.rest.providers;

import org.dspace.core.Context;
import org.dspace.rest.params.Parameters;

interface AttributeValuer {

    public abstract Object attributeValueFor(final String id,
            Parameters parameters, final Context context,
            final String attributeAccessorName);

}