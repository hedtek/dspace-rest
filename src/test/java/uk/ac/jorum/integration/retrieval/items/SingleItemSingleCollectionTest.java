/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package uk.ac.jorum.integration.retrieval.items;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;
import static uk.ac.jorum.integration.matchers.EntityMatchers.*;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.HTTPStatusCode;
import uk.ac.jorum.integration.RestApiBaseTest;


public class SingleItemSingleCollectionTest extends RestApiBaseTest {

	@BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("singleItemSingleCollectionTopLevelCommunity");
      startJetty();
    }
	
	@Test
	public void itemsListShouldReturnSuccessStatusCode() throws Exception {
		int result = getResponseCode("/items", "");
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
		JSONArray itemsList = (JSONArray) resultJSON
				.get("items_collection");
		structureAssertionsOn((JSONObject)itemsList.get(0));		
	}

	@Test
	public void itemListWithIdOnlyShouldReturnOnlyIds() throws Exception {
		String result = makeRequest("/items", "idOnly=true");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		JSONArray itemsList = (JSONArray) resultJSON.get("items_collection");
		idOnlyStructureAssertionsOn((JSONObject) itemsList.get(0));
	}
	
	private void structureAssertionsOn(JSONObject item) throws Exception{
		assertThat(item, is(allOf(
				cannotBeEdited(),
				hasHandle("123456789/7"),
				hasType(2),
				hasId(1),
				hasName("First Upload"),
				hasEntityReference("/items/1"),
				hasEntityURL("http://localhost:8080/dspace-rest/items/1"),
				hasEntityId(),
        hasKeys(new String[]
          {
            "isArchived", "isWithdrawn", "lastModified",
            "bitstreams", "bundles", "communities",
            "owningCollection", "submitter", "metadata"
          }))));
	}
	
	private void idOnlyStructureAssertionsOn(JSONObject item) throws Exception{
		assertThat(item, is(allOf(
				hasId(1),
				hasNo("name"),
				hasNo("owningCollection"),
				hasNo("collections"),
				hasNo("communities"),
				hasEntityReference("/items/1"),
				hasEntityURL("http://localhost:8080/dspace-rest/items/1"),
				hasKey("entityId")
				)));
	}
}
