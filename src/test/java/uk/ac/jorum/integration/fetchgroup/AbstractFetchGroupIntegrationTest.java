package uk.ac.jorum.integration.fetchgroup;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONValue;

import uk.ac.jorum.integration.RestApiBaseTest;

public abstract class AbstractFetchGroupIntegrationTest extends RestApiBaseTest {

    public static void createFixture(final String fixtureName) throws Exception {
        loadFixture(fixtureName);
        startJetty();
    }

    private final String fixturePath;

    public AbstractFetchGroupIntegrationTest(String fixturePath) {
        this.fixturePath = fixturePath;
    }

    protected Object expectedJSon(final String expected) throws FileNotFoundException {
        final String filename = "src/test/java/uk/ac/jorum/integration/fetchgroup" + fixturePath + expected + ".json";
        final Object json = JSONValue.parse(new FileReader(new File(filename)));
        if (json == null) {
            throw new RuntimeException ("Cannot parse json in: " + filename);
        }
        return json;
    }

    protected void checkResponse(final String endPoint, final String expectedJsonContainedIn) throws Exception,
    FileNotFoundException {
        String result = makeRequest(endPoint);
        assertEquals(result, expectedJSon(expectedJsonContainedIn), JSONValue.parse(result));
    }

}