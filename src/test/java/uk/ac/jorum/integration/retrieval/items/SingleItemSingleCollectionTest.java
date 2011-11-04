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
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollection;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionWithId;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunity;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItem;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItemWithId;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.HTTPStatusCode;
import uk.ac.jorum.integration.RestApiBaseTest;


public class SingleItemSingleCollectionTest extends RestApiBaseTest {


	private static final ArrayList<Matcher<JSONObject>> subCommunityListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isCommunityWithId(4));
		}
	};

	private static final ArrayList<Matcher<JSONObject>> collectionListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isCollectionWithId(1));
		}
	};
		
	private static final ArrayList<Matcher<JSONObject>> communityListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isCommunityWithId(2));
		}
	};
	
	private static final ArrayList<Matcher<JSONObject>> itemListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isItemWithId(1));
		}
	};
	private static final Matcher<JSONObject> owningCollection = isCollection(1, "Collection 1", "123456789/6",
			"Introductory Text for collection 1",
			"Short Description for Collection 1",
			"Side bar text for collection 1",
			"Copyright information for collection 1",
			"Licence for collection 1",
			"Provenance for collection 1", 1, communityListWithIdMatchers, itemListWithIdMatchers);
	private static final ArrayList<Matcher<JSONObject>> collectionListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(owningCollection);
		}
	};
	
	private static final Matcher<JSONObject> parentCommunity = isCommunity(2,
			"Community no 1", "123456789/2",
			"Introductory text for community no 1",
			"Short description of community no 1",
			"Side bar text for community 1", "Copyright information", 1,
			null, subCommunityListWithIdMatchers, emptyMatcherList(),
			collectionListWithIdMatchers);
	
	private static final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(parentCommunity);
		}
	};
	
	private static final Matcher<JSONObject> item = isItem(1, 
			"First Upload", "123456789/7", true, false, owningCollection, communityListMatchers, collectionListMatchers); 
	
	private static final ArrayList<Matcher<JSONObject>> itemMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(item);
		}
	};
	
	@BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("singleItemSingleCollectionTopLevelCommunity");
      startJetty();
    }
	
	//@Test
	public void itemsListShouldReturnSuccessStatusCode() throws Exception {
		int result = getResponseCode("/items");
		assertThat(result,hasHTTPCode(HTTPStatusCode.SUCCESS));
	}

	//@Test
	public void itemsListSizeShouldBeOne() throws Exception {
		String result = makeRequest("/items");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray itemsList = (JSONArray) resultJSON
				.get("items_collection");
		assertThat(itemsList.size(), is(equalTo(1)));
	}
	
	//@Test
	public void itemsListItemShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("items_collection", itemMatchers));
	}

	//@Test
	public void itemListWithIdOnlyShouldReturnOnlyIds() throws Exception {
		String result = makeRequest("/items", "idOnly=true");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("items_collection",	itemListWithIdMatchers));
	}
	
	@Test
	public void showItemShouldHaveCorrectStructure() throws Exception{
		String result = makeRequest("/items/1");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, item);
	}
}
