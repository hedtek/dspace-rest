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
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasKeys;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasSubObject;
import static uk.ac.jorum.integration.matchers.EntityMatchers.withValue;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class MetadataMatcher {
	@Factory
	public static Matcher<JSONObject> isMetadataItem(String element, String qualifier) {
	     return allOf(
	        hasKey("element", withValue(element)),
	        hasKey("qualifier", withValue(qualifier)),
	        hasKeys(keys)
	      );
	  }
     private static final String [] keys = {"schema", "value"};

	 @Factory
	 public static Matcher<JSONObject> isMetadataItem(String element, Matcher<JSONObject> qualifier) {
	     
	     return allOf(
	        hasKey("element", withValue(element)),
	        hasSubObject("qualifier", withValue(qualifier)),
	        hasKeys(keys)
	      );
	  }
}
