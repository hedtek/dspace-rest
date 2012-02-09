/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.retrieval.items;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.fixtures.TwoItemsSingleCollectionInTopLevelCommunity.firstItem;
import static uk.ac.jorum.integration.matchers.fixtures.TwoItemsSingleCollectionInTopLevelCommunity.itemListIdOnlyMatchers;
import static uk.ac.jorum.integration.matchers.fixtures.TwoItemsSingleCollectionInTopLevelCommunity.secondItem;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class TwoItemsSingleCollectionTest extends RestApiBaseTest {
	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("twoItemsSingleCollectionInTopLevelCommunity");
		startJetty();
	}
	
	@Test
	public void itemsListSizeShouldBeTwo() throws Exception {
		String result = makeRequest("/items");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray itemsList = (JSONArray) resultJSON
				.get("items_collection");
		assertThat(itemsList.size(), is(equalTo(2)));
	}
	
	@Test
	public void itemsListItemShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("items_collection", itemListIdOnlyMatchers));
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
}
