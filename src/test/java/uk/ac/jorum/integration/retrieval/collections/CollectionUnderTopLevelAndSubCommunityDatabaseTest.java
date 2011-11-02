package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;

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
		String result = makeRequest("/collections");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray collectionsList = (JSONArray)resultJSON.get("collections_collection");
		JSONObject collection = (JSONObject) collectionsList.get(collectionsList.size() - 1);
		JSONArray communitiesList = (JSONArray)collection.get("communities");

		assertThat(communitiesList.size(), is(greaterThan(1)));
	}
	
	public void topLevelCommunityCollectionsShouldNotListSubCommunityCollections() throws Exception {
		//TODO subCommunityCollectionShouldHaveMoreThanOneCommunityOwners
	}
	
	
}
