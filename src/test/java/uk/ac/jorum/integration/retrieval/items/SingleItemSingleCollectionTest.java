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
		//----assertThat(item, containsJSONKey("bitstreams"));
		//----assertThat(item, containsJSONKey("bundles"));
		assertThat(item, cannotBeEdited());
		//----assertThat(item, containsJSONKey("communities"));
		//----assertThat(item, containsJSONKey("collections"));
		assertThat(item, hasHandle("123456789/7"));
		assertThat(item, hasType(2));
		
		assertThat(item, hasId(1));
		assertThat(item, hasKey("isArchived"));
		assertThat(item, hasKey("isWithdrawn"));
		assertThat(item, hasKey("lastModified"));
		//-----assertThat(item, containsJSONKey("metadata"));
		assertThat(item, hasName("First Upload"));
		//----assertThat(item, containsJSONKey("owning_collection"));
		//----assertThat(item, containsJSONKey("submitter"));
		assertThat(item, hasEntityReference("/items/1"));
		assertThat(item, hasEntityURL("http://localhost:8080/dspace-rest/items/1"));
		assertThat(item, hasKey("entityId"));
	}
	
	private void idOnlyStructureAssertionsOn(JSONObject item) throws Exception{
		assertThat(item, hasId(1));
		assertThat(item, hasNo("name"));
		assertThat(item, hasNo("owning_collection"));
		assertThat(item, hasNo("collections"));
		assertThat(item, hasNo("communities"));
		assertThat(item, hasEntityReference("/items/1"));
		assertThat(item, hasEntityURL("http://localhost:8080/dspace-rest/items/1"));
		assertThat(item, hasKey("entityId"));
	}
}
