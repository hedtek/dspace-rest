package uk.ac.jorum.integration.fetchgroup;

import uk.ac.jorum.integration.RestApiBaseTest;

public abstract class AbstractFetchGroupIntegrationTest extends RestApiBaseTest {

    public static void createFixture(final String fixtureName) throws Exception {
        loadFixture(fixtureName);
          startJetty();
    }

    public AbstractFetchGroupIntegrationTest() {
        super();
    }

}