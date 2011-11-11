/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.retrieval.communities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.fixtures.TwoTopLevelCommunities.communityListMatchers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class TwoTopLevelCommunitiesDatabaseTest extends RestApiBaseTest {
	@BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("twoTopLevelCommunitiesDatabase");
      startJetty();
    }
	
	@Test
  	public void communityListSizeShouldBeTwo() throws Exception {
	  String result = makeRequest("/communities");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  assertEquals(2, communityList.size());
  	}
	
	@Test
	public void communityListShouldHaveCorrectCommunities() throws Exception{
		String result = makeRequest("/communities");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("communities_collection", communityListMatchers));
	}
	
}
