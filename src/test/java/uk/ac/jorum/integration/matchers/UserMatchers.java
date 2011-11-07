package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.allOf;
import static uk.ac.jorum.integration.matchers.EntityMatchers.*;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;

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
				hasKey("email", email),
				hasKey("fullName", firstName + " " + lastName),
				hasType(7)
				);
	}

}
