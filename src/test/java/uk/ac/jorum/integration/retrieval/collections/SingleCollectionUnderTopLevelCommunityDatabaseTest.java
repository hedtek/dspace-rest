package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.containsJSONKey;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.withValue;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.HTTPStatusCode;
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
		assertThat(result,hasHTTPCode(HTTPStatusCode.SUCCESS));
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
	
	@Test
	public void showCollectionShouldHaveCorrectStructure() throws Exception {

		String result = makeRequest("/collections/1");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		structureAssertionsOn(collection);				
	}
	
	@Test
	public void showNonExistentCollectionShouldReturnNotFoundStatusCode() throws Exception {
		int result = getResponseCode("/collections/2");
		assertThat(result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));
	}
	
	@Test
	public void showCollectionInvalidElementShouldReturnBadRequestStatusCode() throws Exception {
		int result = getResponseCode("/collections/2/invalidelement");
		assertThat("Correct behaviour should return BAD_REQUEST", result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));
	}
	
	@Test
	public void showCollectionWithIdOnlyShouldReturnOnlyIds() throws Exception {
		String result = makeRequest("/collections/1", "idOnly=true");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		idOnlyStructureAssertionsOn(collection);		
	}

	@Test
	public void showCollectionIdShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/id");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, containsJSONKey("data", withValue(ONE)));
		entityAssertionsOn(collection);
	}
	
	@Test
	public void showCollectionNameShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/name");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, containsJSONKey("data", withValue("Collection 1")));
		entityAssertionsOn(collection);
	}
	
	@Test
	public void showCollectionCountItemsShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/countItems");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		final Long ZERO = new Long(0);
		assertThat(collection, containsJSONKey("data", withValue(ZERO)));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionHandleShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/handle");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, containsJSONKey("data", withValue("123456789/6")));
		entityAssertionsOn(collection);		
	}

	@Test
	public void showCollectionTypeShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/type");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		final Long THREE = new Long(3);
		
		assertThat(collection, containsJSONKey("data", withValue(THREE)));
		entityAssertionsOn(collection);		
	}

	@Test
	public void showCollectionCommunitiesShouldHaveDataField() throws Exception {
		String result = makeRequest("/collections/1/communities");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, containsJSONKey("data"));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionItemsShouldHaveDataField() throws Exception {
		String result = makeRequest("/collections/1/items");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, containsJSONKey("data"));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionCanEditShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/canedit");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, containsJSONKey("data", withValue(false)));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionLicenceShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/licence");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, containsJSONKey("data", withValue("Licence for collection 1")));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionProvenanceShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/provenance");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, containsJSONKey("data", withValue("Provenance for collection 1")));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionShortDescriptionShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/shortDescription");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, containsJSONKey("data", withValue("Short Description for Collection 1")));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionCopyrightTextShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/copyrightText");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, containsJSONKey("data", withValue("Copyright information for collection 1")));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionSidebarTextShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/sidebarText");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, containsJSONKey("data", withValue("Side bar text for collection 1")));
		entityAssertionsOn(collection);
	}
	
	@Test
	public void showCollectionIntroductoryTextShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/introText");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, containsJSONKey("data", withValue("Introductory Text for collection 1")));
		entityAssertionsOn(collection);
	}

	private void entityAssertionsOn(JSONObject collection) throws Exception{
		assertThat(collection, containsJSONKey("entityReference", withValue("/collections/1")));
		assertThat(collection, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/collections/1")));
		assertThat(collection, containsJSONKey("entityId"));
		assertThat(collection, containsJSONKey("displayTitle"));
		assertThat(collection, containsJSONKey("entityProperties"));
	}

	private void structureAssertionsOn(JSONObject collection) throws Exception{
		assertThat(collection, containsJSONKey("canEdit", withValue(false)));
		assertThat(collection, containsJSONKey("communities"));
		assertThat(collection, containsJSONKey("copyrightText", withValue("Copyright information for collection 1")));
		assertThat(collection, containsJSONKey("countItems"));
		assertThat(collection, containsJSONKey("handle", withValue("123456789/6")));
		assertThat(collection, containsJSONKey("id", withValue(ONE)));
		assertThat(collection, containsJSONKey("introText",	withValue("Introductory Text for collection 1")));
		assertThat(collection, containsJSONKey("items"));
		assertThat(collection, containsJSONKey("licence", withValue("Licence for collection 1")));
		assertThat(collection, containsJSONKey("name", withValue("Collection 1")));
		assertThat(collection, containsJSONKey("provenance",withValue("Provenance for collection 1")));
		assertThat(collection, containsJSONKey("shortDescription",withValue("Short Description for Collection 1")));
		assertThat(collection, containsJSONKey("sidebarText", withValue("Side bar text for collection 1")));
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
		assertThat(collection, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/collections/1")));
		assertThat(collection, containsJSONKey("entityId"));
	}
	
	
}
