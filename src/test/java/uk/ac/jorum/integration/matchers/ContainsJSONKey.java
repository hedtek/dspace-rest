package uk.ac.jorum.integration.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import org.junit.internal.matchers.TypeSafeMatcher;

public class ContainsJSONKey <T> extends TypeSafeMatcher<JSONObject> {
	@Factory
	public static Matcher<JSONObject> hasKey( String key ) {
	    return new ContainsJSONKey<Object>(key);
	}

	@Factory
	public static <T> Matcher<JSONObject> hasKey( String key, T value) {
	    return new ContainsJSONKey<T>(key, value);
	}
	
	private String key;
	private T expectedValue;
	private boolean isKeyPresent;
	
	@Override
	public void describeTo(Description description) {
		if(!isKeyPresent) {
			description.appendText("Key not present: " + key);
		} else {
			description.appendText("Value of " + key + " to be " + expectedValue);
		}
	}

	@Override
	public boolean matchesSafely(JSONObject item) {
		isKeyPresent = item.containsKey(key);
		if(!isKeyPresent) {
			return false;
		}
		if (expectedValue == null) {
			return true;
		}

		T actualValue = (T)item.get(key);
		
		if (actualValue == null) {
			return (expectedValue == null);
		} 
		return actualValue.equals(expectedValue);
	}

	ContainsJSONKey(String key) {
        this.key = key;
    }
	
	ContainsJSONKey(String key, T value) {
        this.key = key;
        this.expectedValue = value;
    }
}
