package uk.ac.jorum.integration.retrieval.communities;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.*;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;
import uk.ac.jorum.integration.matchers.ContainsJSONKey;

public class SubCommunityDatabaseTest extends RestApiBaseTest {
	
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
	public void subCommunityInListShouldHaveParentInformation() throws Exception {
	  String result = makeRequest("/communities", "topLevelOnly=false");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  
	  
	  ArrayList<Matcher<JSONObject>> subCommunityListMatchers = new ArrayList<Matcher<JSONObject>>(){{
		  add(isCommunityId(4));
	  }};

	  final Matcher<JSONObject> parentCommunityMatcher = isCommunity(2, "Community no 1", "123456789/2",
				"Introductory text for community no 1",
				"Short description of community no 1",
				"Side bar text for community 1",
				"Copyright information", 0, null, subCommunityListMatchers,
				emptyMatcherList(), emptyMatcherList());
	  final Matcher<JSONObject> communityMatcher = isCommunity(4, "Sub-community", "123456789/4",
				"Introductory text for the sub-community",
				"This is a sub-community for a top-level community",
				"",	"", 0, parentCommunityMatcher, emptyMatcherList(),
				emptyMatcherList(), emptyMatcherList());
	  
	  ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {{
		add(parentCommunityMatcher);
		add(communityMatcher);
	  }};
	  
	  assertThat(resultJSON, hasArray("communities_collection", communityListMatchers));
	}
	  
	@Test
	public void parentLevelCommunityShouldHaveSubCommunityInformation() throws Exception {
	  String result = makeRequest("/communities", "topLevelOnly=false");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  
	  JSONObject community = (JSONObject) communityList.get(0);
	  JSONArray subCommunitiesList = (JSONArray) community.get("subCommunities");
	  JSONObject subCommunity = (JSONObject) subCommunitiesList.get(0);
	  assertThat(subCommunity, hasId(4));
	}
	
	@Test
	public void showCommunityAnchestorShouldHaveDataWithParentDetailsForSubcommunities() throws Exception {
		String result = makeRequest("/communities/4/anchestor");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, hasId(2));
	}  
}
