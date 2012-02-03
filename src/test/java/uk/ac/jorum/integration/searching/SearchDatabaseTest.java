/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.searching;

import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.emptySearchResultList;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.searchResultListWithJavaCommunity;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.searchResultListWithJavaCommunityId;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.searchResultListWithPomSprintItems;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.searchResultListWithProductBacklogItem;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.searchResultListWithProductBacklogItemId;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.searchResultListWithScrumTutorialItem;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.searchResultListWithSinatraCollection;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.searchResultListWithSinatraCollectionId;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.searchResultListWithTutorialItem;

import org.dspace.rest.diagnose.HTTPStatusCode;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class SearchDatabaseTest extends RestApiBaseTest {
	@BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("searchDatabase");
      startJetty();
    }

	@Test
  	public void searchWithoutQueryStringShouldReturnSuccessStatusCode() throws Exception{
	  int result = getResponseCode("/search");
	  assertThat(result, hasHTTPCode(HTTPStatusCode.SUCCESS));
  	}

	@Test
  	public void searchWithoutQueryStringShouldHaveCorrectStructure() throws Exception{
	  String result = makeRequest("/search");
	  JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
	  
	  assertThat("Result count should be 0", resultJSON, hasArray("search_collection", emptySearchResultList));
  	}


	@Test
  	public void searchWithNoResultsShouldReturnSuccessStatusCode() throws Exception{
	  int result = getResponseCode("/search", "query=nothing");
	  assertThat(result, hasHTTPCode(HTTPStatusCode.SUCCESS));
  	}

	@Test
  	public void searchWithNoResultsShouldHaveCorrectStructure() throws Exception{
	  String result = makeRequest("/search", "query=nothing");
	  JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
	  
	  assertThat("Result count should be 0", resultJSON, hasArray("search_collection", emptySearchResultList));
  	}
	
	@Test
	public void searchForCommunityShouldReturnCommunityWithCorrectStructure() throws Exception{
		String result = makeRequest("/search", "query=java");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 1", resultJSON, hasArray("search_collection", searchResultListWithJavaCommunity));
	}
	
	@Test
	public void searchForCommunityInIdOnlyModeShouldReturnCommunityWithCorrectStructure() throws Exception{
		String result = makeRequest("/search", "query=java&idOnly=true");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 1", resultJSON, hasArray("search_collection", searchResultListWithJavaCommunityId));
	}
	
	@Test
	public void searchForCollectionShouldReturnCollectionWithCorrectStructure() throws Exception {
		String result = makeRequest("/search", "query=sinatra");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 2", resultJSON, hasArray("search_collection", searchResultListWithSinatraCollection));
	}
	
	@Test
	public void searchForCollectionInIdOnlyModeShouldReturnCollectionWithCorrectStructure() throws Exception {
		String result = makeRequest("/search", "query=sinatra&idOnly=true");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 2", resultJSON, hasArray("search_collection", searchResultListWithSinatraCollectionId));
	}
	
	@Test
	public void searchForItemTitleShouldReturnItemWithCorrectStructure() throws Exception {
		String result = makeRequest("/search", "query=product%20backlog");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 1", resultJSON, hasArray("search_collection", searchResultListWithProductBacklogItem));
	}
	
	@Test
	public void searchForItemInIdOnlyModeShouldReturnCollectionWithCorrectStructure() throws Exception {
		String result = makeRequest("/search", "query=product%20backlog&idOnly=true");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 1", resultJSON, hasArray("search_collection", searchResultListWithProductBacklogItemId));
	}
	
	@Test
	public void searchForItemBitstreamTitleShouldReturnItemWithCorrectStructure() throws Exception {
		String result = makeRequest("/search", "query=product_backlog");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 1", resultJSON, hasArray("search_collection", searchResultListWithProductBacklogItem));
	}
	
	@Test
	public void searchForItemBitstreamTitleInIdOnlyModeShouldReturnCollectionWithCorrectStructure() throws Exception {
		String result = makeRequest("/search", "query=product_backlog&idOnly=true");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 1", resultJSON, hasArray("search_collection", searchResultListWithProductBacklogItemId));
	}
	
	@Test
	public void searchForItemAuthorShouldReturnItemWithCorrectStructure() throws Exception {
		String result = makeRequest("/search", "query=alan%20sugar");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 1", resultJSON, hasArray("search_collection", searchResultListWithProductBacklogItem));
	}
	
	@Test
	public void searchForItemAuthorInIdOnlyModeShouldReturnCollectionWithCorrectStructure() throws Exception {
		String result = makeRequest("/search", "query=alan%20sugar&idOnly=true");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 1", resultJSON, hasArray("search_collection", searchResultListWithProductBacklogItemId));
	}
	
	@Test
	public void searchShouldReturnMultipleIteItemsMatchingQueryFromSpecifiedCollectionmsMatchingTheQuery() throws Exception {
		String result = makeRequest("/search", "query=tutorial");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 8", resultJSON, hasArray("search_collection", searchResultListWithTutorialItem));
	}

	@Test
	public void searchShouldReturnItemsMatchingConjunctionQuery() throws Exception {
		String result = makeRequest("/search", "query=tutorial%20AND%20scrum");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 1", resultJSON, hasArray("search_collection", searchResultListWithScrumTutorialItem));
	}
	
	@Test
	public void searchShouldReturnItemsMatchingQueryFromSpecifiedCommunity() throws Exception {
		String result = makeRequest("/search", "query=product_backlog&community=3");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 1", resultJSON, hasArray("search_collection", searchResultListWithProductBacklogItem));
	}
	
	@Test
	public void searchShouldReturnItemsMatchingQueryFromSpecifiedCollection() throws Exception {
		String result = makeRequest("/search", "query=tutorial&collection=6");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 1", resultJSON, hasArray("search_collection", searchResultListWithScrumTutorialItem));
	}
	
	@Test
	public void searchItemWithinCommunityAndCollectionShouldReturnBadRequestResponseCode() throws Exception {
		int result = getResponseCode("/search", "query=product_backlog&community=3&collection=6");
		assertThat(result, hasHTTPCode(HTTPStatusCode.BAD_REQUEST));
	}

	@Test
	public void searchShouldReturnItemsMatchingMultipleKeywords() throws Exception {
		String result = makeRequest("/search", "query=pom%20sprint");
		JSONObject resultJSON = (JSONObject)JSONValue.parse(result);
		
		assertThat("Result count should be 2", resultJSON, hasArray("search_collection", searchResultListWithPomSprintItems));
	}
}
