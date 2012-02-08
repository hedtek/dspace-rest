package uk.ac.jorum.integration.searching;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class SearchPaginationTest extends RestApiBaseTest {
    private static final String SEARCH_RESULTS_KEY = "search_collection";
    private static final int NUMBER_OF_HEADER_RECORDS = 1;
    private static final int NUMBER_OF_ITEM_RECORDS_IN_SEARCH_DB = 8;

    @BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("searchDatabase");
      startJetty();
    }

    private int countResults(String extraparameters) throws Exception {
        return count(requestJSON(extraparameters), SEARCH_RESULTS_KEY) - NUMBER_OF_HEADER_RECORDS;
    }

    private JSONObject requestJSON(String extraparameters) throws Exception {
        String queryString = "query=search.resourcetype:3" + extraparameters;
        String result = makeRequest("/search", queryString);
        JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
        return resultJSON;
    }

    private int count(JSONObject resultJSON, String key) {
        return ((JSONArray) resultJSON.get(key)).size();
    }

    private void resultsShouldMatchPerPage(int perPage) throws Exception {
        assertEquals("Result count should match per page, which is " + perPage, perPage, countResults(perPageParameter(perPage)));
    }
    
    private String perPageParameter(int perPage) {
        return "&_perpage=" + perPage;
    }
 
    private Set<Integer> pageIds(int pageNumber) throws Exception {
        return resultIds(requestJSON(perPageParameter(5) + pageParameter(pageNumber)));
    }

    private Set<Integer> resultIds(JSONObject json) {
        JSONObject info = info(json);
        JSONArray resultIds = (JSONArray) info.get("resultIDs");
        assertNotNull("Unexpected info " + info, resultIds);
        @SuppressWarnings("unchecked")
        final Set<Integer> results = new HashSet<Integer>(resultIds);
        return results;
    }

    private JSONObject info(JSONObject json) {     
        return (JSONObject)((JSONArray) json.get(SEARCH_RESULTS_KEY)).get(0);
    }

    private String pageParameter(int pageNumber) {
        return "&_page=" + pageNumber;
    }
    
    @Test
    public void searchShouldReturnEnoughRecordsToTestPagination() throws Exception {
        assertEquals("Result count should be " + NUMBER_OF_ITEM_RECORDS_IN_SEARCH_DB, 
                NUMBER_OF_ITEM_RECORDS_IN_SEARCH_DB, countResults(""));
    }

    @Test
    public void searchShouldReturnCorrectNumberOfItemsWhenPerPageIsSet() throws Exception {
        for (int i=1;i<=NUMBER_OF_ITEM_RECORDS_IN_SEARCH_DB;i++) {
            resultsShouldMatchPerPage(i);
        }
    }
    
    public void firstAndSecondPagesShouldShareNoCommonItems() throws Exception {
        final Set<Integer> firstPageIds = pageIds(1);
        final Set<Integer> secondPageIds = pageIds(2);
    }
}
