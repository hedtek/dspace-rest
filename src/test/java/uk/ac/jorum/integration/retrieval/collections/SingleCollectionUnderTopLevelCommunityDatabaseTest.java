/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;
import static uk.ac.jorum.integration.matchers.EntityMatchers.isEntityElement;
import static uk.ac.jorum.integration.matchers.EntityMatchers.isEntityElementWithArray;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;
import static uk.ac.jorum.integration.matchers.fixtures.SingleCollectionUnderTopLevelCommunity.communityListIdOnlyMatchers;
import static uk.ac.jorum.integration.matchers.fixtures.SingleCollectionUnderTopLevelCommunity.communityListMatchers;
import static uk.ac.jorum.integration.matchers.fixtures.SingleCollectionUnderTopLevelCommunity.parentCommunity;

import org.dspace.rest.diagnose.HTTPStatusCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;
import uk.ac.jorum.integration.matchers.fixtures.AllCollectionMatchers;

public class SingleCollectionUnderTopLevelCommunityDatabaseTest extends
		RestApiBaseTest {



	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("singleCollectionUnderTopLevelCommunityDatabase");
		startJetty();
	}

	@Test
	public void collectionListShouldReturnSuccessStatusCode() throws Exception {
		int result = getResponseCode("/collections", "");
		assertThat(result, hasHTTPCode(HTTPStatusCode.SUCCESS));
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
		JSONArray collectionsList = (JSONArray) resultJSON
				.get("collections_collection");
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
	public void showNonExistentCollectionShouldReturnNotFoundStatusCode()
			throws Exception {
		int result = getResponseCode("/collections/2");
		assertThat(result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));
	}

	@Test
	public void showCollectionInvalidElementShouldReturnBadRequestStatusCode()
			throws Exception {
		int result = getResponseCode("/collections/2/invalidelement");
		assertThat("Correct behaviour should return BAD_REQUEST", result,
				hasHTTPCode(HTTPStatusCode.NOT_FOUND));
	}

	@Test
	public void showCollectionWithIdOnlyShouldReturnOnlyIds() throws Exception {
		String result = makeRequest("/collections/1", "idOnly=true");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		idOnlyStructureAssertionsOn(collection);
	}

	@Test
	public void showCollectionIdShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/collections/1/id");
		JSONObject resutJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resutJSON, isEntityElement(1, "collections", 1));
	}

	@Test
	public void showCollectionNameShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/collections/1/name");
		JSONObject resutJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resutJSON, isEntityElement(1, "collections", "Collection 1"));
	}

	@Test
	public void showCollectionCountItemsShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/collections/1/countItems");
		JSONObject resutJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resutJSON, isEntityElement(1, "collections", 0));
	}

	@Test
	public void showCollectionHandleShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/collections/1/handle");
		JSONObject resutJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resutJSON, isEntityElement(1, "collections", "123456789/6"));

	}

	@Test
	public void showCollectionTypeShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/collections/1/type");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(1, "collections", 3));
	}

	@Test
	public void showCollectionCommunitiesShouldHaveDataField() throws Exception {
		String result = makeRequest("/collections/1/communities");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(
				resultJSON,
				isEntityElementWithArray(1, "collections",
						communityListMatchers));
	}

	@Test
	public void showCollectionItemsShouldHaveDataField() throws Exception {
		String result = makeRequest("/collections/1/items");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON,
				isEntityElementWithArray(1, "collections", emptyMatcherList()));
	}

	@Test
	public void showCollectionCanEditShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/collections/1/canedit");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(1, "collections", false));
	}

	@Test
	public void showCollectionLicenceShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/collections/1/licence");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON,
				isEntityElement(1, "collections", "Licence for collection 1"));
	}

	@Test
	public void showCollectionProvenanceShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/collections/1/provenance");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(
				resultJSON,
				isEntityElement(1, "collections", "Provenance for collection 1"));
	}

	@Test
	public void showCollectionShortDescriptionShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/collections/1/shortDescription");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(
				resultJSON,
				isEntityElement(1, "collections",
						"Short Description for Collection 1"));
	}

	@Test
	public void showCollectionCopyrightTextShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/collections/1/copyrightText");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(
				resultJSON,
				isEntityElement(1, "collections",
						"Copyright information for collection 1"));
	}

	@Test
	public void showCollectionSidebarTextShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/collections/1/sidebarText");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(
				resultJSON,
				isEntityElement(1, "collections",
						"Side bar text for collection 1"));
	}

	@Test
	public void showCollectionIntroductoryTextShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/collections/1/introText");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(
				resultJSON,
				isEntityElement(1, "collections",
						"Introductory Text for collection 1"));
	}

	@Test
	public void communityOwningACollectionShouldContainCollectionInShowCommunityDetails()
			throws Exception {
		String result = makeRequest("/communities/2");
		JSONObject communityOwningACollection = (JSONObject) JSONValue
				.parse(result);
		assertThat(communityOwningACollection, is(parentCommunity));
	}

	private void structureAssertionsOn(JSONObject collection) throws Exception {
		assertThat(collection, is(AllCollectionMatchers.firstCollection(0,
		        communityListIdOnlyMatchers, emptyMatcherList())));
	}

	private void idOnlyStructureAssertionsOn(JSONObject collection)
			throws Exception {
		assertThat(collection, is(AllCollectionMatchers.firstCollectionId()));
	}
}