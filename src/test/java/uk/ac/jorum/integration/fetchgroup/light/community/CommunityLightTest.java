/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package uk.ac.jorum.integration.fetchgroup.light.community;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.fetchgroup.AbstractFetchGroupIntegrationTest;

public class CommunityLightTest extends AbstractFetchGroupIntegrationTest {

	public CommunityLightTest() {
        super("/light/community/");
    }

    @BeforeClass
    public static void createFixture() throws Exception {
      createFixture("lightCommunityDatabase");
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

    private void check(final int community) throws Exception,
            FileNotFoundException {
        checkResponse("/communities/" + community + "?fetch=light", "community" + community + "light");
    }
}
