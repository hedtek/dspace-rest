package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.fixtures.TwoCollectionsUnderTopLevelCommunity.collectionListMatchers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class MultipleCollectionsUnderTopLevelCommunityDatabaseTest extends RestApiBaseTest {
	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("twoCollectionsUnderTopLevelCommunityDatabase");
		startJetty();
	}
	
	@Test
	public void collectionSizeShouldBeGreaterThanOne() throws Exception {
		String result = makeRequest("/collections");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray collectionsList = (JSONArray) resultJSON.get("collections_collection");
		assertThat(collectionsList.size(), is(greaterThan(1)));
	}
	
	@Test
	public void collectionListShouldContainTheCorrectCollections() throws Exception {
		String result = makeRequest("/collections");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("collections_collection", collectionListMatchers));
	}
	
}