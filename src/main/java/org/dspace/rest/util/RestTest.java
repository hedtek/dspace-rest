/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestTest {

  public static void main(String [] args) throws Exception {
	try {
    String url = "http://localhost:8080/rest/communities.xml";
	String [] paramName = new String[] {"id","action","name","user","pass"};
	String [] paramVal = new String[] {"1","createCollection","demo","dhaivat4u@gmail.com","dhaivat123"};

    System.out.println(httpPost(url, paramName, paramVal));
	} catch(Exception e) {
		e.printStackTrace();
	}
  }

  public static String toXml(String [] paramName, String [] paramVal) {
    StringBuffer sb = new StringBuffer();
	sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
	sb.append("<request>");
	for(int i=0;i<paramName.length;i++) {
	  sb.append("<" + paramName[i]+">" + paramVal[i] + "</" + paramName[i] + ">");
	}
	sb.append("</request>");
        System.out.println(sb.toString());
	return sb.toString();

  }
  public static String httpPost(String urlStr, String[] paramName, String[] paramVal) throws Exception {
    URL url = new URL(urlStr);
    HttpURLConnection conn =
      (HttpURLConnection) url.openConnection();

    conn.setRequestMethod("POST");
    conn.setDoOutput(true);
    conn.setDoInput(true);
    conn.setUseCaches(false);
    conn.setAllowUserInteraction(false);
	String userPassword = "nirajaswani@gmail.com:niraj123";

    // Encode String
    String encoding = new sun.misc.BASE64Encoder().encode (userPassword.getBytes());
    //conn.setRequestProperty("Authorization", "Basic " + encoding);
    conn.setRequestProperty("Content-Type", "application/xml");

    // Create the form content
    OutputStream out = conn.getOutputStream();
    Writer writer = new OutputStreamWriter(out, "UTF-8");
	String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
"<request>" +
"<id type='number'>2</id>" +
"<name>newname</name>" +
"<action>createCollection</action>" +
"</request>";
	//writer.write(URLEncoder.encode(s,"UTF-8"));
        writer.write(s);
    /*for (int i = 0; i < paramName.length; i++) {
		System.out.println(paramName[i]+"=>" +URLEncoder.encode(paramVal[i], "UTF-8"));
      writer.write(paramName[i]);
      writer.write("=");
      writer.write(URLEncoder.encode(paramVal[i], "UTF-8"));
      writer.write("&");
    }*/
    writer.close();
    out.close();

    /*if (conn.getResponseCode() != 200) {
      throw new IOException(conn.getResponseMessage());
    }*/

    // Buffer the result into a string
    BufferedReader rd = new BufferedReader(
      new InputStreamReader(conn.getInputStream()));
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      sb.append(line);
    }
    rd.close();

    conn.disconnect();
    return sb.toString();
  }
}