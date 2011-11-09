package uk.ac.jorum.integration.retrieval.bitstreams;

import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.HTTPStatusCode;
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
	
	//@Test
	public void bitstreamShouldHaveCorrectStructure() throws Exception {
		
	}
}
