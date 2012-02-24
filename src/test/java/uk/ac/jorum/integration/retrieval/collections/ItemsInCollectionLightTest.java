/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package uk.ac.jorum.integration.retrieval.collections;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class ItemsInCollectionLightTest extends RestApiBaseTest {


	@BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("itemsInCollectionLightDatabase");
      startJetty();
    }

	@Test
    public void smoke() throws Exception {
      String result = makeRequest("/collections/1/items?fetch=light");
      assertTrue(result, true);
    }
	

    @Test
    public void smoke2() throws Exception {
      String result = makeRequest("/collections/2/items?fetch=light");
      assertTrue(result, true);
    }


    @Test
    public void smoke3() throws Exception {
      String result = makeRequest("/collections/3/items?fetch=light");
      assertTrue(result, true);
    }


}
