package uk.ac.jorum.integration.retrieval.communities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunity;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class TwoTopLevelCommunitiesDatabaseTest extends RestApiBaseTest {

	private final Matcher<JSONObject> topCommunityOne = isCommunity(2,
			"Community no 1", "123456789/2",
			"Introductory text for community no 1",
			"Short description of community no 1",
			"Side bar text for community 1", "Copyright information", 0,
			null, emptyMatcherList(), emptyMatcherList(),
			emptyMatcherList());
	
	private final Matcher<JSONObject> topCommunityTwo = isCommunity(3,
			"Top level community no 2", "123456789/3",
			null, null,null, null, 0, null, 
			emptyMatcherList(), emptyMatcherList(),	emptyMatcherList());
	
	private final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(topCommunityOne);
			add(topCommunityTwo);
		}
	};
	
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
