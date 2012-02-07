/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.providers;

import java.util.List;
import java.util.Map;

import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.EntityView;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityCustomAction;
import org.dspace.content.Bitstream;
import org.dspace.core.Context;
import org.apache.log4j.Logger;
import java.sql.SQLException;
import org.dspace.rest.entities.*;
import org.dspace.rest.params.RequestParameters;
import org.dspace.rest.util.RecentSubmissionsException;
import javax.servlet.http.HttpServletResponse;
import org.dspace.authorize.AuthorizeException;
import javax.servlet.ServletOutputStream;
import java.io.BufferedInputStream;
import org.sakaiproject.entitybus.exception.EntityException;
import java.io.IOException;

/**
 * Provides access to bitstream entities
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class BitstreamProvider extends AbstractBaseProvider implements CoreEntityProvider {

    EntityProviderManager locEPM;
    private static Logger log = Logger.getLogger(BitstreamProvider.class);

    public BitstreamProvider(EntityProviderManager entityProviderManager) throws SQLException, NoSuchMethodException {
        super(entityProviderManager);
        entityProviderManager.registerEntityProvider(this);
        locEPM = entityProviderManager;
        processedEntity = BitstreamEntity.class;
        func2actionMapGET.put("getMimeType", "mimeType");
        func2actionMapGET.put("getBundles", "bundles");
        func2actionMapGET.put("getCheckSum", "checkSum");
        func2actionMapGET.put("getCheckSumAlgorithm", "checkSumAlgorithm");
        func2actionMapGET.put("getDescription", "description");
        func2actionMapGET.put("getFormatDescription", "formatDescription");
        func2actionMapGET.put("getSequenceId", "sequenceId");
        func2actionMapGET.put("getSize", "size");
        func2actionMapGET.put("getSource", "source");
        func2actionMapGET.put("getStoreNumber", "storeNumber");
        func2actionMapGET.put("getUserFormatDescription", "userFormatDescription");
        func2actionMapGET.put("getName", "name");
        func2actionMapGET.put("getHandle", "handle");
        func2actionMapGET.put("getId", "id");
        func2actionMapGET.put("getType", "type");
        entityConstructor = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, RequestParameters.class});
        initMappings(processedEntity);

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
            log.info(userInfo() + "receive_action:" + reference.getId());
            Context context = context();

            try{
                // refresh query parameters and transfer to local variables
                RequestParameters uparam;
                uparam = refreshParams(context);

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
                removeConn(context);
            }
        }

    /**
     * Standard method for checking if required entity is available
     * @param id
     * @return
     */
    public boolean entityExists(String id) {
        log.info(userInfo() + "entity_exists:" + id);

        // sample entity
        if (id.equals(":ID:")) {
            return true;
        }

        Context context = context();

        RequestParameters uparams;
        uparams = refreshParams(context);

        boolean result = false;
        try {
            Bitstream bst = Bitstream.find(context, Integer.parseInt(id));
            if (bst != null) {
                result = true;
            }
        } catch (SQLException ex) {
            result = false;
        }

        removeConn(context);
        return result;
    }

    /**
     * Provide information on particular bitstream
     * @param reference
     * @return
     */
    public Object getEntity(EntityReference reference) {
        String segments[] = {};

        if (reqStor.getStoredValue("pathInfo") != null) {
            segments = reqStor.getStoredValue("pathInfo").toString().split("/", 10);
        }

        // first check if there is sub-field requested
        // if so then invoke appropriate method inside of entity
        if (segments.length > 3) {
            return super.getEntity(reference);
        }

        Context context = context();

        try {
            RequestParameters uparams;
            uparams = refreshParams(context);
            log.info(userInfo() + "get_entity:" + reference.getId());

            // sample entity
            if (reference.getId().equals(":ID:")) {
                return new CommunityEntity();
            }

            if (reference.getId() == null) {
                return new BitstreamEntity();
            }
            if (entityExists(reference.getId())) {
                try {
                    if (idOnly) {
                        return new BitstreamEntityId(reference.getId(), context);
                    } else {
                        return new BitstreamEntity(reference.getId(), context,1, uparams);
                    }
                } catch (SQLException ex) {
                    throw new IllegalArgumentException("sql!Invalid id:" + reference.getId());
                }
            }

            throw new IllegalArgumentException("Invalid id:" + reference.getId());
        } finally {
            removeConn(context);
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
        log.info(userInfo() + "list_entities:");
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
