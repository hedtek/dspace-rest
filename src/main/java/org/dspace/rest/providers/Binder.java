package org.dspace.rest.providers;

import java.sql.SQLException;

import org.dspace.core.Context;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.params.Parameters;
import org.dspace.rest.params.Route;
import org.sakaiproject.entitybus.exception.EntityException;

public abstract class Binder {
    
    protected Binder() {
        super();
    }

    public Object resolve(final String id, Route route, Parameters parameters,
            final Context context) {
        return valueAttribute(id, parameters, context, route.attributeSegment());
    }

    protected abstract Object value(String id, Parameters parameters,
            Context context, String attributeSegment) throws SQLException;
    
    public Object valueAttribute(String id, Parameters parameters, Context context,
            String attributeSegment) {
                try {
                    final Object value = value(id, parameters, context, attributeSegment);
                    if (value == null) {
                        throw new EntityException("Bad request", "Method not supported " + attributeSegment, 404);
                    }
                    return value;
                } catch (SQLException cause) {
                    throw new SQLFailureEntityException(operation(), cause);
                }
            }

    protected abstract Operation operation();
}
