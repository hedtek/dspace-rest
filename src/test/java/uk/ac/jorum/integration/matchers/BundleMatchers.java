/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.allOf;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasName;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasType;

import java.util.ArrayList;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class BundleMatchers {


	  @Factory
	  public static Matcher<JSONObject> isBundleWithId(int id) {
	    return hasId(id);
	  }
	  
	  @Factory
	  public static Matcher<JSONObject> isBundle(int id, String name, ArrayList<Matcher<JSONObject>> itemIdMatchers, ArrayList<Matcher<JSONObject>> bitstreamIdMatchers) {
	    return allOf(
	    		hasId(id),
	    		hasType(1),
	    		hasName(name),
	    		hasArray("items", itemIdMatchers),
	    		hasArray("bitstreams", bitstreamIdMatchers)
	    		);
	  }
}
