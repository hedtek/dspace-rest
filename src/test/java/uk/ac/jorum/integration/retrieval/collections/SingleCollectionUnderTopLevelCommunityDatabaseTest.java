package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.containsJSONKey;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.withValue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class SingleCollectionUnderTopLevelCommunityDatabaseTest extends
		RestApiBaseTest {

	private static final Long ONE = new Long(1);

	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("singleCollectionUnderTopLevelCommunityDatabase");
		startJetty();
	}
	
	@Test
	public void collectionListShouldReturnSuccessStatusCode() throws Exception {
		int result = getResponseCode("/collections", "");
		assertThat(result, is(equalTo(200)));
	}

	@Test
	public void collectionListSizeShouldBeOne() throws Exception {
		String result = makeRequest("/collections");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray collectionsList = (JSONArray) resultJSON
				.get("collections_collection");
		assertThat(collectionsList.size(), is(equalTo(1)));
	}

	@Test
	public void collectionListItemShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/collections");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray collectionsList = (JSONArray) resultJSON
				.get("collections_collection");
		JSONObject collection = (JSONObject) collectionsList.get(0);
		structureAssertionsOn(collection);		
	}

	@Test
	public void collectionListWithIdOnlyShouldReturnOnlyIds() throws Exception {
		String result = makeRequest("/collections", "idOnly=true");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray collectionsList = (JSONArray) resultJSON.get("collections_collection");
		JSONObject collection = (JSONObject) collectionsList.get(0);
		idOnlyStructureAssertionsOn(collection);
	}

	private void structureAssertionsOn(JSONObject collection) throws Exception{
		assertThat(collection, containsJSONKey("canEdit", withValue(false)));
		assertThat(collection, containsJSONKey("communities"));
		assertThat(collection, containsJSONKey("copyrightText", withValue(null))); //withValue("Side bar text for collection 1")));
		assertThat(collection, containsJSONKey("countItems"));
		assertThat(collection, containsJSONKey("handle", withValue("123456789/6")));
		assertThat(collection, containsJSONKey("id", withValue(ONE)));
		assertThat(collection, containsJSONKey("introText",	withValue(null))); //withValue("Introductory Text for collection 1")));
		assertThat(collection, containsJSONKey("items"));
		assertThat(collection, containsJSONKey("licence", withValue("Licence for collection 1")));
		assertThat(collection, containsJSONKey("name", withValue("Collection 1")));
		assertThat(collection, containsJSONKey("provenance",withValue(null))); //withValue("Provenance for collection 1")));
		assertThat(collection, containsJSONKey("shortDescription",withValue(null))); //withValue("Short Description for collection 1")));
		assertThat(collection, containsJSONKey("sidebarText", withValue(null))); //withValue("Side bar text for collection 1")));
		assertThat(collection, containsJSONKey("type"));
		assertThat(collection, containsJSONKey("entityReference", withValue("/collections/1")));
		assertThat(collection, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/collections/1")));
		assertThat(collection, containsJSONKey("entityId"));
	}
	
	private void idOnlyStructureAssertionsOn(JSONObject collection) throws Exception{
		assertThat(collection, containsJSONKey("id", withValue(ONE)));
		assertThat(collection, not(containsJSONKey("name")));
		assertThat(collection, not(containsJSONKey("introductoryText")));
		assertThat(collection, containsJSONKey("entityReference", withValue("/collections/1")));
		assertThat(collection, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/collections/2")));
		assertThat(collection, containsJSONKey("entityId"));
	}
}
