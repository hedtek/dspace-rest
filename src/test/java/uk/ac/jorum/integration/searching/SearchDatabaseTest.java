/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.searching;

import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;

import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.emptySearchResultList;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.searchResultListWithJavaCommunity;;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;

import static org.hamcrest.core.Is.is;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.HTTPStatusCode;
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
}
