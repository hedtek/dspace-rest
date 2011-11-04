/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package uk.ac.jorum.integration;

import java.io.File;
import java.io.FileReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dspace.core.ConfigurationManager;
import org.dspace.storage.rdbms.DatabaseManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public abstract class RestApiBaseTest {
  private static String apiHost = "localhost";
  private static String apiProtocol = "http";
  private static Server server;
  private HttpClient client;

  @AfterClass
    public static void destroyFixture() throws Exception {
      server.stop();
    }

  @Before
    public void ApiSetup() {
      client = new DefaultHttpClient();
    }

  protected String makeRequest(String endpoint) throws Exception {
    return makeRequest(endpoint, null);
  }

  protected String makeRequest(String endpoint, String queryString) throws Exception {
    URI uri = URIUtils.createURI(apiProtocol, apiHost, jettyPort(), jettyPath() + endpoint, queryString, null);
    HttpGet httpget = new HttpGet(uri);
    httpget.addHeader("Accept", "application/json");
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    return client.execute(httpget, responseHandler);
  }

  protected int getResponseCode(String endpoint, String queryString) throws Exception{
    URI uri = URIUtils.createURI(apiProtocol, apiHost, jettyPort(), jettyPath() + endpoint, queryString, null);
    HttpGet httpget = new HttpGet(uri);
    httpget.addHeader("Accept", "application/json");
    HttpResponse response = client.execute(httpget);
    return response.getStatusLine().getStatusCode();
  }

  protected static int jettyPort() {
    return Integer.parseInt(System.getenv("jetty.port"));
  }

  protected static String jettyPath() {
    return System.getenv("jetty.path");
  }

  protected static String resourceBase() {
    return System.getenv("resource.base");
  }

  protected static String webXmlLocation() {
    return System.getenv("webxml.location");
  }

  protected int getResponseCode(String endpoint) throws Exception{
    return getResponseCode(endpoint, null);
  }

  private static void loadDatabase(String filename) throws Exception {
    ConfigurationManager.loadConfig(resourceBase() + "/config/dspace.cfg");
    DatabaseManager.loadSql(new FileReader(new File(filename).getCanonicalPath()));
  }

  protected static void loadFixture(String fixtureName) throws Exception {
    loadDatabase(resourceBase() + "/setup/cleardb.sql");
    loadDatabase(resourceBase() + "/fixtures/" + fixtureName + ".sql");
  }

  protected static void startJetty() throws Exception {
    server = new Server();
    Connector connector = new SelectChannelConnector();
    connector.setPort(jettyPort());
    connector.setHost(apiHost);
    server.addConnector(connector);

    WebAppContext wac = new WebAppContext();
    wac.setContextPath(jettyPath());
    wac.setResourceBase(resourceBase());
    wac.setDescriptor(webXmlLocation());
    wac.setParentLoaderPriority(true);
    server.setHandler(wac);
    server.setStopAtShutdown(true);

    server.start();
    while (!wac.isStarted()) {}
  }
}
