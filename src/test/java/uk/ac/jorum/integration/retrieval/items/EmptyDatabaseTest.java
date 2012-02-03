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
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;

import org.dspace.rest.diagnose.HTTPStatusCode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class EmptyDatabaseTest extends RestApiBaseTest {

	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("emptyDatabase");
		startJetty();
	}

	@Test
	public void itemListShouldBeEmpty() throws Exception {
		String result = makeRequest("/items");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray itemsList = (JSONArray) resultJSON.get("items_collection");
		assertThat(itemsList.size(), is(equalTo(0)));
	}

	@Test
	public void emptyItemsListShouldReturnNoContentStatusCode()
			throws Exception {
		int result = getResponseCode("/items");
		assertThat("200 is observed behaviour, should really be 204", result,
				hasHTTPCode(HTTPStatusCode.SUCCESS));
	}
	
	@Test
	public void requestingNonExistantItemShouldReturnNotFound()
			throws Exception {
		int result = getResponseCode("/items/1");
		assertThat(result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));
	}

}
