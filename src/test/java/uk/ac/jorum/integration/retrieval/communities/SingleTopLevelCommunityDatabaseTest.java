package uk.ac.jorum.integration.retrieval.communities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;
import static uk.ac.jorum.integration.matchers.EntityMatchers.*;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;

import static org.hamcrest.CoreMatchers.allOf;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.HTTPStatusCode;
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
		JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
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
	public void showNonExistentCommunityShouldReturnNotFoundStatusCode() throws Exception {
		int result = getResponseCode("/communities/1", "");
		assertThat(result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));
	}
	
	@Test
	public void showCommunityInvalidElementShouldReturnBadRequestStatusCode() throws Exception {
		int result = getResponseCode("/communities/2/invalidelement");
		assertThat(result, hasHTTPCode(HTTPStatusCode.BAD_REQUEST));
	}
	
	@Test
	public void showCommunityWithIdOnlyShouldReturnOnlyIds() throws Exception {
		String result = makeRequest("/communities/2", "idOnly=true");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		idOnlyStructureAssertionsOn(community);		
	}
	
	@Test
	public void showCommunityIdShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/id");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, hasKey("data", withValue(TWO)));
		entityAssertionsOn(community);
	}
	
	@Test
	public void showCommunityNameShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/name");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, hasKey("data", withValue("Community no 1")));
		entityAssertionsOn(community);
	}
	
	@Test
	public void showCommunityCountItemsShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/countItems");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		final Long ZERO = new Long(0);
		assertThat(community, hasKey("data", withValue(ZERO)));
		entityAssertionsOn(community);		
	}
	
	@Test
	public void showCommunityHandleShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/handle");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, hasKey("data", withValue("123456789/2")));
		entityAssertionsOn(community);		
	}

	@Test
	public void showCommunityTypeShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/type");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		final Long FOUR = new Long(4);
		
		assertThat(community, hasKey("data", withValue(FOUR)));
		entityAssertionsOn(community);		
	}

	@Test
	public void showCommunityCollectionShouldHaveDataField() throws Exception {
		String result = makeRequest("/communities/2/collections");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, hasKey("data"));
		entityAssertionsOn(community);		
	}
	
	@Test
	public void showCommunityCanEditShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/canedit");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, hasKey("data", withValue(false)));
		entityAssertionsOn(community);		
	}
	
	@Test
	public void showCommunityChildrenShouldHaveDataField() throws Exception {
		String result = makeRequest("/communities/2/children");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, hasKey("data"));
		entityAssertionsOn(community);		
	}
	
	@Test
	public void showCommunityRecentShouldHaveDataField() throws Exception {
		String result = makeRequest("/communities/2/recent");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, hasKey("data"));
		entityAssertionsOn(community);		
	}
	
	@Test
	public void showCommunityShortDescriptionShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/shortDescription");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, hasKey("data", withValue("Short description of community no 1")));
		entityAssertionsOn(community);		
	}
	
	@Test
	public void showCommunityCopyrightTextShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/copyrightText");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, hasKey("data", withValue("Copyright information")));
		entityAssertionsOn(community);		
	}
	
	@Test
	public void showCommunitySidebarTextShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/sidebarText");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, hasKey("data", withValue("Side bar text for community 1")));
		entityAssertionsOn(community);
	}
	
	@Test
	public void showCommunityIntroductoryTextShouldHaveDataFieldWithCorrectValue() throws Exception {
		String result = makeRequest("/communities/2/introductoryText");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		
		assertThat(community, hasKey("data", withValue("Introductory text for community no 1")));
		entityAssertionsOn(community);
	}
	
	@Test
	public void showCommunityAnchestorShouldReturnNotFoundStatusCodeForTopLevelCommunity() throws Exception {
		int result = getResponseCode("/collections/2/anchestor", "");
		assertThat(result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));

	}
	
	private void entityAssertionsOn(JSONObject community) throws Exception{
		assertThat(community, is(allOf(
				hasEntityReference("/communities/2"),
				hasEntityURL("http://localhost:8080/dspace-rest/communities/2"),
				hasEntityId(),
				hasKey("displayTitle"),
				hasKey("entityProperties")
				)));
	}
	
	private void structureAssertionsOn(JSONObject community) throws Exception{
		assertThat(community, is(allOf(
				hasId(2),
				hasHandle("123456789/2"),
				hasName("Community no 1"),
				hasKey("introductoryText",	withValue("Introductory text for community no 1")),
				hasKey("parentCommunity", withValue(null)),
				hasKey("recentSubmissions"),
				hasKey("shortDescription"),
				hasKey("sidebarText"),
				hasKey("subCommunities"),
				hasType(4),
				hasKey("administrators"),
				cannotBeEdited(),
				hasKey("collections"),
				hasKey("copyrightText"),
				hasKey("countItems"),
				hasEntityReference("/communities/2"),
				hasEntityURL("http://localhost:8080/dspace-rest/communities/2"),
				hasEntityId(),
				hasKey("entityTitle")
				)));
	}
	
	private void idOnlyStructureAssertionsOn(JSONObject community) throws Exception{
		assertThat(community, is(allOf(
				hasId(2),
				hasNo("name"),
				hasNo("introductoryText"),
				hasEntityReference("/communities/2"),
				hasEntityURL("http://localhost:8080/dspace-rest/communities/2"),
				hasNo("parentCommunity"),
				hasEntityId(),
				hasNo("entityTitle")
				)));	
	}
}

