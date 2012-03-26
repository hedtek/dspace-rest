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
import java.util.ArrayList;
import java.util.List;

import org.dspace.core.Context;
import org.dspace.rest.data.DSpace;
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

    /**
     * Handles provider for search accross items
     * @param entityProviderManager
     * @throws java.sql.SQLException
     */
    public SearchProvider(EntityProviderManager entityProviderManager) throws SQLException {
        super(entityProviderManager);
    }

    public String getEntityPrefix() {
        return "search";
    }

    public boolean entityExists(String id) {
        return true;
    }

    public Object getEntity(EntityReference reference) {
        throw new EntityException("Not Acceptable", "The data is not available", 406);
    }

    /**
     * Wiring only. Parameters passed are ignored.
     */
    public List<?> getEntities(EntityReference ref, Search search) {
        return search();
    }

    private List<?> search() {
        final Context context = DSpace.context();

        try {
            
            return buildEntities(context, doQuery(context));
            
        } catch (SQLException cause) {
            throw new SQLFailureEntityException(Operation.SEARCH, cause);
        } catch (IOException cause) {
            throw new IOFailureEntityException(Operation.SEARCH, cause);

        } finally {
            DSpace.complete(context);
        }
    }

    private List<?> buildEntities(final Context context,
            final QueryResults queryResults) throws SQLException {
        final List<Object> entities = buildResults(context, queryResults);
        entities.add(0, buildInfo(queryResults));
        return entities;
    }

    private List<Object> buildResults(final Context context, final QueryResults queryResults)
            throws SQLException {     
        return EntityBuildParameters.build(requestStore).build(context, DetailDepthParameters.build(requestStore).getDepth(), queryResults);
    }

    private SearchResultsInfoEntity buildInfo(final QueryResults queryResults) {
        List<Integer> hh = queryResults.getHitTypes();
        List<Object> hitTypes = new ArrayList<Object>(hh.size());
        for (Integer e : hh) {
            hitTypes.add((Object) e);
        }

        List<String> ii = queryResults.getHitHandles();
        List<Object> hitHandles = new ArrayList<Object>(ii.size());
        for (String e : ii) {
            hitHandles.add((Object) e);
        }

        List<Integer> jj = queryResults.getHitIds();
        List<Object> hitIds = new ArrayList<Object>(jj.size());
        for (Integer e : jj) {
            hitIds.add((Object) e);
        }

        return new SearchResultsInfoEntity(queryResults.getHitCount(), hitTypes, hitHandles, hitIds);
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
}
