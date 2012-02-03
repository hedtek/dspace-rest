/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.retrieval.bitstreams;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.EntityMatchers.isEntityElement;
import static uk.ac.jorum.integration.matchers.EntityMatchers.isEntityElementWithArray;
import static uk.ac.jorum.integration.matchers.HasFileContent.hasFileContent;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;
import static uk.ac.jorum.integration.matchers.fixtures.AllBitstreamMatchers.firstBitstream;
import static uk.ac.jorum.integration.matchers.fixtures.AllBundleMatchers.firstBundleList;

import org.dspace.rest.diagnose.HTTPStatusCode;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class SingleBitstreamInItemDatabaseTest extends RestApiBaseTest {
	@BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("singleItemSingleCollectionTopLevelCommunity");
      startJetty();
    }

	@Test
	public void requestingNonExistantBitstreamShouldReturnNotFound() throws Exception {
		int result = getResponseCode("/bitstream/404");
		assertThat(result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));
	}

	@Test
	public void requestingExistingBitstreamShouldReturnSuccess() throws Exception {
		int result = getResponseCode("/bitstream/1");
		assertThat(result, hasHTTPCode(HTTPStatusCode.SUCCESS));
	}
	
	@Test
	public void bitstreamShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/bitstream/1");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, is(firstBitstream(firstBundleList())));
	}
	
	@Test
	public void showBitstreamBundlesShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/bitstream/1/bundles");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElementWithArray(1, "bitstream", firstBundleList()));
	}
	
	@Test
	public void showBitstreamMimeTypeShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/bitstream/1/mimeType");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(1, "bitstream", "text/plain"));
	}
	
	@Test
	public void showBitstreamChecksumAlgoShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/bitstream/1/checkSumAlgorithm");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(1, "bitstream", "MD5"));
	}
	
	@Test
	public void showBitstreamDescriptionShouldHaveCorrectStructure() throws Exception {
		int result = getResponseCode("/bitstream/1/description");
		assertThat("Expected behaviour should be data with null rather than 404 Not Found", result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));
	}
	
	@Test
	public void showBitstreamFormatDescriptionShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/bitstream/1/formatDescription");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(1, "bitstream", "Text"));
	}
	
	@Test
	public void showBitstreamSequenceIdShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/bitstream/1/sequenceId");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(1, "bitstream", 1));
	}
	
	@Test
	public void showBitstreamSizeShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/bitstream/1/size");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(1, "bitstream", 13));
	}
	
	@Test
	public void showBitstreamSourceShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/bitstream/1/source");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(1, "bitstream", getSource("firstUpload.txt")));
	}
	
	@Test
	public void showBitstreamStoreNumberShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/bitstream/1/storeNumber");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(1, "bitstream", 0));
	}
	
	@Test
	public void showBitstreamUserFormatDescriptionShouldHaveCorrectStructure() throws Exception {
		int result = getResponseCode("/bitstream/1/userFormatDescription");
		assertThat("Expected behaviour should be data with null rather than 404 Not Found", result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));
	}
	
	@Test
	public void showBitstreamNameShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/bitstream/1/name");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(1, "bitstream","firstUpload.txt"));
	}
	
	@Test
	public void showBitstreamHandleShouldHaveCorrectStructure() throws Exception {
		int result = getResponseCode("/bitstream/1/handle");
		assertThat("Expected behaviour should retrieve handle", result, hasHTTPCode(HTTPStatusCode.NOT_FOUND));
	}
	
	@Test
	public void showBitstreamIdShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/bitstream/1/id");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(1, "bitstream", 1));
	}
	
	@Test
	public void showBitstreamTypeShouldHaveCorrectStructure() throws Exception {
		String result = makeRequest("/bitstream/1/type");
		JSONObject resultJSON = (JSONObject) JSONValue.parse(result);
		assertThat(resultJSON, isEntityElement(1, "bitstream", 0));
	}

	@Test
	public void requestingReceiveOnExistingBitstreamShouldReturnSuccess() throws Exception {
		int result = getResponseCode("/bitstream/1/receive");
		assertThat(result, hasHTTPCode(HTTPStatusCode.SUCCESS));
	}

	@Test
	public void requestingReceiveOnExistingBitstreamShouldHaveCorrectContent() throws Exception {
		String result = makeRequest("/bitstream/1/receive");
		assertThat(result, hasFileContent(getSource("firstUpload")));
	}
	
	private static String getUploadDir() {
		return System.getenv("resource.base") + "/upload/";
	}
	
	private static String getSource(String name) {
		return getUploadDir() + name;
	}
}
