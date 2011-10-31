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
		
		Object actualValue = item.get(key);

		if(checkMode == Mode.VALUE) {
			return compareWithExpectedValue(actualValue);
		} else if (checkMode == Mode.VALUES) {
			return findInExpectedValues(actualValue);
		}
		
		return isKeyPresent;
	}
	
	private boolean compareWithExpectedValue(Object actualValue) {
		if (actualValue == null) {
			return (expectedValue == null);
		} 
		return actualValue.equals(expectedValue);
	}
	
	private boolean findInExpectedValues(Object actualValue) {
		boolean itemFound = false;
		if (actualValue == null) {
			for(int i=0;i<expectedValues.length;i++) {
				if(expectedValues[i]==null) {
					itemFound = true;
					break;
				}
			} 
		} else {
			for(int i=0;i<expectedValues.length;i++) {
				if(actualValue.equals(expectedValues[i])) {
					itemFound = true;
					break;
				}
			}
		}
		return itemFound;
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
	public static Matcher<JSONObject> containsJSONKey( String key ) {
	    return new ContainsJSONKey<Object>(key);
	}

	@Factory
	public static <T> Matcher<JSONObject> containsJSONKey( String key, T value) {
	    return new ContainsJSONKey<T>(key, value);
	}
	
	@Factory
	public static <T> Matcher<JSONObject> containsJSONKey( String key, T[] values) {
	    return new ContainsJSONKey<T>(key, values);
	}
	
	public static <T> T withValue(T value) {
		return value;
	}
	
	public static <T> T[] withValueIn(T[] values) {
		return values;
	}
}
