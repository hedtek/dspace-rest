package uk.ac.jorum.integration.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import org.junit.internal.matchers.TypeSafeMatcher;

public class ContainsJSONKey <T> extends TypeSafeMatcher<JSONObject> {

	private enum Mode { ONLY_KEY, VALUE, VALUES }
	private String key;
	private T expectedValue;
	private T[] expectedValues;
	private Mode checkMode;
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
			return isKeyPresent;
		}
		
		T actualValue = (T)item.get(key);

		if(checkMode == Mode.VALUE) {
			return compareWithExpectedValue(actualValue);
		} else if (checkMode == Mode.VALUES) {
			return findInExpectedValues(actualValue);
		}
		
		return isKeyPresent;
	}
	
	private boolean compareWithExpectedValue(T actualValue) {
		if (actualValue == null) {
			return (expectedValue == null);
		} 
		return actualValue.equals(expectedValue);
	}
	
	private boolean findInExpectedValues(T actualValue) {
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

	private ContainsJSONKey(String key) {
        this.key = key;
        checkMode = Mode.ONLY_KEY;
    }
	
	private ContainsJSONKey(String key, T value) {
        this.key = key;
        this.expectedValue = value;
        checkMode = Mode.VALUE;
    }
	
	private ContainsJSONKey(String key, T[] values) {
        this.key = key;
        this.expectedValues = values;
        checkMode= Mode.VALUES;
    }
	
	@Factory
	public static Matcher<JSONObject> hasKey( String key ) {
	    return new ContainsJSONKey<Object>(key);
	}

	@Factory
	public static <T> Matcher<JSONObject> hasKey( String key, T value) {
	    return new ContainsJSONKey<T>(key, value);
	}
	
	@Factory
	public static <T> Matcher<JSONObject> hasKey( String key, T[] values) {
	    return new ContainsJSONKey<T>(key, values);
	}
}
