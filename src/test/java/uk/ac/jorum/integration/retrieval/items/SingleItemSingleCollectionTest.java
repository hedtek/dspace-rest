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
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollection;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionWithId;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunity;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.EntityMatchers.withValue;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItem;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItemWithId;
import static uk.ac.jorum.integration.matchers.ItemMatchers.getMetadataMatchers;
import static uk.ac.jorum.integration.matchers.UserMatchers.isUser;
import static uk.ac.jorum.integration.matchers.BitstreamsMatchers.isBitstream;
import static uk.ac.jorum.integration.matchers.BitstreamsMatchers.isBitstreamWithId;
import static uk.ac.jorum.integration.matchers.BundleMatchers.isBundle;
import static uk.ac.jorum.integration.matchers.BundleMatchers.isBundleWithId;

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
	
	private static final ArrayList<Matcher<JSONObject>> bundleIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBundleWithId(1));
		}
	};
	
	private static final Matcher<JSONObject> submitter =  isUser(1, "dspace", "dspace", "dspace@example.com");
	
	private static final Matcher<JSONObject> bitstream = isBitstream(1, "firstUpload.txt", "Text", "text/plain", bundleIdMatchers);
	
	private static final ArrayList<Matcher<JSONObject>> bitstreamMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(bitstream);
		}
	};
	
	private static final ArrayList<Matcher<JSONObject>> bitstreamIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBitstreamWithId(1));
			add(isBitstreamWithId(2));
		}
	};
	
	private static final Matcher<JSONObject> bundle1 = isBundle(1, "ORIGINAL", itemListWithIdMatchers, bitstreamIdMatchers);
	private static final Matcher<JSONObject> bundle2 = isBundle(2, "LICENSE", itemListWithIdMatchers, bitstreamIdMatchers);
	
	private static final ArrayList<Matcher<JSONObject>> bundleMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(bundle1);
			add(bundle2);
		}
	};
	
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
			"First Upload", "123456789/7", true, false, owningCollection, submitter, communityListMatchers, collectionListMatchers, bundleMatchers, bitstreamMatchers); 
	
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
	
	@Test
	public void itemsListShouldReturnSuccessStatusCode() throws Exception {
		int result = getResponseCode("/items");
		assertThat(result,hasHTTPCode(HTTPStatusCode.SUCCESS));
	}

	@Test
	public void itemsListSizeShouldBeOne() throws Exception {
		String result = makeRequest("/items");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray itemsList = (JSONArray) resultJSON
				.get("items_collection");
		assertThat(itemsList.size(), is(equalTo(1)));
	}
	
	@Test
	public void itemsListItemShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("items_collection", itemMatchers));
	}

	@Test
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
	
	@Test
	public void showItemMetadataShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/metadata");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("data", getMetadataMatchers()));
	}
	
	@Test
	public void showItemSubmitterShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/submitter");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, submitter);
	}
	
	@Test
	public void showItemIsArchivedShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/isArchived");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasKey("data", withValue(true)));
	}
	
	@Test
	public void showItemIsWithdrawnShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/isWithdrawn");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasKey("data", withValue(false)));
	}
	
	@Test
	public void showItemOwningCollectionShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/owningCollection");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, owningCollection);
	}
	
	@Test
	public void showItemLastModifiedShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/lastModified");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasKey("data", withValue(1320071454211L)));
	}
	
	@Test
	public void showItemCollectionsShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/collections");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("data", collectionListMatchers));
	}
	
	@Test
	public void showItemCommunitiesShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/communities");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("data", communityListMatchers));
	}
	
	@Test
	public void showItemNameShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/name");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasKey("data", withValue("First Upload")));
	}
	
	@Test
	public void showItemBitstreamsShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/bitstreams");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("data", bitstreamMatchers));
	}
	
	@Test
	public void showItemHandleShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/handle");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasKey("data", withValue("123456789/7")));
	}
	
	@Test
	public void showItemCaneditShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/canedit");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasKey("data", withValue(false)));
	}
	
	@Test
	public void showItemIdShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/id");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasKey("data", withValue(new Long(1))));
	}
	
	@Test
	public void showItemTypeShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/type");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasKey("data", withValue(new Long(2))));
	}
	
	@Test
	public void showItemBundlesShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/items/1/bundles");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("data", bundleMatchers));
	}
}
