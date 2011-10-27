/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package uk.ac.jorum.integration.retrieval.communities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

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
    public void communityListShouldBeEmpty() throws Exception {
      String result = makeRequest("/communities");
      assertThat(result, containsString("\"communities_collection\": [\n\n]}"));
    }

	@Test
  	public void emptyCommunitiesListShouldReturnNoContentStatusCode() throws Exception{
	  int result = getResponseCode("/communities", "");
	  assertThat("200 is observed behaviour, should really be 204", result, is(equalTo(200)));
  	}

}
