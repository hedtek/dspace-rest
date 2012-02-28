/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package uk.ac.jorum.integration.retrieval.items;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class ItemsDisplayTest extends RestApiBaseTest {


	@BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("lightCommunityDatabase");
      startJetty();
    }

	@Test
    public void smoke1() throws Exception {
      String result = makeRequest("/items/1?fetch=display");
      assertTrue(result, true);
    }
}
