/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.UserMatchers.isUser;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class AllUserMatchers {
	
	public static final Matcher<JSONObject> firstUser() {
		return isUser(1, "dspace", "dspace", "dspace@example.com");
	}
}
