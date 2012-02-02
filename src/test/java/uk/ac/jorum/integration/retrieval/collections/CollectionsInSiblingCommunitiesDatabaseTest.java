/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.retrieval.collections;

import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.fixtures.CollectionsInSiblingCommunities.collectionListWithIdOnlyMatchers;
import static uk.ac.jorum.integration.matchers.fixtures.CollectionsInSiblingCommunities.communityListWithIdOnlyMatchers;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class CollectionsInSiblingCommunitiesDatabaseTest extends
		RestApiBaseTest {

	
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
