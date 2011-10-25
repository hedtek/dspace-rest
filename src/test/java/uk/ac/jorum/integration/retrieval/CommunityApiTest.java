/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package uk.ac.jorum.integration.retrieval;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.containsJSONKey;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.withValue;
import org.junit.BeforeClass;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class CommunityApiTest extends RestApiBaseTest {
  @BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("emptyDatabase");
      startJetty();
    }

  private static final Long TWO = new Long(2);
  private static final Long FOUR = new Long(4);
  
  @Test
    public void emptyCommunitiesList() throws Exception {
      //String result = makeRequest("/communities");
      //assertThat(result, containsString("\"communities_collection\": [\n\n]}"));
    }

  @Test
  	public void emptyCommunitiesListStatusCode() throws Exception{
	  int result = getResponseCode("/communities", "");
	  assertThat("200 is observed behaviour, should really be 204", result, is(equalTo(200)));
  	}

//  @Test
	public void communityListWithOneTopLevelCommunityStatusCode() throws Exception{
	  //loadFixture("singleTopLevelCommunityDatabase");dspace-rest/src/test/java/uk/ac/jorum/integration/retrieval/CommunityApiTest.java
	  int result = getResponseCode("/communities", "");
	  assertThat(result, is(equalTo(200)));
	}
  
//  @Test
  	public void communityListWithOneTopLevelCommunity() throws Exception {
	  //loadFixture("singleTopLevelCommunityDatabase");
	  String result = makeRequest("/communities");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  assertThat(communityList.size(), is(equalTo(1)));
  	}
  
//  @Test
  	public void communityListItemStructure() throws Exception {
	  //loadFixture("singleTopLevelCommunityDatabase");
	  String result = makeRequest("/communities");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  JSONObject community = (JSONObject) communityList.get(0);

	  assertThat(community, containsJSONKey("id", withValue(TWO)));
	  assertThat(community, containsJSONKey("handle", withValue("123456789/2")));
	  assertThat(community, containsJSONKey("name", withValue("Community no 1")));
	  assertThat(community, containsJSONKey("introductoryText", withValue("Introductory text for community no 1")));
 	  assertThat(community, containsJSONKey("parentCommunity", withValue(null)));
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
	  assertThat(community, containsJSONKey("entityReference", withValue("/communities/2")));
	  assertThat(community, containsJSONKey("entityURL", withValue("http://localhost:8080/dspace-rest/communities/2")));
	  assertThat(community, containsJSONKey("entityId"));
	  assertThat(community, containsJSONKey("entityTitle"));
  	}

  //@Test
	public void communityListWithIdOnly() throws Exception {
	  //loadFixture("singleTopLevelCommunityDatabase");
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

  
  //@Test
  	public void communityListWithMoreThanOneCommunity() throws Exception {
	  //loadFixture("twoTopLevelCommunitiesDatabase");
	  String result = makeRequest("/communities");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  assertEquals(2, communityList.size());
  	}
  
  //@Test
	public void subCommunityNotShownInTopLevelList() throws Exception {
	  loadFixture("subCommunityDatabase");
	  String result = makeRequest("/communities");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  assertEquals(1, communityList.size());
	}

  //@Test
	public void subCommunityIsShownInCompleteList() throws Exception {
	  loadFixture("subCommunityDatabase");
	  String result = makeRequest("/communities", "topLevelOnly=false");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  assertEquals(2, communityList.size());
	}

  //@Test
	public void subCommunityHasParentInformation() throws Exception {
	  loadFixture("subCommunityDatabase");
	  String result = makeRequest("/communities", "topLevelOnly=false");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  
	  JSONObject community = (JSONObject) communityList.get(1);
	  JSONObject parentCommunity = (JSONObject) community.get("parentCommunity");
	  assertThat(parentCommunity, containsJSONKey("id", withValue(TWO)));
	}
  
  //@Test
	public void topLevelCommunityHasSubCommunityInformation() throws Exception {
	  loadFixture("subCommunityDatabase");
	  String result = makeRequest("/communities", "topLevelOnly=false");
	  JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
	  JSONArray communityList = (JSONArray) resultJSON.get("communities_collection");
	  
	  JSONObject community = (JSONObject) communityList.get(0);
	  JSONArray subCommunitiesList = (JSONArray) community.get("subCommunities");
	  JSONObject subCommunity = (JSONObject) subCommunitiesList.get(0);
	  assertThat(subCommunity, containsJSONKey("id", withValue(FOUR)));
	}
  

}
