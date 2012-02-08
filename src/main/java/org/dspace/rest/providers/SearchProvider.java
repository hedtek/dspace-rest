/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.providers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.dspace.rest.diagnose.IOFailureEntityException;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.diagnose.SQLFailureEntityException;
import org.dspace.rest.entities.SearchResultsInfoEntity;
import org.dspace.rest.params.DetailDepthParameters;
import org.dspace.rest.params.EntityBuildParameters;
import org.dspace.rest.params.PaginationParameters;
import org.dspace.rest.params.ScopeParameters;
import org.dspace.rest.params.SortParameters;
import org.dspace.search.QueryArgs;
import org.dspace.search.QueryResults;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;
import org.sakaiproject.entitybus.exception.EntityException;

/**
 * Enables users to search through items according to different criteria
 * @see SearchResultsInfoEntity
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class SearchProvider extends AbstractBaseProvider implements CoreEntityProvider {

    private static Logger log = Logger.getLogger(SearchProvider.class);

    /**
     * Handles provider for search accross items
     * @param entityProviderManager
     * @throws java.sql.SQLException
     */
    public SearchProvider(EntityProviderManager entityProviderManager) throws SQLException {
        super(entityProviderManager);
        entityProviderManager.registerEntityProvider(this);
    }

    public String getEntityPrefix() {
        return "search";
    }

    public boolean entityExists(String id) {
        return true;
    }

    public Object getEntity(EntityReference reference) {
        log.info(userInfo() + "get_entity:" + reference.getId());
        throw new EntityException("Not Acceptable", "The data is not available", 406);
    }

    /**
     * Wiring only. Parameters passed are ignored.
     */
    public List<?> getEntities(EntityReference ref, Search search) {
        return search();
    }

    private List<?> search() {
        log.info(userInfo() + "get_entities");

        final Context context = context();

        try {
            final QueryResults queryResults = doQuery(context);
            final SearchResultsInfoEntity info = buildInfo(queryResults);
            final List<Object> entities = buildResults(context, queryResults);
            entities.add(0, info);
            return entities;
        } catch (SQLException cause) {
            throw new SQLFailureEntityException(Operation.SEARCH, cause);
        } catch (IOException cause) {
            throw new IOFailureEntityException(Operation.SEARCH, cause);

        } finally {
            complete(context);
        }
    }

    private List<Object> buildResults(final Context context, final QueryResults queryResults)
            throws SQLException {     
        final List<Object> entities = EntityBuildParameters.build(requestStore).build(context, DetailDepthParameters.build(requestStore).getDepth(), queryResults);
        sort(entities);

        /**
         * process entities according to _limit, _perpage etc
         */
        removeTrailing(entities);
        return entities;
    }

    private SearchResultsInfoEntity buildInfo(final QueryResults queryResults) {
        return new SearchResultsInfoEntity(queryResults.getHitCount() - 1, queryResults.getHitTypes(), queryResults.getHitHandles(), queryResults.getHitIds());
    }

    private QueryResults doQuery(final Context context) throws IOException {
        return ScopeParameters.build(requestStore, context).doQuery(context, buildQueryArguments());
    }

    private QueryArgs buildQueryArguments() {
        // extract query arguments from the request
        // deprecated - this is now handled at the end of function
        final QueryArgs arg = new QueryArgs();
        new PaginationParameters(requestStore).configure(arg);
        new SortParameters(requestStore).configure(arg);
        return arg;
    }

    /**
     * Returns a Entity object with sample data
     */
    public Object getSampleEntity() {
        return null;
    }
}
