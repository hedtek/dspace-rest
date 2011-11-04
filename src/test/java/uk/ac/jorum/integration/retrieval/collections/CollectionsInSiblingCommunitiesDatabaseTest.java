package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionWithId;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class CollectionsInSiblingCommunitiesDatabaseTest extends
		RestApiBaseTest {


	private final ArrayList<Matcher<JSONObject>> collectionListWithIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(not(isCollectionWithId(6)));
		}
	};
	
	private final ArrayList<Matcher<JSONObject>> communityListWithIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(not(isCommunityWithId(6)));
		}
	};
	
	
	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("collectionsInSiblingCommunitiesDatabase");
		startJetty();
	}
	
	@Test
	public void communityListForACollectionShouldNotListSiblingCommunitiesOfOwningCommunity() throws Exception {
		String result = makeRequest("/collections/3");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		assertThat(collection, hasArray("communities", communityListWithIdOnlyMatchers));
	}
	
	@Test
	public void collectionListForCommunityShouldNotListCollectionsOfSiblingCommunities() throws Exception {
		String result = makeRequest("/communities/4");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		assertThat(community, hasArray("collections", collectionListWithIdOnlyMatchers));
	}
	
}
