package uk.ac.jorum.integration.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import org.junit.internal.matchers.TypeSafeMatcher;

public class ContainsJSONKey <T> extends TypeSafeMatcher<JSONObject> {

	private String key;
	private T expectedValue;
	private boolean shouldCheckValue;
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
		if(shouldCheckValue) {
			if(!isKeyPresent) {
				return isKeyPresent;
			}

			if (item.get(key) == null) {
				return (expectedValue == null);
			} 
			
			return item.get(key).equals(expectedValue);
		} 
		
		return isKeyPresent;
	}

	private ContainsJSONKey(String key) {
        this.key = key;
        shouldCheckValue = false;
    }
	
	private ContainsJSONKey(String key, T value) {
        this.key = key;
        this.expectedValue = value;
        shouldCheckValue = true;
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
