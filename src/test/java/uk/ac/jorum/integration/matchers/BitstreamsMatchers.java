/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.allOf;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasName;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasType;
import static uk.ac.jorum.integration.matchers.EntityMatchers.withValue;

import java.util.ArrayList;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class BitstreamsMatchers {
	@Factory
	public static Matcher<JSONObject> isBitstream(int id, String name, String formatDescription, String mimeType, ArrayList<Matcher<JSONObject>> bundleIdMatchers){

		return allOf(
				hasId(id),
				hasType(0),
				hasName(name),
				hasKey("formatDescription", withValue(formatDescription)),
				hasKey("mimeType", withValue(mimeType)),
				hasKey("source", withValue(getUploadDir() + name)),
				hasKey("checkSum"),
				hasKey("checkSumAlgorithm", withValue("MD5")),
				hasArray("bundles", bundleIdMatchers)
				);
	}
	
	@Factory
	  public static Matcher<JSONObject> isBitstreamWithId(int id) {
	    return hasId(id);
	  }
	
	private static String getUploadDir() {
		return System.getenv("resource.base") + "/upload/";
	}
}
