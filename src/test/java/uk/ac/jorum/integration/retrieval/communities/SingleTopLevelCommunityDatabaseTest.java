/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.retrieval.communities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;
import static uk.ac.jorum.integration.matchers.EntityMatchers.isEntityElement;
import static uk.ac.jorum.integration.matchers.EntityMatchers.isEntityElementWithArray;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;

import org.dspace.rest.diagnose.HTTPStatusCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;
import uk.ac.jorum.integration.matchers.fixtures.AllCommunityMatchers;

public class SingleTopLevelCommunityDatabaseTest extends RestApiBaseTest {
	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("singleTopLevelCommunityDatabase");
		startJetty();
	}

	@Test
	public void communityListShouldReturnSuccessStatusCode() throws Exception {
		int result = getResponseCode("/communities", "");
		assertThat(result, hasHTTPCode(HTTPStatusCode.SUCCESS));
	}

	@Test
	public void communityListSizeShouldBeOne() throws Exception {
		String result = makeRequest("/communities");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray communityList = (JSONArray) resultJSON
				.get("communities_collection");
		assertThat(communityList.size(), is(equalTo(1)));
	}

	@Test
	public void communityListItemShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/communities");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray communityList = (JSONArray) resultJSON
				.get("communities_collection");
		JSONObject community = (JSONObject) communityList.get(0);
		structureAssertionsOn(community);
	}

	@Test
	public void communityListWithIdOnlyShouldReturnOnlyIds() throws Exception {
		String result = makeRequest("/communities", "idOnly=true");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray communityList = (JSONArray) resultJSON
				.get("communities_collection");
		JSONObject community = (JSONObject) communityList.get(0);
		idOnlyStructureAssertionsOn(community);
	}

	@Test
	public void showCommunityShouldHaveCorrectStructure() throws Exception {

		String result = makeRequest("/communities/2");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		structureAssertionsOn(community);
	}

	@Test
	public void showNonExistentCommunityShouldReturnNotFoundStatusCode()
			throws Exception {
		int result = getResponseCode("/communities/1", "");
		assertThat(result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));
	}

	@Test
	public void showCommunityInvalidElementShouldReturnBadRequestStatusCode()
			throws Exception {
		int result = getResponseCode("/communities/2/invalidelement");
		assertThat(result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));
	}

	@Test
	public void showCommunityWithIdOnlyShouldReturnOnlyIds() throws Exception {
		String result = makeRequest("/communities/2", "idOnly=true");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		idOnlyStructureAssertionsOn(community);
	}

	@Test
	public void showCommunityIdShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/communities/2/id");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(2, "communities", 2));
	}

	@Test
	public void showCommunityNameShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/communities/2/name");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON,
				isEntityElement(2, "communities", "Community no 1"));
	}

	@Test
	public void showCommunityCountItemsShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/communities/2/countItems");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(2, "communities", 0));
	}

	@Test
	public void showCommunityHandleShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/communities/2/handle");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(2, "communities", "123456789/2"));
	}

	@Test
	public void showCommunityTypeShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/communities/2/type");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(2, "communities", 4));
	}

	@Test
	public void showCommunityCollectionShouldHaveDataField() throws Exception {
		String result = makeRequest("/communities/2/collections");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON,
				isEntityElementWithArray(2, "communities", emptyMatcherList()));
	}

	@Test
	public void showCommunityCanEditShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/communities/2/canedit");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(2, "communities", false));
	}

	@Test
	public void showCommunityChildrenShouldHaveDataField() throws Exception {
		String result = makeRequest("/communities/2/children");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON,
				isEntityElementWithArray(2, "communities", emptyMatcherList()));
	}

	@Test
	public void showCommunityRecentShouldHaveDataField() throws Exception {
		String result = makeRequest("/communities/2/recent");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON,
				isEntityElementWithArray(2, "communities", emptyMatcherList()));
	}

	@Test
	public void showCommunityShortDescriptionShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/communities/2/shortDescription");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(
				resultJSON,
				isEntityElement(2, "communities",
						"Short description of community no 1"));
	}

	@Test
	public void showCommunityCopyrightTextShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/communities/2/copyrightText");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON,
				isEntityElement(2, "communities", "Copyright information"));
	}

	@Test
	public void showCommunitySidebarTextShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/communities/2/sidebarText");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(
				resultJSON,
				isEntityElement(2, "communities",
						"Side bar text for community 1"));
	}

	@Test
	public void showCommunityIntroductoryTextShouldHaveDataFieldWithCorrectValue()
			throws Exception {
		String result = makeRequest("/communities/2/introductoryText");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(
				resultJSON,
				isEntityElement(2, "communities",
						"Introductory text for community no 1"));
	}

	@Test
	public void showCommunityAnchestorShouldReturnNotFoundStatusCodeForTopLevelCommunity()
			throws Exception {
		int result = getResponseCode("/collections/2/anchestor", "");
		assertThat(result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));

	}

	private void structureAssertionsOn(JSONObject community) throws Exception {
		assertThat(community, is(AllCommunityMatchers.firstCommunity(0,
				emptyMatcherList(), emptyMatcherList(), emptyMatcherList())));
	}

	private void idOnlyStructureAssertionsOn(JSONObject community)
			throws Exception {
		assertThat(community, is(AllCommunityMatchers.firstCommunityId()));
	}
}
