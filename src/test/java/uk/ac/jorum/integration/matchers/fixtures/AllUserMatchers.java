package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.UserMatchers.isUser;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class AllUserMatchers {
	
	public static final Matcher<JSONObject> firstUser =  isUser(1, "dspace", "dspace", "dspace@example.com");

}
