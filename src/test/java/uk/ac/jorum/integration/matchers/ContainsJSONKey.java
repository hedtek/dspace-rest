package uk.ac.jorum.integration.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import org.junit.internal.matchers.TypeSafeMatcher;

public class ContainsJSONKey <T> extends TypeSafeMatcher<JSONObject> {

	private String key;
	private T value;
	
	@Override
	public void describeTo(Description description) {
		description.appendText("Key not present");
	}

	@Override
	public boolean matchesSafely(JSONObject item) {
		boolean isKeyPresent = item.containsKey(key);
		if (value == null) {
			return isKeyPresent;
		} else {
			return item.get(key).equals(value);
		}
	}

	private ContainsJSONKey(String key) {
        this.key = key;
    }
	
	private ContainsJSONKey(String key, T value) {
        this.key = key;
        this.value = value;
    }
	
	@Factory
	public static Matcher<JSONObject> containsJSONKey( String key ) {
	    return new ContainsJSONKey<Object>(key);
	}
	
	@Factory
	public static <T> Matcher<JSONObject> containsJSONKey( String key, T value) {
	    return new ContainsJSONKey<T>(key, value);
	}
	
	public static <T> T withValue(T value) {
		return value;
	}
}
