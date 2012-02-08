/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.providers;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.dspace.rest.entities.HarvestResultsInfoEntity;
import org.dspace.rest.entities.ItemEntity;
import org.dspace.rest.entities.ItemEntityId;
import org.dspace.rest.params.DetailDepthParameters;
import org.dspace.rest.params.EntityBuildParameters;
import org.dspace.rest.params.PaginationParameters;
import org.dspace.rest.params.ScopeParameters;
import org.dspace.search.Harvest;
import org.dspace.search.HarvestedItemInfo;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;
import org.sakaiproject.entitybus.exception.EntityException;

/**
 * Provides interface for access to harvesting
 * Enables users to harvest items according to several queries, including
 * data range of publication, status of publication, containing elements etc
 * @see HarvestResultsInfoEntity
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class HarvestProvider extends AbstractBaseProvider implements CoreEntityProvider {

    private static Logger log = Logger.getLogger(HarvestProvider.class);

    public HarvestProvider(EntityProviderManager entityProviderManager) throws SQLException {
        super(entityProviderManager);
        entityProviderManager.registerEntityProvider(this);
    }

    public String getEntityPrefix() {
        return "harvest";
    }

    public boolean entityExists(String id) {
        return true;
    }

    public Object getEntity(EntityReference reference) {
        log.info(userInfo() + "get_entity:" + reference.getId());
        throw new EntityException("Not Acceptable", "The data is not available", 406);
    }

    public List<?> getEntities(EntityReference ref, Search search) {
        log.info(userInfo() + "get_entities");

        Context context = context();

        try {
            List<Object> entities = new ArrayList<Object>();
            List<HarvestedItemInfo> harvestedItems = new ArrayList<HarvestedItemInfo>();

            /**
             * check requirement for communities and collections, they should be
             * mutually excluded as underlying architecture accepts searching
             * in only one subject (community or collection)
             */
            try {
                harvestedItems = harvest(context);
            } catch (ParseException ex) {
                throw new EntityException("Bad request", "Incompatible date format", 400);
            } catch (SQLException sq) {
                throw new EntityException("Internal Server Error", "SQL Problem", 500);
            }

            // check results and add entities
            try {
                entities.add(new HarvestResultsInfoEntity(harvestedItems.size()));
                for (int x = 0; x < harvestedItems.size(); x++) {
                    entities.add(EntityBuildParameters.build(reqStor).isIdOnly() ? new ItemEntityId(harvestedItems.get(x).item) : new ItemEntity(harvestedItems.get(x).item, 1, DetailDepthParameters.build(reqStor).getDepth()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            // sort entities if the full info are requested and there are sorting fields
            sort(entities);

            // format results accordint to _limit, _perpage etc
            removeTrailing(entities);

            return entities;
        } finally {
            removeConn(context);
        }
    }

    @SuppressWarnings("unchecked")
    private List<HarvestedItemInfo> harvest(Context context)
            throws SQLException, ParseException {
        List<HarvestedItemInfo> harvestedItems;
        final PaginationParameters paginationParameters = new PaginationParameters(reqStor);
        harvestedItems = Harvest.harvest(context, ScopeParameters.build(reqStor, context).scope(), _sdate, _edate, paginationParameters.getStart(), paginationParameters.getLimit(), true, true, withdrawn, true);
        return harvestedItems;
    }

    /**
     * Returns a Entity object with sample data
     */
    public Object getSampleEntity() {
        return null;
    }
}
