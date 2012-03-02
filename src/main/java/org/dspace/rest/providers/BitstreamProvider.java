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
import org.dspace.rest.data.DSpace;
import org.dspace.rest.data.bitstream.BitstreamEntity;
import org.dspace.rest.data.bitstream.Bitstreams;
import org.dspace.rest.diagnose.EntityNotFoundException;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.params.Parameters;
import org.dspace.rest.params.Route;
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

    public static class BitstreamBinder extends Binder{
    
        @Override
        protected Object value(String id, Parameters parameters,
                Context context, String attributeSegment) throws SQLException {
            if("mimeType".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getMimeType();
            } else if("bundles".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getBundles();
            } else if("checkSum".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getCheckSum();
            } else if("checkSumAlgorithm".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getCheckSumAlgorithm();
            } else if("description".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getDescription();
            } else if("formatDescription".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getFormatDescription();
            } else if("sequenceId".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getSequenceId();
            } else if("size".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getSize();
            } else if("source".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getSource();
            } else if("storeNumber".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getStoreNumber();
            } else if("userFormatDescription".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getUserFormatDescription();
            } else if("name".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getName();
            } else if("handle".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getHandle();
            } else if("id".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getId();
            } else if("type".equals(attributeSegment)) {
                return bitstream(id, parameters, context).getType();
            } else {
                return null;
            }
        }

        private static BitstreamEntity bitstream(String uid, Parameters parameters,
                Context context) throws SQLException {
            return new Bitstreams(context).till(parameters.getDetailDepth().getDepth()).full(uid);
        }
    
        @Override
        protected Operation operation() {
            return Operation.GET_BITSTREAM;
        }
        
    }

    private static Logger log = Logger.getLogger(BitstreamProvider.class);
    private final Binder binder;

    public BitstreamProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        this.binder = new BitstreamProvider.BitstreamBinder();
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
     * @throws org.dspace.rest.entities.RecentSubmissionsException
     */
    @EntityCustomAction(action = "receive", viewKey = EntityView.VIEW_SHOW)
    public Object receive(EntityReference reference, EntityView view, Map<String, Object> params) throws SQLException {
        Context context = DSpace.context();

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
            DSpace.complete(context);
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

        
        final Context context = DSpace.context();
        try {
            return Bitstream.find(context, Integer.parseInt(id)) != null;
        } catch (SQLException ex) {
            log.debug("Failed to find community. Assuming that this means it doesn't exist.", ex);
            return false;
        } finally {
            DSpace.complete(context);
        }
    }

    /**
     * Provide information on particular bitstream
     * @param reference
     * @return
     */
    public Object getEntity(EntityReference reference) {
        final String id = reference.getId();
        if (":ID:".equals(id) || id == null) {
            return getSampleEntity();
        }

        return entity(id);
    }

    private Object entity(final String id) {
        final Context context = DSpace.context();
        final Operation operation = Operation.GET_BITSTREAM;
        try {

            final Parameters parameters = new Parameters(requestStore);
            final Route route = new Route(requestStore);

            if (route.isAttribute()) {
                log.debug("Using generic entity binding");
                return binder.resolve(id, route, parameters, context);
            }

            if (entityExists(id)) {
                return parameters.bitstream(id, context);

            } else {
                throw new EntityNotFoundException(operation);
            }
        } catch (SQLException cause) {
            throw new SQLFailureEntityException(operation, cause);

        } finally {
            DSpace.complete(context);
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
}
