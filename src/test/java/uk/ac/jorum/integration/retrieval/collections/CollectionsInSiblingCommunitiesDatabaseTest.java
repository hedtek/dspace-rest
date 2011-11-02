package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasId;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class CollectionsInSiblingCommunitiesDatabaseTest extends
		RestApiBaseTest {

	

	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("collectionsInSiblingCommunitiesDatabase");
		startJetty();
	}
	
	@Test
	public void communityListForACollectionShouldNotListSiblingCommunitiesOfOwningCommunity() throws Exception {
		String result = makeRequest("/collections/3");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		JSONArray communitiesList = (JSONArray)collection.get("communities");
		
		final int SIBLING_COMMUNITY_ID = 6;
		
		for (Object community : communitiesList) {
			assertThat((JSONObject)community, not(hasId(SIBLING_COMMUNITY_ID)));
		}
	}
	
	@Test
	public void collectionListForCommunityShouldNotListCollectionsOfSiblingCommunities() throws Exception {
		String result = makeRequest("/communities/4");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		JSONArray collectionsList = (JSONArray) community.get("collections");
		
		final int COLLECTION_ID = 6;
		
		for (Object collection : collectionsList) {
			assertThat((JSONObject)collection, not(hasId(COLLECTION_ID)));
		}
	}
	
}
