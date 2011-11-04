package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollection;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionWithId;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunity;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class MultipleCollectionsUnderTopLevelCommunityDatabaseTest extends RestApiBaseTest {



	private final ArrayList<Matcher<JSONObject>> collectionListWithIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isCollectionWithId(1));
			add(isCollectionWithId(2));
		}
	};
	
	private final Matcher<JSONObject> parentCommunity = isCommunity(2,
		"Community no 1", "123456789/2",
		"Introductory text for community no 1",
		"Short description of community no 1",
		"Side bar text for community 1", "Copyright information", 0,
		null, new ArrayList<Matcher<JSONObject>>() {{ add(isCommunityWithId(4)); }}, 
		emptyMatcherList(), collectionListWithIdOnlyMatchers);
	
	
	private final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(parentCommunity);
		}
	};

	private final Matcher<JSONObject> collectionOne = isCollection(1, "Collection 1", "123456789/6",
			"Introductory Text for collection 1",
			"Short Description for Collection 1",
			"Side bar text for collection 1",
			"Copyright information for collection 1",
			"Licence for collection 1",
			"Provenance for collection 1", 0, communityListMatchers, emptyMatcherList());

	private final Matcher<JSONObject> collectionTwo = isCollection(2, "Collection 2", "123456789/7",
			"Introductory Text for collection 2",
			"Short Description for Collection 2",
			"Side bar text for collection 2",
			"Copyright text for collection 2",
			"Licence text for collection 2",
			"Provenance text for collection 2", 0, communityListMatchers, emptyMatcherList());
	
	
	private final ArrayList<Matcher<JSONObject>> collectionListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(collectionOne);
			add(collectionTwo);
		}
	};
	
	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("twoCollectionsUnderTopLevelCommunityDatabase");
		startJetty();
	}
	
	@Test
	public void collectionSizeShouldBeGreaterThanOne() throws Exception {
		String result = makeRequest("/collections");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray collectionsList = (JSONArray) resultJSON.get("collections_collection");
		assertThat(collectionsList.size(), is(greaterThan(1)));
	}
	
	@Test
	public void collectionListShouldContainTheCorrectCollections() throws Exception {
		String result = makeRequest("/collections");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("collections_collection", collectionListMatchers));
	}
	
}