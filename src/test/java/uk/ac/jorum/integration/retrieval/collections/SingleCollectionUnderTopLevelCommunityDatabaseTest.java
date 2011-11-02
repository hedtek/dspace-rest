package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;
import static uk.ac.jorum.integration.matchers.EntityMatchers.*;

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
		
		assertThat(collection, hasKey("data", withValue(ONE)));
		entityAssertionsOn(collection);
	}
	
	@Test
	public void showCollectionNameShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/name");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, hasKey("data", withValue("Collection 1")));
		entityAssertionsOn(collection);
	}
	
	@Test
	public void showCollectionCountItemsShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/countItems");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		final Long ZERO = new Long(0);
		assertThat(collection, hasKey("data", withValue(ZERO)));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionHandleShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/handle");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, hasKey("data", withValue("123456789/6")));
		entityAssertionsOn(collection);		
	}

	@Test
	public void showCollectionTypeShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/type");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		final Long THREE = new Long(3);
		
		assertThat(collection, hasKey("data", withValue(THREE)));
		entityAssertionsOn(collection);		
	}

	@Test
	public void showCollectionCommunitiesShouldHaveDataField() throws Exception {
		String result = makeRequest("/collections/1/communities");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, hasKey("data"));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionItemsShouldHaveDataField() throws Exception {
		String result = makeRequest("/collections/1/items");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, hasKey("data"));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionCanEditShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/canedit");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, hasKey("data", withValue(false)));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionLicenceShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/licence");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, hasKey("data", withValue("Licence for collection 1")));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionProvenanceShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/provenance");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, hasKey("data", withValue("Provenance for collection 1")));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionShortDescriptionShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/shortDescription");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, hasKey("data", withValue("Short Description for Collection 1")));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionCopyrightTextShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/copyrightText");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, hasKey("data", withValue("Copyright information for collection 1")));
		entityAssertionsOn(collection);		
	}
	
	@Test
	public void showCollectionSidebarTextShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/sidebarText");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, hasKey("data", withValue("Side bar text for collection 1")));
		entityAssertionsOn(collection);
	}
	
	@Test
	public void showCollectionIntroductoryTextShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/collections/1/introText");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		
		assertThat(collection, hasKey("data", withValue("Introductory Text for collection 1")));
		entityAssertionsOn(collection);
	}


	@Test
	public void communityOwningACollectionShouldContainCollectionInShowCommunityDetails() throws Exception {
		String result = makeRequest("/communities/2");
		JSONObject communityOwningACollection = (JSONObject) JSONValue.parse(result);
		JSONArray collectionsList = (JSONArray)communityOwningACollection.get("collections");
		JSONObject collection = (JSONObject)collectionsList.get(0);
		
		assertThat(collection, hasId(1));
	}
	
	private void entityAssertionsOn(JSONObject collection) throws Exception{
		assertThat(collection, is(allOf(
				hasEntityReference("/collections/1"),
				hasEntityURL("http://localhost:8080/dspace-rest/collections/1"),
				hasEntityId(), 
				hasKey("displayTitle"),
				hasKey("entityProperties")
				)));
	}

	private void structureAssertionsOn(JSONObject collection) throws Exception{
		assertThat(
				collection,
				isCollection(1, "Collection 1", "123456789/6",
						"Introductory Text for collection 1",
						"Short Description for Collection 1",
						"Side bar text for collection 1",
						"Copyright information for collection 1",
						"Licence for collection 1",
						"Provenance for collection 1", 0, null, null));
	}
	
	private void idOnlyStructureAssertionsOn(JSONObject collection) throws Exception{
		assertThat(collection, isCollectionId(1));
	}
		
}