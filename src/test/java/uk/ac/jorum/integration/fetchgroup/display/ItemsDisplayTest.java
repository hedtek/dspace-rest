/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package uk.ac.jorum.integration.fetchgroup.display;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONValue;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.jorum.integration.RestApiBaseTest;

public class ItemsDisplayTest extends RestApiBaseTest {


	@BeforeClass
    public static void createFixture() throws Exception {
      loadFixture("lightCommunityDatabase");
      startJetty();
    }

    /**
     * For retrofitting new integration tests aimed at preventing regression.
     * @throws Exception
     */
	//@Test
    public void smoke() throws Exception {
        final int itemNumber = 10;
        String result = makeRequest("/items/" + itemNumber + "?fetch=display");
        assertTrue(result, false);
    }

    @Test
    public void smoke10() throws Exception {
        checkItem(10);
    }

    @Test
    public void smoke9() throws Exception {
        checkItem(9);
    }
    
    @Test
    public void smoke8() throws Exception {
        checkItem(8);
    }
    
    @Test
    public void smoke7() throws Exception {
        checkItem(7);
    }

    @Test
    public void smoke6() throws Exception {
        checkItem(6);
    }

    @Test
    public void smoke5() throws Exception {
        checkItem(5);
    }

    @Test
    public void smoke4() throws Exception {
        checkItem(4);
    }

    @Test
    public void smoke3() throws Exception {
        checkItem(3);
    }
    
    @Test
    public void smoke2() throws Exception {
        checkItem(2);
    }
    
	@Test
    public void smoke1() throws Exception {
	    checkItem(1);
    }

    private void checkItem(final int itemNumber) throws Exception,
            FileNotFoundException {
        String result = makeRequest("/items/" + itemNumber + "?fetch=display");
	    assertEquals(result, expectedJSon("item" + itemNumber + "display"), JSONValue.parse(result));
    }

    private Object expectedJSon(final String expected)
            throws FileNotFoundException {
        final String filename = "src/test/java/uk/ac/jorum/integration/fetchgroup/display/" + expected + ".json";
        final Object json = JSONValue.parse(new FileReader(new File(filename)));
        if (json == null) {
            throw new RuntimeException ("Cannot parse json in: " + filename);
        }
        return json;
    }
}
