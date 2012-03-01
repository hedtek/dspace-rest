/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package uk.ac.jorum.integration.fetchgroup.light;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;
import uk.ac.jorum.integration.fetchgroup.AbstractFetchGroupIntegrationTest;

public class CommunityLightTest extends AbstractFetchGroupIntegrationTest {

	@BeforeClass
    public static void createFixture() throws Exception {
      createFixture("lightCommunityDatabase");
    }

	@Test
    public void smoke1() throws Exception {
      String result = makeRequest("/communities/1?fetch=light");
      assertTrue(result, true);
    }
	
    @Test
    public void smoke2() throws Exception {
      String result = makeRequest("/communities/2?fetch=light");
      assertTrue(result, true);
    }
    
    @Test
    public void smoke3() throws Exception {
      String result = makeRequest("/communities/3?fetch=light");
      assertTrue(result, true);
    }
}
