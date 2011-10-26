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

}
