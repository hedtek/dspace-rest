package uk.ac.jorum.integration.retrieval;

import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.containsJSONKey;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.withValue;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class ShowCommunityApiTest extends RestApiBaseTest {

	private static final Long TWO = new Long(2);
	
  @BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("singleTopLevelCommunityDatabase");
      startJetty();
    }
  
	@Test
	public void showCommunityDetails() throws Exception {
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
		/*assertThat(
				community,
				containsJSONKey(
						"entityURL",
						withValue("http://localhost:9090/dspace-rest/communities/2")));*/
		assertThat(community, containsJSONKey("entityId"));
		assertThat(community, containsJSONKey("entityTitle"));
	}
}
