/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package uk.ac.jorum.integration.searching;

import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.harvestResultListWithAllItems;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.emptyHarvestResultList;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.harvestResultListWithItemsAfterTenth;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.harvestResultListWithItemsBeforeTenth;
import static uk.ac.jorum.integration.matchers.fixtures.AllSearchMatchers.harvestResultListWithItemsBetweenNinthTenth;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.HTTPStatusCode;
import uk.ac.jorum.integration.RestApiBaseTest;

public class HarvestDatabaseTest extends RestApiBaseTest {
	@BeforeClass
	public static void createFixture() throws Exception {
		loadFixture("harvestDatabase");
		startJetty();
	}

	@Test
	public void harvestWithoutQueryStringShouldReturnSuccessStatusCode()
			throws Exception {
		int result = getResponseCode("/harvest");
		assertThat(result, hasHTTPCode(HTTPStatusCode.SUCCESS));
	}
	
	@Test
	public void harvestShouldReturnCorrectStructureWithAllItems() throws Exception {
		String result = makeRequest("/harvest");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("harvest_collection", harvestResultListWithAllItems));
	}

	@Test
	public void harvestShouldReturnCorrectStructureWithItemsAfterStartDate() throws Exception {
		String result = makeRequest("/harvest", "startdate=2011-11-10");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("harvest_collection", harvestResultListWithItemsAfterTenth));
	}
	
	@Test
	public void harvestWithValidDateFormatShouldReturnSuccessStatusCode()
			throws Exception {
		int result = getResponseCode("/harvest", "startdate=2011-11-10");
		assertThat(result, hasHTTPCode(HTTPStatusCode.SUCCESS));
	}
	
	@Test
	public void harvestShouldNotReturnItemsBeforeStartDate() throws Exception {
		String result = makeRequest("/harvest", "startdate=2011-11-11");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("harvest_collection", emptyHarvestResultList));
	}
	
	@Test
	public void harvestWithInvalidStartDateFormatShouldReturnBadRequestStatusCode()
			throws Exception {
		int result = getResponseCode("/harvest", "startdate=13-2011-13");
		assertThat("Expected behaviour should be bad request", result,
				hasHTTPCode(HTTPStatusCode.SUCCESS));
	}

	@Test
	public void harvestWithStartDateWithoutHyphensFormatShouldReturnBadRequestStatusCode() throws Exception {
		int result = getResponseCode("/harvest", "startdate=20111110");
		assertThat(result, hasHTTPCode(HTTPStatusCode.BAD_REQUEST));
	}
	
	@Test
	public void harvestWithStartDateWithCharactersFormatShouldReturnBadRequestStatusCode() throws Exception {
		int result = getResponseCode("/harvest", "startdate=10nov2011");
		assertThat(result, hasHTTPCode(HTTPStatusCode.BAD_REQUEST));
	}
	
	@Test
	public void harvestWithValidTimestampStartDateFormatShouldReturnSuccessStatusCode()
			throws Exception {
		int result = getResponseCode("/harvest", "startdate=2011-11-10T07:39:40Z");
		assertThat(result, hasHTTPCode(HTTPStatusCode.SUCCESS));
	}
	
	@Test
	public void harvestWithInvalidEndDateFormatShouldReturnBadRequestStatusCode()
			throws Exception {
		int result = getResponseCode("/harvest", "enddate=13-2011-13");
		assertThat("Expected behaviour should be bad request", result,
				hasHTTPCode(HTTPStatusCode.SUCCESS));
	}

	@Test
	public void harvestWithEndDateWithoutHyphensFormatShouldReturnBadRequestStatusCode() throws Exception {
		int result = getResponseCode("/harvest", "enddate=20111110");
		assertThat(result, hasHTTPCode(HTTPStatusCode.BAD_REQUEST));
	}
	
	@Test
	public void harvestWithEndDateWithCharactersFormatShouldReturnBadRequestStatusCode() throws Exception {
		int result = getResponseCode("/harvest", "enddate=10nov2011");
		assertThat(result, hasHTTPCode(HTTPStatusCode.BAD_REQUEST));
	}
	
	@Test
	public void harvestWithValidTimestampEndDateFormatShouldReturnSuccessStatusCode()
			throws Exception {
		int result = getResponseCode("/harvest", "enddate=2011-11-10T07:39:40Z");
		assertThat(result, hasHTTPCode(HTTPStatusCode.SUCCESS));
	}

	@Test
	public void harvestShouldReturnCorrectStructureWithItemsBeforeEndDate() throws Exception {
		String result = makeRequest("/harvest", "enddate=2011-11-10");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("harvest_collection", harvestResultListWithItemsBeforeTenth));
	}
	
	@Test
	public void harvestShouldReturnCorrectStructureWithItemsBetweenStartDateAndEndDate() throws Exception {
		String result = makeRequest("/harvest", "startdate=2011-11-09&enddate=2011-11-10");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("harvest_collection", harvestResultListWithItemsBetweenNinthTenth));
	}
	
	@Test
	public void harvestWithStartDateAfterEndDateShouldReturnReturnEmptyResult() throws Exception {
		String result = makeRequest("/harvest", "startdate=2011-11-11&enddate=2011-11-10");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, hasArray("harvest_collection", emptyHarvestResultList));
	}
}
