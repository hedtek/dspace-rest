package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.not;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;

public class EntityMatchers {
	@Factory
	public static Matcher<JSONObject> cannotBeEdited() {
		return hasKey("canEdit", withValue(false));
	}
	
	@Factory
	public static Matcher<JSONObject> hasHandle(String handle) {
		return hasKey("handle", withValue(handle));
	}
	
	@Factory
	public static Matcher<JSONObject> hasId(int id) {
		return hasKey("id", withValue(new Long(id)));
	}
	
	@Factory
	public static Matcher<JSONObject> hasName(String name){
		return hasKey("name", withValue(name));		
	}
	
	@Factory
	public static Matcher<JSONObject> hasNo(String key) {
		return not(hasKey(key));
	}
	
	@Factory
	public static Matcher<JSONObject> hasEntityReference(String entityReference){
		return hasKey("entityReference", withValue(entityReference));		
	}
	
	@Factory
	public static Matcher<JSONObject> hasEntityURL(String entityURL){
		return hasKey("entityURL", withValue(entityURL));		
	}

	@Factory
	public static <T> T withValue(T value) {
		return value;
	}

	@Factory
	public static <T> T[] withValueIn(T[] values) {
		return values;
	}
}
