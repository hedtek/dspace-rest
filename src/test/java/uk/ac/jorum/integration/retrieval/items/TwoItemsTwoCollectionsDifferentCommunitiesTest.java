package uk.ac.jorum.integration.retrieval.items;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.fixtures.TwoItemsInSeparateCollectionsInSeparateTopLevelCommunities.*;
import uk.ac.jorum.integration.RestApiBaseTest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

public class TwoItemsTwoCollectionsDifferentCommunitiesTest extends
		RestApiBaseTest {
	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("twoItemsInSeparateCollectionsInSeparateTopLevelCommunities");
		startJetty();
	}
	
	@Test
	public void itemsListSizeShouldBeTwo() throws Exception {
		String result = makeRequest("/items");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray itemsList = (JSONArray) resultJSON.get("items_collection");
		assertThat(itemsList.size(), is(equalTo(2)));
	}
	
	@Test
	public void itemsListItemShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("items_collection", itemMatchers));
	}
	
	@Test
	public void showItem1ShouldHaveCorrectStructure() throws Exception{
		String result = makeRequest("/items/1");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, firstItem);
	}
	
	@Test
	public void showItem2ShouldHaveCorrectStructure() throws Exception{
		String result = makeRequest("/items/2");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, secondItem);
	}
	
	@Test
	public void collectionOneItemsShouldHaveTheItem() throws Exception {
		String result = makeRequest("/collections/1/items");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("data", firstItemForCollectionMatchers));
	}
	
	@Test
	public void collectionTwoItemsShouldHaveTheItem() throws Exception {
		String result = makeRequest("/collections/2/items");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("data", secondItemForCollectionMatchers));
	}
}
