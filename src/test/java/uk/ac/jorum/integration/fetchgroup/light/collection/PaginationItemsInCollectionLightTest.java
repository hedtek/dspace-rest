/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package uk.ac.jorum.integration.fetchgroup.light.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class PaginationItemsInCollectionLightTest extends RestApiBaseTest {


    private static final int COLLECTION1_ITEM_COUNT = 18;
	private static final int DEFAULT_PER_PAGE = 10;


    @BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("itemsInCollectionLightDatabase");
      startJetty();
    }


    @Test
    public void shouldBeRemainingItemsOnPage2() throws Exception {
        shouldBePerPageItemsPerPage(2, COLLECTION1_ITEM_COUNT - DEFAULT_PER_PAGE);
    }
    
    @Test
    public void shouldBePerPageItemsPerPageOnPage1() throws Exception {
        shouldBePerPageItemsPerPage(1, DEFAULT_PER_PAGE);
    }

    private void shouldBePerPageItemsPerPage(final int page, int expectedCount) throws Exception {
        shouldBeDefaultPerPageOnPage("&_page=" + page, expectedCount);
    }
    
	@Test
	public void shouldBePerPageItemsPerPage() throws Exception {
	    shouldBeDefaultPerPageOnPage("", DEFAULT_PER_PAGE);
	}

    private void shouldBeDefaultPerPageOnPage(final String pageQueryExpression, int expectedCount)
            throws Exception {
        String result = request(pageQueryExpression);
	    JSONObject collection = parse(result);
	    JSONArray items = (JSONArray) collection.get("items");
        assertEquals(result, expectedCount, items.size());
    }

    private JSONObject parse(String result) {
        return (JSONObject) JSONValue.parse(result);
    }


    private String request(final String pageQueryExpression) throws Exception {
        return makeRequest("/collections/1/items?fetch=light" + pageQueryExpression);
    }
}
