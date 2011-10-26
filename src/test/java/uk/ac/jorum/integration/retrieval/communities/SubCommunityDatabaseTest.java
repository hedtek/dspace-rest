package uk.ac.jorum.integration.retrieval.communities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.containsJSONKey;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.withValue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class SubCommunityDatabaseTest extends RestApiBaseTest {

	private static final Long TWO = new Long(2);
	private static final Long FOUR = new Long(4);


	@BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("subCommunityDatabase");
      startJetty();
    }
	
	@Test
	public void subCommunityShouldNotBeShownInTopLevelList() throws Exception {
	  String result = makeRequest("/communities");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  assertEquals(1, communityList.size());
	}

	@Test
	public void subCommunityShouldBeShownInCompleteList() throws Exception {
	  String result = makeRequest("/communities", "topLevelOnly=false");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  assertEquals(2, communityList.size());
	}

	@Test
	public void subCommunityShouldHaveParentInformation() throws Exception {
	  String result = makeRequest("/communities", "topLevelOnly=false");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  
	  JSONObject community = (JSONObject) communityList.get(1);
	  JSONObject parentCommunity = (JSONObject) community.get("parentCommunity");
	  assertThat(parentCommunity, containsJSONKey("id", withValue(TWO)));
	}
	  
	@Test
	public void parentLevelCommunityShouldHaveSubCommunityInformation() throws Exception {
	  String result = makeRequest("/communities", "topLevelOnly=false");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  
	  JSONObject community = (JSONObject) communityList.get(0);
	  JSONArray subCommunitiesList = (JSONArray) community.get("subCommunities");
	  JSONObject subCommunity = (JSONObject) subCommunitiesList.get(0);
	  assertThat(subCommunity, containsJSONKey("id", withValue(FOUR)));
	}
	  
}
