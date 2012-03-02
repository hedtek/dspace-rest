/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package uk.ac.jorum.integration.fetchgroup.light.collection;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.fetchgroup.AbstractFetchGroupIntegrationTest;

/**
 * Note that these regression tests are not robust against database changes,
 * since the results are returned unordered. When upgrading to a new database
 * version, manually check then capture to prevent regression.
 */
public class ItemsInCollectionLightTest extends AbstractFetchGroupIntegrationTest {


    public ItemsInCollectionLightTest() {
        super("/light/collection/");
    }

    @BeforeClass
    public static void createFixture() throws Exception {
      createFixture("itemsInCollectionLightDatabase");
    }
    
    @Test
    public void smokePageOne() throws Exception {
        checkPage(1);
    }
    
    @Test
    public void smokePageTwo() throws Exception {
        checkPage(2);
    }
    
	@Test
    public void smoke1() throws Exception {
	    check(1);
    }
	

    @Test
    public void smoke2() throws Exception {
        check(2);
    }


    @Test
    public void smoke3() throws Exception {
        check(3);
    }

    private void checkPage(final int page) throws Exception {
        checkResponse("/collections/1/items?fetch=light&_page=" + page, "itemsInCollection1Page" + page);
    }
    
    private void check(final int collection) throws Exception {
        checkResponse("/collections/" + collection + "/items?fetch=light", "itemsInCollection" + collection + "Page1");
    }
}
