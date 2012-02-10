/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.providers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Bitstream;
import org.dspace.core.Context;
import org.dspace.rest.entities.BitstreamEntity;
import org.dspace.rest.entities.BitstreamEntityId;
import org.dspace.rest.entities.CommunityEntity;
import org.dspace.rest.params.DetailDepthParameters;
import org.dspace.rest.params.EntityBuildParameters;
import org.dspace.rest.params.Parameters;
import org.dspace.rest.params.Route;
import org.dspace.rest.util.RecentSubmissionsException;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.EntityView;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityCustomAction;
import org.sakaiproject.entitybus.entityprovider.search.Search;
import org.sakaiproject.entitybus.exception.EntityException;

/**
 * Provides access to bitstream entities
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BitstreamProvider extends AbstractBaseProvider  implements CoreEntityProvider {

    private static Logger log = Logger.getLogger(BitstreamProvider.class);
    private final Binder binder;

    public BitstreamProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        this.binder = Binder.forBitstream();
    }

    public String getEntityPrefix() {
        return "bitstream";
    }

    /**
     * This part sends binary object to user, usually document file
     * @param reference
     * @param view
     * @param params
     * @return
     * @throws java.sql.SQLException
     * @throws org.dspace.rest.util.RecentSubmissionsException
     */
    @EntityCustomAction(action = "receive", viewKey = EntityView.VIEW_SHOW)
    public Object receive(EntityReference reference, EntityView view, Map<String, Object> params) throws SQLException, RecentSubmissionsException {
        Context context = context();

        try{
            // refresh query parameters and transfer to local variables
            Bitstream bst = Bitstream.find(context, Integer.parseInt(reference.getId()));

            /**
             * Define stream, headers, file.. and send
             */
            HttpServletResponse response = this.entityProviderManager.getRequestGetter().getResponse();
            try {
                ServletOutputStream stream = response.getOutputStream();
                response.setContentType(bst.getFormat().getMIMEType());
                response.addHeader("Content-Disposition", "attachment; filename=" + bst.getName());
                response.setContentLength((int) bst.getSize());
                BufferedInputStream buf = new BufferedInputStream(bst.retrieve());

                int readBytes = 0;
                while ((readBytes = buf.read()) != -1) {
                    stream.write(readBytes);
                }

                if (stream != null) {
                    stream.close();
                }
                if (buf != null) {
                    buf.close();
                }
            } catch (IOException ex) {
                throw new EntityException("Internal Server error", "Unable to open file", 500);
            } catch (AuthorizeException ae) {
                throw new EntityException("Forbidden", "The resource is not available for current user", 403);
            }

            throw new IllegalArgumentException("Invalid id:" + reference.getId());
        } finally {
            complete(context);
        }
    }

    /**
     * Standard method for checking if required entity is available
     * @param id
     * @return
     */
    public boolean entityExists(String id) {
        // sample entity
        if (id.equals(":ID:")) {
            return true;
        }

        Context context = context();

        boolean result = false;
        try {
            Bitstream bst = Bitstream.find(context, Integer.parseInt(id));
            if (bst != null) {
                result = true;
            }
        } catch (SQLException ex) {
            result = false;
        }

        complete(context);
        return result;
    }

    /**
     * Provide information on particular bitstream
     * @param reference
     * @return
     */
    public Object getEntity(EntityReference reference) {
        final Route route = new Route(requestStore);
        if (route.isAttribute()) {
            log.debug("Using generic entity binding");
            final Parameters parameters = new Parameters(requestStore);
            
            final Context context1 = context();
            try {
                return binder.resolve(reference.getId(), route, parameters, context1);
            } finally {
                complete(context1);
            }
        }

        Context context = context();

        try {

            // sample entity
            if (reference.getId().equals(":ID:")) {
                return new CommunityEntity();
            }

            if (reference.getId() == null) {
                return new BitstreamEntity();
            }
            if (entityExists(reference.getId())) {
                try {
                    if (EntityBuildParameters.build(requestStore).isIdOnly()) {
                        return new BitstreamEntityId(reference.getId(), context);
                    } else {
                        return new BitstreamEntity(reference.getId(), context,1, DetailDepthParameters.build(requestStore).getDepth());
                    }
                } catch (SQLException ex) {
                    throw new IllegalArgumentException("sql!Invalid id:" + reference.getId());
                }
            }

            throw new IllegalArgumentException("Invalid id:" + reference.getId());
        } finally {
            complete(context);
        }
    }

    /**
     * There are no entities, not supported by unterlying architecture
     * In order to find all bitstreams on the system, search for items
     * and then take bitstream info
     * @param ref
     * @param search
     * @return
     */
    public List<?> getEntities(EntityReference ref, Search search) {
        log.debug("Bitstream getEntities is not supported.");
        return null;
    }

    /**
     * Sample entity
     * @return
     */
    public Object getSampleEntity() {
        return new BitstreamEntity();
    }
}
