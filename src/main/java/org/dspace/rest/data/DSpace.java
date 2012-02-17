package org.dspace.rest.data;

import java.sql.SQLException;

import org.dspace.content.Collection;
import org.dspace.core.Context;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.entities.CollectionEntity;
import org.dspace.rest.entities.CollectionEntityId;
import org.dspace.rest.entities.DetailDepth;

public class DSpace {

    /**
     * Complete connection in order to lower load of sql server
     * this way it goes faster and prevents droppings with higher load
     * @param context
     */
    public static final void complete(Context context) {
        // close connection to prevent connection problems
        try {
            context.complete();
        } catch (SQLException ex) {
        }
    }

    public static final Context context() {
        final Context context;
        try {
            context = new Context();
        } catch (SQLException ex) {
            throw new SQLFailureEntityException(Operation.CREATE_CONTEXT, ex);
        }
        return context;
    }

    public static CollectionEntity build(final String uid, final Context context, final DetailDepth depth) throws SQLException {
        return new CollectionEntity(Collection.find(context, Integer.parseInt(uid)), 1, depth);
    }

    public static CollectionEntityId build(String uid, Context context) throws SQLException {
        return DSpace.build(Collection.find(context, Integer.parseInt(uid)));
    }

    public static CollectionEntityId build(final Collection collection) {
        return new CollectionEntityId(collection.getID());
    }

}
