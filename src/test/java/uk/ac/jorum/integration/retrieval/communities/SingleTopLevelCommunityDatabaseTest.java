package uk.ac.jorum.integration.retrieval.communities;

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

public class SingleTopLevelCommunityDatabaseTest extends RestApiBaseTest {

	private static final Long TWO = new Long(2);

	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("singleTopLevelCommunityDatabase");
		startJetty();
	}

	@Test
	public void communityListShouldReturnSuccessStatusCode() throws Exception {
		int result = getResponseCode("/communities", "");
		assertThat(result, is(equalTo(200)));
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

		assertThat(community, containsJSONKey("id", withValue(TWO)));
		assertThat(community,
				containsJSONKey("handle", withValue("123456789/2")));
		assertThat(community,
				containsJSONKey("name", withValue("Community no 1")));
		assertThat(
				community,
				containsJSONKey("introductoryText",
						withValue("Introductory text for community no 1")));
		assertThat(community,
				containsJSONKey("parentCommunity", withValue(null)));
		assertThat(community, containsJSONKey("recentSubmissions"));
		assertThat(community, containsJSONKey("shortDescription"));
		assertThat(community, containsJSONKey("sidebarText"));
		assertThat(community, containsJSONKey("subCommunities"));
		assertThat(community, containsJSONKey("type"));
		assertThat(community, containsJSONKey("administrators"));
		assertThat(community, containsJSONKey("canEdit", withValue(false)));
		assertThat(community, containsJSONKey("collections"));
		assertThat(community, containsJSONKey("copyrightText"));
		assertThat(community, containsJSONKey("countItems"));
		assertThat(community,
				containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(
				community,
				containsJSONKey(
						"entityURL",
						withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("entityTitle"));
	}

	@Test
	public void communityListWithIdOnlyShouldReturnOnlyIds() throws Exception {
		String result = makeRequest("/communities", "idOnly=true");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
		JSONObject community = (JSONObject) communityList.get(0);
		assertThat(community, containsJSONKey("id", withValue(TWO)));
		assertThat(community, not(containsJSONKey("name")));
		assertThat(community, not(containsJSONKey("introductoryText")));
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, not(containsJSONKey("parentCommunity")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, not(containsJSONKey("entityTitle")));
	}

	
	@Test
	public void showCommunityShouldHaveCorrectStructure() throws Exception {

		String result = makeRequest("/communities/2");
		JSONObject community = (JSONObject) JSONValue.parse(result);

		assertThat(community, containsJSONKey("id", withValue(TWO)));
		assertThat(community,
				containsJSONKey("handle", withValue("123456789/2")));
		assertThat(community,
				containsJSONKey("name", withValue("Community no 1")));
		assertThat(
				community,
				containsJSONKey("introductoryText",
						withValue("Introductory text for community no 1")));
		assertThat(community,
				containsJSONKey("parentCommunity", withValue(null)));
		assertThat(community, containsJSONKey("recentSubmissions"));
		assertThat(community, containsJSONKey("shortDescription"));
		assertThat(community, containsJSONKey("sidebarText"));
		assertThat(community, containsJSONKey("subCommunities"));
		assertThat(community, containsJSONKey("type"));
		assertThat(community, containsJSONKey("administrators"));
		assertThat(community, containsJSONKey("canEdit", withValue(false)));
		assertThat(community, containsJSONKey("collections"));
		assertThat(community, containsJSONKey("copyrightText"));
		assertThat(community, containsJSONKey("countItems"));

		assertThat(community,
				containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(
				community,
				containsJSONKey(
						"entityURL",
						withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("entityTitle"));
	}

	@Test
	public void showNonExistentCommunityShouldReturnNotFoundStatusCode() throws Exception {
		int result = getResponseCode("/communities/1", "");
		assertThat(result, is(equalTo(404)));
	}
	
	@Test
	public void showCommunityInvalidElementShouldReturnBadRequestStatusCode() throws Exception {
		int result = getResponseCode("/communities/2/invalidelement");
		assertThat(result, is(equalTo(400)));
	}
	
	@Test
	public void showCommunityWithIdOnlyShouldReturnOnlyIds() throws Exception {
		String result = makeRequest("/communities/2", "idOnly=true");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, containsJSONKey("id", withValue(TWO)));
		assertThat(community, not(containsJSONKey("name")));
		assertThat(community, not(containsJSONKey("introductoryText")));
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, not(containsJSONKey("parentCommunity")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, not(containsJSONKey("entityTitle")));
	}

	@Test
	public void showCommunityNameShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/name");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, containsJSONKey("data", withValue("Community no 1")));
		
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("displayTitle"));
		assertThat(community, containsJSONKey("entityProperties"));
		
	}
	
	@Test
	public void showCommunityCountItemsShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/countItems");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		final Long ZERO = new Long(0);
		assertThat(community, containsJSONKey("data", withValue(ZERO)));
		
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("displayTitle"));
		assertThat(community, containsJSONKey("entityProperties"));
		
	}
	
	@Test
	public void showCommunityHandleShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/handle");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, containsJSONKey("data", withValue("123456789/2")));
		
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("displayTitle"));
		assertThat(community, containsJSONKey("entityProperties"));
		
	}

	@Test
	public void showCommunityTypeShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/type");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		final Long FOUR = new Long(4);
		
		assertThat(community, containsJSONKey("data", withValue(FOUR)));
		
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("displayTitle"));
		assertThat(community, containsJSONKey("entityProperties"));
		
	}

	@Test
	public void showCommunityCollectionShouldHaveDataField() throws Exception {
		String result = makeRequest("/communities/2/collections");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, containsJSONKey("data"));
		
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("displayTitle"));
		assertThat(community, containsJSONKey("entityProperties"));
		
	}
	
	@Test
	public void showCommunityCanEditShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/canedit");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, containsJSONKey("data", withValue(false)));
		
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("displayTitle"));
		assertThat(community, containsJSONKey("entityProperties"));
		
	}
	
	@Test
	public void showCommunityChildrenShouldHaveDataField() throws Exception {
		String result = makeRequest("/communities/2/children");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, containsJSONKey("data"));
		
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("displayTitle"));
		assertThat(community, containsJSONKey("entityProperties"));
		
	}
	
	@Test
	public void showCommunityRecentShouldHaveDataField() throws Exception {
		String result = makeRequest("/communities/2/recent");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, containsJSONKey("data"));
		
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("displayTitle"));
		assertThat(community, containsJSONKey("entityProperties"));
		
	}
	
	@Test
	public void showCommunityShortDescriptionShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/shortDescription");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, containsJSONKey("data", withValue("Short description of community no 1")));
		
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("displayTitle"));
		assertThat(community, containsJSONKey("entityProperties"));
		
	}
	
	@Test
	public void showCommunityCopyrightTextShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/copyrightText");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, containsJSONKey("data", withValue("Copyright information")));
		
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("displayTitle"));
		assertThat(community, containsJSONKey("entityProperties"));
		
	}
	
	@Test
	public void showCommunitySidebarTextShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/sidebarText");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, containsJSONKey("data", withValue("Side bar text for community 1")));
		
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("displayTitle"));
		assertThat(community, containsJSONKey("entityProperties"));
		
	}
	
	@Test
	public void showCommunityIntroductoryTextShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/introductoryText");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, containsJSONKey("data", withValue("Introductory text for community no 1")));
		
		assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
		assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("displayTitle"));
		assertThat(community, containsJSONKey("entityProperties"));
		
	}
}

