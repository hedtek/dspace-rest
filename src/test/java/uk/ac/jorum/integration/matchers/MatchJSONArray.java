package uk.ac.jorum.integration.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import static org.hamcrest.CoreMatchers.anyOf;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.Arrays;
import org.junit.internal.matchers.TypeSafeMatcher;

public class MatchJSONArray <T> extends TypeSafeMatcher<JSONObject> {
  private Matcher<T>[] matchers;
  private String key;
	private boolean isKeyPresent;
  private JSONArray array;
  private Matcher<T> anyOfmatcher;
	
	@Override
	public void describeTo(Description description) {
    if (array.size() == 0) {
      description.appendText("Expected a non-empty array");
    } else if (matchers.length == 0) {
      description.appendText("Expected an empty array");
    } else {
      description.appendText("Array match failed with ");
      anyOfmatcher.describeTo(description);
    }
	}

	@Override
	public boolean matchesSafely(JSONObject item) {
		isKeyPresent = item.containsKey(key);
		if(!isKeyPresent) {
			return false;
		}

    array = (JSONArray) item.get(key);

    if(array.size() == 0 && matchers.length == 0) {
      return true;
    } else if (array.size() == 0 && matchers.length > 0) {
      return false;
    } else if (array.size() > 0 && matchers.length == 0) {
      return false;
    }

    anyOfmatcher = anyOf((Iterable)Arrays.asList(matchers));
    for(Object obj : array) {
      if(!anyOfmatcher.matches((JSONObject)obj)) {
        return false;
      }
    }
    return true;
	}

	MatchJSONArray(String key, Matcher<T>[] matchers) {
    this.key = key;
    this.matchers = matchers;
  }
}
