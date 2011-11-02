package uk.ac.jorum.integration.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import org.junit.internal.matchers.TypeSafeMatcher;

public class MatchJSONSubObject <T> extends TypeSafeMatcher<JSONObject> {
  private Matcher<T> matcher;
  private String key;
	private boolean isKeyPresent;
	
	@Override
	public void describeTo(Description description) {
    matcher.describeTo(description);
	}

	@Override
	public boolean matchesSafely(JSONObject item) {
		isKeyPresent = item.containsKey(key);
		if(!isKeyPresent) {
			return false;
		}
    return matcher.matches((T) item.get(key));
	}

	MatchJSONSubObject(String key, Matcher<T> matcher) {
    this.key = key;
    this.matcher = matcher;
  }
}
