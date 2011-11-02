package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasIdIn;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasId;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.HTTPStatusCode;
import uk.ac.jorum.integration.RestApiBaseTest;

public class CollectionUnderTopLevelAndSubCommunityDatabaseTest extends
		RestApiBaseTest {

	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("collectionUnderTopLevelAndSubCommunityDatabase");
		startJetty();
	}
	
	@Test
	public void collectionListShouldReturnSuccessStatusCode() throws Exception {
		int result = getResponseCode("/collections");
		assertThat(result,hasHTTPCode(HTTPStatusCode.SUCCESS));
	}
	
	@Test
	public void subCommunityCollectionShouldHaveMoreThanOneCommunityOwners() throws Exception {
		String result = makeRequest("/collections/3");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		JSONArray communitiesList = (JSONArray)collection.get("communities");

		assertThat(communitiesList.size(), is(greaterThan(1)));
	}
	
	@Test
	public void subCommunityCollectionListsParentCommunityUnderItsCommunities() throws Exception {
		String result = makeRequest("/collections/3");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		JSONArray communitiesList = (JSONArray)collection.get("communities");
		
		final int TOP_LEVEL_COMMUNITY_ID = 2;
		final int SUB_COMMUNITY_ID = 4;
		Integer[] idValues = {TOP_LEVEL_COMMUNITY_ID, SUB_COMMUNITY_ID};
		
		for (Object community : communitiesList) {
			assertThat((JSONObject)community, hasIdIn(idValues));
		}
	}
	
	@Test
	public void topLevelCommunityCollectionsShouldNotListSubCommunityCollections() throws Exception {
		String result = makeRequest("/communities/2");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		JSONArray collectionsList = (JSONArray) community.get("collections");
		
		final int COLLECTION_ID = 3;
		
		for (Object collection : collectionsList) {
			assertThat((JSONObject)collection, not(hasId(COLLECTION_ID)));
		}
	}
	
}
