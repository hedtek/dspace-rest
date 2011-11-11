/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.retrieval.communities;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.fixtures.SubCommunity.allCommunities;
import static uk.ac.jorum.integration.matchers.fixtures.SubCommunity.parentCommunity;
import static uk.ac.jorum.integration.matchers.fixtures.SubCommunity.topLevelCommunities;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class SubCommunityDatabaseTest extends RestApiBaseTest {
	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("subCommunityDatabase");
		startJetty();
	}

	@Test
	public void subCommunityShouldNotBeShownInTopLevelList() throws Exception {
		String result = makeRequest("/communities");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON,
				hasArray("communities_collection", topLevelCommunities));
	}

	@Test
	public void subCommunityShouldBeShownInCompleteList() throws Exception {
		String result = makeRequest("/communities", "topLevelOnly=false");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);

		assertThat(resultJSON,
				hasArray("communities_collection", allCommunities));
	}

	@Test
	public void showCommunityAnchestorShouldHaveDataWithParentDetailsForSubcommunities()
			throws Exception {
		String result = makeRequest("/communities/4/anchestor");
		JSONObject anchestor = (JSONObject) JSONValue.parse(result);
		assertThat(anchestor, is(parentCommunity));
	}
}
