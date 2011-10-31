package uk.ac.jorum.integration.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import org.junit.internal.matchers.TypeSafeMatcher;

public class ContainsJSONKey <T> {
	@Factory
	public static Matcher<JSONObject> hasKey( String key ) {
	    return new ContainsJSONKeyValue<Object>(key);
	}

	@Factory
	public static <T> Matcher<JSONObject> hasKey( String key, T value) {
	    return new ContainsJSONKeyValue<T>(key, value);
	}
	
	@Factory
	public static <T> Matcher<JSONObject> hasKey( String key, T[] values) {
	    return new ContainsJSONKeyArray<T>(key, values);
	}
}

class ContainsJSONKeyValue <T> extends TypeSafeMatcher<JSONObject> {
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

	ContainsJSONKeyValue(String key) {
        this.key = key;
    }
	
	ContainsJSONKeyValue(String key, T value) {
        this.key = key;
        this.expectedValue = value;
    }
}

class ContainsJSONKeyArray <T> extends TypeSafeMatcher<JSONObject> {
	private String key;
	private T[] expectedValues;
	private boolean isKeyPresent;
	
	@Override
	public void describeTo(Description description) {
		if(!isKeyPresent) {
			description.appendText("Key not present: " + key);
		} else {
			description.appendText("Value of " + key + " to be " + expectedValues);
		}
	}

	@Override
	public boolean matchesSafely(JSONObject item) {
		isKeyPresent = item.containsKey(key);
		if(!isKeyPresent) {
			return false;
		}

		T actualValue = (T)item.get(key);
		
		if (actualValue == null) {
			for (T expected: expectedValues) {
				if(expected==null) {
					return true;
				}
			} 
		} else {
			for (T expected: expectedValues) {
				if(actualValue.equals(expected)) {
					return true;
				}
			} 
		}
		return false;
	}
	
	ContainsJSONKeyArray(String key, T[] values) {
        this.key = key;
        this.expectedValues = values;
    }
}

