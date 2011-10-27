package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.containsJSONKey;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class SingleCommunityWithoutCollectionsDatabaseTest extends
		RestApiBaseTest {
	
	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("singleTopLevelCommunityDatabase");
		startJetty();
	}
	
	@Test
    public void collectionsListShouldBeEmpty() throws Exception {
      String result = makeRequest("/collections");
      JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
      JSONArray collectionsList = (JSONArray) resultJSON
				.get("collections_collection");
		assertThat(collectionsList.size(), is(equalTo(0)));
    }

	@Test
  	public void emptyCollectionsListShouldReturnNoContentStatusCode() throws Exception{
	  int result = getResponseCode("/collections", "");
	  assertThat("200 is observed behaviour, should really be 204", result, is(equalTo(200)));
  	}
}
