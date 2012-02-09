package uk.ac.jorum.integration.searching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
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
        return pageIds(pageNumber, 5);
    }

    private Set<Integer> pageIds(int pageNumber, int perPage) throws Exception {
        return resultIds(requestJSON(perPageParameter(perPage) + pageParameter(pageNumber)));
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


    private void checkNumberOfItemsOnLastPage(final int perpage) throws Exception {
        int page = (int)Math.round(((float)NUMBER_OF_ITEM_RECORDS_IN_SEARCH_DB / (float)perpage) + 0.5);
        int remainingNumberOfItems = NUMBER_OF_ITEM_RECORDS_IN_SEARCH_DB % perpage;
        assertEquals("Page " + page + " should contain the number of results specified by perpage " + perpage + " when total number of items is " + NUMBER_OF_ITEM_RECORDS_IN_SEARCH_DB, 
                remainingNumberOfItems, pageIds(page, perpage).size());
    }

    private void checkNumberOfItemsOnPage(final int page, final int perpage) throws Exception {
        assertEquals("Page " + page + " should contain the number of results specified by perpage " + perpage, perpage, pageIds(page, perpage).size());
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
    
    @Test
    public void firstAndSecondPagesShouldShareNoCommonItems() throws Exception {
        final Set<Integer> firstPageIds = pageIds(1);
        final Set<Integer> secondPageIds = pageIds(2);
        assertTrue("No shared items should appear on the first and second pages." + firstPageIds + "," + secondPageIds, CollectionUtils.intersection(firstPageIds, secondPageIds).isEmpty());
    }
    
    @Test
    public void firstAndThirdPagesShouldShareNoCommonItems() throws Exception {
        final int perPage = 2;
        final Set<Integer> firstPageIds = pageIds(1, perPage);
        final Set<Integer> thirdPageIds = pageIds(3, perPage);
        assertTrue("No shared items should appear on the first and second pages." + firstPageIds + "," + thirdPageIds, CollectionUtils.intersection(firstPageIds, thirdPageIds).isEmpty());
    }
    
    @Test
    public void firstAndFourthPagesShouldShareNoCommonItems() throws Exception {
        final int perPage = 2;
        final Set<Integer> firstPageIds = pageIds(1, perPage);
        final Set<Integer> fourthPageIds = pageIds(4, perPage);
        assertTrue("No shared items should appear on the first and second pages." + firstPageIds + "," + fourthPageIds , CollectionUtils.intersection(firstPageIds, fourthPageIds).isEmpty());
    }


    @Test
    public void page1ShouldReturnCorrectNumberOfItemsPerPageSize4() throws Exception {
        checkNumberOfItemsOnPage(1, 4);
    }
    
    @Test
    public void page2ShouldReturnCorrectNumberOfItemsPerPageSize4() throws Exception {
        checkNumberOfItemsOnPage(2, 4);
    }
    
    @Test
    public void page1ShouldReturnCorrectNumberOfItemsPerPageSize3() throws Exception {
        checkNumberOfItemsOnPage(1, 3);
    }
    
    @Test
    public void page2ShouldReturnCorrectNumberOfItemsPerPageSize3() throws Exception {
        checkNumberOfItemsOnPage(2, 3);
    }

    @Test
    public void lastPageShouldReturnCorrectNumberOfItemsPerPageSize3() throws Exception {
        checkNumberOfItemsOnLastPage(3);
    }

    @Test
    public void lastPageShouldReturnCorrectNumberOfItemsPerPageSize5() throws Exception {
        checkNumberOfItemsOnLastPage(5);
    }

    @Test
    public void lastPageShouldReturnCorrectNumberOfItemsPerPageSize6() throws Exception {
        checkNumberOfItemsOnLastPage(6);
    }

    @Test
    public void lastPageShouldReturnCorrectNumberOfItemsPerPageSize7() throws Exception {
        checkNumberOfItemsOnLastPage(7);
    }
    
    @Test
    public void page1ShouldReturnCorrectNumberOfItemsPerPageSize2() throws Exception {
        checkNumberOfItemsOnPage(1, 2);
    }
    
    @Test
    public void page2ShouldReturnCorrectNumberOfItemsPerPageSize2() throws Exception {
        checkNumberOfItemsOnPage(2, 2);
    }

    @Test
    public void page3ShouldReturnCorrectNumberOfItemsPerPageSize2() throws Exception {
        checkNumberOfItemsOnPage(3, 2);
    }

    @Test
    public void page4ShouldReturnCorrectNumberOfItemsPerPageSize2() throws Exception {
        checkNumberOfItemsOnPage(4, 2);
    }
}
