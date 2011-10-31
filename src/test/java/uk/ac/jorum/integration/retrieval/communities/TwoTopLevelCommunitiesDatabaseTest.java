package uk.ac.jorum.integration.retrieval.communities;

import static org.junit.Assert.assertEquals;

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
	
	//TODO Check if its the correct communities 
}
