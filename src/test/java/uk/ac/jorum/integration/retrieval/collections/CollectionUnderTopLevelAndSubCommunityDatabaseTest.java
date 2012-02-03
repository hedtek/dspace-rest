/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.retrieval.collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;
import static uk.ac.jorum.integration.matchers.fixtures.CollectionUnderTopLevelAndSubCommunity.collectionListWithIdOnlyMatchers;
import static uk.ac.jorum.integration.matchers.fixtures.CollectionUnderTopLevelAndSubCommunity.communityListWithIdOnlyMatchers;

import org.dspace.rest.diagnose.HTTPStatusCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class CollectionUnderTopLevelAndSubCommunityDatabaseTest extends
		RestApiBaseTest {	
	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("collectionUnderTopLevelAndSubCommunityDatabase");
		startJetty();
	}
	
	@Test
	public void collectionListShouldReturnSuccessStatusCode() throws Exception {
		int result = getResponseCode("/collections");
		assertThat(result,hasHTTPCode(HTTPStatusCode.SUCCESS));
	}
	
	@Test
	public void subCommunityCollectionShouldHaveMoreThanOneCommunityOwners() throws Exception {
		String result = makeRequest("/collections/3");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		JSONArray communitiesList = (JSONArray)collection.get("communities");

		assertThat(communitiesList.size(), is(greaterThan(1)));
	}
	
	@Test
	public void subCommunityCollectionListsAncestorCommunitiesUnderItsCommunities() throws Exception {
		String result = makeRequest("/collections/3");
		JSONObject collection = (JSONObject) JSONValue.parse(result);
		assertThat(collection, hasArray("communities", communityListWithIdOnlyMatchers));
	}

	
	@Test
	public void topLevelCommunityCollectionsShouldNotListSubCommunityCollections() throws Exception {
		String result = makeRequest("/communities/2");
		JSONObject community = (JSONObject) JSONValue.parse(result);
		assertThat(community, hasArray("collections", collectionListWithIdOnlyMatchers));
	}
	
}
