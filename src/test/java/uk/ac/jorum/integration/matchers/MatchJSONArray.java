/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.anyOf;

import java.util.ArrayList;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.internal.matchers.TypeSafeMatcher;

public class MatchJSONArray<T> extends TypeSafeMatcher<JSONObject> {
	private ArrayList<Matcher<T>> matchers;
	private String key;
	private boolean isKeyPresent;
	private boolean run = false;
	private JSONArray array;
	private Matcher<T> anyOfmatcher;

	@Override
	public void describeTo(Description description) {
		if (!run) return;
		if(!isKeyPresent) {
			description.appendText("Key " + key + " was not present in the json object");
		} else if (array.size() == 0) {
			description.appendText("Expected a non-empty array for key " + key);
		} else if (matchers.size() == 0) {
			description.appendText("Expected an empty array for key " + key);
		} else {
			description.appendText("Array match failed with ");
			anyOfmatcher.describeTo(description);
		}
	}

	@Override
	public boolean matchesSafely(JSONObject item) {
		run = true;
		isKeyPresent = item.containsKey(key);
		if (!isKeyPresent) {
			return false;
		}

		array = (JSONArray) item.get(key);

		if (array.size() == 0 && matchers.size() == 0) {
			return true;
		} else if (array.size() == 0 && matchers.size() > 0) {
			return false;
		} else if (array.size() > 0 && matchers.size() == 0) {
			return false;
		}

		anyOfmatcher = anyOf((Iterable)matchers);
		for (Object obj : array) {
			if (!anyOfmatcher.matches((T) obj)) {
				return false;
			}
		}
		return true;
	}

	MatchJSONArray(String key, ArrayList<Matcher<T>> matchers) {
		this.key = key;
		this.matchers = matchers;
	}
}
