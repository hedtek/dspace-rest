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
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasType;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class UserMatchers {
	@Factory
	public static Matcher<JSONObject> isUser(int id, String firstName, String lastName, String email){

		return allOf(
				hasId(id),
				hasKey("firstName", firstName),
				hasKey("lastName", lastName),
				hasKey("fullName", firstName + " " + lastName),
				hasType(7)
				);
	}

}
