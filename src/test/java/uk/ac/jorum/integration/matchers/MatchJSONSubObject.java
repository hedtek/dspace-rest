/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import org.junit.internal.matchers.TypeSafeMatcher;

public class MatchJSONSubObject<T> extends TypeSafeMatcher<JSONObject> {
	private Matcher<T> matcher;
	private String key;
	private boolean isKeyPresent;
	private boolean run = false;
	private boolean success;

	@Override
	public void describeTo(Description description) {
		if (!run || success)
			return;
		description.appendText("Expected " + key + " to match submatcher: ");
		matcher.describeTo(description);
	}

	@Override
	public boolean matchesSafely(JSONObject item) {
		run = true;
		success = runMatch(item);
		return success;
	}

	private boolean runMatch(JSONObject item) {
		isKeyPresent = item.containsKey(key);
		if (!isKeyPresent) {
			return false;
		}
		return matcher.matches((T) item.get(key));
	}

	MatchJSONSubObject(String key, Matcher<T> matcher) {
		this.key = key;
		this.matcher = matcher;
	}
}
