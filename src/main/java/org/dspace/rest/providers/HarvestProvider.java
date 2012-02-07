/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.providers;

import java.util.ArrayList;
import java.util.List;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;
import org.dspace.core.Context;
import java.sql.SQLException;
import org.sakaiproject.entitybus.exception.EntityException;
import org.dspace.rest.entities.*;
import org.dspace.search.*;
import org.apache.log4j.Logger;
import java.text.ParseException;
import java.util.Collections;
import org.dspace.rest.util.RequestParameters;
import org.dspace.rest.util.GenComparator;

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
            RequestParameters uparams;
            uparams = refreshParams(context);
            List<Object> entities = new ArrayList<Object>();
            List<HarvestedItemInfo> res = new ArrayList<HarvestedItemInfo>();

            /**
             * check requirement for communities and collections, they should be
             * mutually excluded as underlying architecture accepts searching
             * in only one subject (community or collection)
             */
            try {
                if (_community != null) {
                    res = Harvest.harvest(context, _community, _sdate, _edate, _start, _limit, true, true, withdrawn, true);
                } else if (_collection != null) {
                    res = Harvest.harvest(context, _collection, _sdate, _edate, _start, _limit, true, true, withdrawn, true);
                } else {
                    res = Harvest.harvest(context, null, _sdate, _edate, _start, _limit, true, true, withdrawn, true);
                }
            } catch (ParseException ex) {
                throw new EntityException("Bad request", "Incompatible date format", 400);
            } catch (SQLException sq) {
                throw new EntityException("Internal Server Error", "SQL Problem", 500);
            }

            // check results and add entities
            try {
                entities.add(new HarvestResultsInfoEntity(res.size()));
                for (int x = 0; x < res.size(); x++) {
                    entities.add(idOnly ? new ItemEntityId(res.get(x).item) : new ItemEntity(res.get(x).item, 1, uparams));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            // sort entities if the full info are requested and there are sorting fields
            if (!idOnly && sortOptions.size() > 0)
                Collections.sort(entities, new GenComparator(sortOptions));

            // format results accordint to _limit, _perpage etc
            removeTrailing(entities);

            return entities;
        } finally {
            removeConn(context);
        }
    }

    /**
     * Returns a Entity object with sample data
     */
    public Object getSampleEntity() {
        return null;
    }
}
