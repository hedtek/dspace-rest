package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.*;

import java.util.HashSet;
import java.util.Set;

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
		JSONArray collectionsList = (JSONArray) resultJSON.get("collections_collection");
		final int COLLECTION_ONE_ID = 1;
		final int COLLECTION_TWO_ID = 2;

		Integer[] idValues = {COLLECTION_ONE_ID,COLLECTION_TWO_ID};
		
		for (Object collection : collectionsList) {
			assertThat((JSONObject)collection, hasIdIn(idValues));
		}
	}
	
	@Test
	public void collectionListShouldContainUniqueCollections() throws Exception {
		String result = makeRequest("/collections");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray collectionsList = (JSONArray) resultJSON.get("collections_collection");
	
		Set<Long> ids = new HashSet<Long>();
		for (Object collection : collectionsList) {
			Long id = (Long)((JSONObject)collection).get("id");
			assertThat(ids, not(hasItem(id)));
			ids.add(id);
		}
	}
}
