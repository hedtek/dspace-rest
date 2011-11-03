package uk.ac.jorum.integration.retrieval.communities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.*;
import static org.hamcrest.CoreMatchers.is;


import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class SubCommunityDatabaseTest extends RestApiBaseTest {
	
	private ArrayList<Matcher<JSONObject>> subCommunityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isCommunityId(4));
		}
	};

	private final Matcher<JSONObject> parentCommunity = isCommunity(2,
			"Community no 1", "123456789/2",
			"Introductory text for community no 1",
			"Short description of community no 1",
			"Side bar text for community 1", "Copyright information", 0,
			null, subCommunityListMatchers, emptyMatcherList(),
			emptyMatcherList());

	private final Matcher<JSONObject> community = isCommunity(4, "Sub-community", "123456789/4",
			"Introductory text for the sub-community",
			"This is a sub-community for a top-level community",
			"",	"", 0, parentCommunity, emptyMatcherList(),
			emptyMatcherList(), emptyMatcherList());
	
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
	  	  
	  ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {{
		add(parentCommunity);
		add(community);
	  }};
	  
	  assertThat(resultJSON, hasArray("communities_collection", communityListMatchers));
	}
	  
	@Test
	public void showCommunityAnchestorShouldHaveDataWithParentDetailsForSubcommunities() throws Exception {
		String result = makeRequest("/communities/4/anchestor");
		JSONObject anchestor = (JSONObject) JSONValue.parse(result);
		assertThat(anchestor, is(parentCommunity));
	}  
}
