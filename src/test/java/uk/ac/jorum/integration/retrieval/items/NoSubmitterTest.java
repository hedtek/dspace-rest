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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.EntityMatchers.isEntityElement;
import static uk.ac.jorum.integration.matchers.EntityMatchers.isEntityElementWithArray;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;
import static uk.ac.jorum.integration.matchers.ItemMatchers.getMetadataMatchers;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItem;
import static uk.ac.jorum.integration.matchers.fixtures.SingleItemSingleCollectionTopLevelCommunity.bitstreamMatchers;
import static uk.ac.jorum.integration.matchers.fixtures.SingleItemSingleCollectionTopLevelCommunity.bundleMatchers;
import static uk.ac.jorum.integration.matchers.fixtures.SingleItemSingleCollectionTopLevelCommunity.collectionListIdOnlyMatchers;
import static uk.ac.jorum.integration.matchers.fixtures.SingleItemSingleCollectionTopLevelCommunity.collectionListMatchers;
import static uk.ac.jorum.integration.matchers.fixtures.SingleItemSingleCollectionTopLevelCommunity.communityListIdOnlyMatchers;
import static uk.ac.jorum.integration.matchers.fixtures.SingleItemSingleCollectionTopLevelCommunity.communityListMatchers;
import static uk.ac.jorum.integration.matchers.fixtures.SingleItemSingleCollectionTopLevelCommunity.itemListWithIdMatchers;
import static uk.ac.jorum.integration.matchers.fixtures.SingleItemSingleCollectionTopLevelCommunity.owningCollection;
import static uk.ac.jorum.integration.matchers.fixtures.SingleItemSingleCollectionTopLevelCommunity.owningCollectionIdOnly;

import java.util.ArrayList;

import org.dspace.rest.diagnose.HTTPStatusCode;
import org.hamcrest.Matcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;
import uk.ac.jorum.integration.matchers.fixtures.AllBitstreamMatchers;
import uk.ac.jorum.integration.matchers.fixtures.AllBundleMatchers;

/**
 * Harvested data often is missing a submitter. 
 * The REST api needs to cope gracefully with this case.
 * The fixture sets up some items with NULL submitter.
 * These tests just check for major processing failures
 * when submitters are null.
 */
public class NoSubmitterTest extends RestApiBaseTest {
    static final Matcher<JSONObject> itemWithoutSubmitterMatcher = isItem(1, "First Upload", true, false, owningCollectionIdOnly,
            null, communityListIdOnlyMatchers, collectionListIdOnlyMatchers,
            AllBundleMatchers.firstBundleIdWithLicenceList(),
            AllBitstreamMatchers.firstBitstreamIdList());
    
	@BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("singleItemNoSubmitterDatabase");
      startJetty();
    }
	
	@Test
	public void itemsListShouldReturnSuccessStatusCode() throws Exception {
		int result = getResponseCode("/items");
		assertThat(result, hasHTTPCode(HTTPStatusCode.SUCCESS));
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
    public void itemsListShouldNotFailDuringProcessing() throws Exception {
        assertFalse("The response should not contain a failure notice.", makeRequest("/items").contains("Failure processing entity request"));
    }

    @Test
    public void itemsListItemShouldNotHaveSubmitterInStructure() throws Exception {
        

        final ArrayList<Matcher<JSONObject>> itemMatchers = new ArrayList<Matcher<JSONObject>>() {
            {
                add(itemWithoutSubmitterMatcher);
            }
        };
        
        String result = makeRequest("/items");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, hasArray("items_collection", itemMatchers));
    }

    @Test
    public void itemListWithIdOnlyShouldReturnOnlyIds() throws Exception {
        String result = makeRequest("/items", "idOnly=true");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, hasArray("items_collection", itemListWithIdMatchers));
    }
    
    @Test
    public void showItemShouldHaveCorrectStructure() throws Exception{
        String result = makeRequest("/items/1");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, is(itemWithoutSubmitterMatcher));
    }
    
    @Test
    public void showItemMetadataShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/metadata");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElementWithArray(1, "items", getMetadataMatchers()));
    }
    
    @Test
    public void showItemSubmitterShouldHaveCorrectStructure() throws Exception {
        int result = getResponseCode("/items/1/submitter");
        assertThat(result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));
    }
    
    @Test
    public void showItemIsArchivedShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/isArchived");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElement(1, "items", true));
    }
    
    @Test
    public void showItemIsWithdrawnShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/isWithdrawn");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElement(1, "items", false));
    }
    
    @Test
    public void showItemOwningCollectionShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/owningCollection");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, is(owningCollection));
    }
    
    @Test
    public void showItemLastModifiedShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/lastModified");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElement(1, "items", 1320071454211L));
    }
    
    @Test
    public void showItemCollectionsShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/collections");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElementWithArray(1, "items", collectionListMatchers));
    }
    
    @Test
    public void showItemCommunitiesShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/communities");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElementWithArray(1, "items", communityListMatchers));
    }
    
    @Test
    public void showItemNameShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/name");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElement(1, "items", "First Upload"));
    }
    
    @Test
    public void showItemBitstreamsShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/bitstreams");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElementWithArray(1, "items", bitstreamMatchers));
    }
    
    @Test
    public void showItemHandleShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/handle");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElement(1, "items", "123456789/7"));
    }
    
    @Test
    public void showItemCaneditShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/canedit");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElement(1, "items", false));
    }
    
    @Test
    public void showItemIdShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/id");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElement(1, "items", 1));
    }
    
    @Test
    public void showItemTypeShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/type");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElement(1, "items", 2));
    }
    
    @Test
    public void showItemBundlesShouldHaveCorrectStructure() throws Exception {
        String result = makeRequest("/items/1/bundles");
        JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
        assertThat(resultJSON, isEntityElementWithArray(1, "items", bundleMatchers));
    }
}
