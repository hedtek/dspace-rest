package uk.ac.jorum.integration.retrieval.collections;

import static org.junit.Assert.assertThat;
import static uk.ac.jorum.integration.matchers.HasHTTPCode.hasHTTPCode;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.HTTPStatusCode;
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
		int result = getResponseCode("/collections", "");
		assertThat(result,hasHTTPCode(HTTPStatusCode.SUCCESS));
	}
	
	public void subCommunityCollectionShouldHaveMoreThanOneCommunityOwners() throws Exception {
		//TODO subCommunityCollectionShouldHaveMoreThanOneCommunityOwners
	}
	
	public void topLevelCommunityCollectionsShouldNotListSubCommunityCollections() throws Exception {
		//TODO subCommunityCollectionShouldHaveMoreThanOneCommunityOwners
	}
	
	
}
