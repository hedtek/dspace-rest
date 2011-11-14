/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;

import java.util.ArrayList;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class EntityMatchers {
	@Factory
	public static Matcher<JSONObject> cannotBeEdited() {
		return hasKey("canEdit", withValue(false));
	}

	@Factory
	public static ArrayList<Matcher<JSONObject>> emptyMatcherList() {
		return new ArrayList<Matcher<JSONObject>>();
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
	public static Matcher<JSONObject> hasIdIn(Integer[] ids) {
		ArrayList<Matcher<JSONObject>> idMatchers = new ArrayList<Matcher<JSONObject>>();
		for (Integer id : ids) {
			idMatchers.add(hasKey("id", withValue(new Long(id))));
		}
		return anyOf((Iterable) idMatchers);
	}

	@Factory
	public static Matcher<JSONObject> hasIdIn(int[] ids) {
		Long[] long_ids = new Long[ids.length];
		for (int i = 0; i < ids.length; i++) {
			long_ids[i] = new Long(ids[i]);
		}
		return hasKey("id", withValueIn(long_ids));

	}

	@Factory
	public static Matcher<JSONObject> hasName(String name) {
		return hasKey("name", withValue(name));
	}

	@Factory
	public static Matcher<JSONObject> hasNo(String key) {
		return not(hasKey(key));
	}

	@Factory
	public static Matcher<JSONObject> hasEntityReference(String entityReference) {
		return hasKey("entityReference", withValue(entityReference));
	}

	@Factory
	public static Matcher<JSONObject> hasEntityURL(String entityURL) {
		return hasKey("entityURL", withValue(entityURL));
	}

	@Factory
	public static Matcher<JSONObject> hasEntityTitle() {
		return hasKey("entityTitle");
	}

	@Factory
	public static Matcher<JSONObject> hasType(int type) {
		return hasKey("type", withValue(new Long(type)));
	}

	@Factory
	public static Matcher<JSONObject> hasEntityId() {
		return hasKey("entityId");
	}

	@Factory
	public static Matcher<JSONObject> hasKeys(String[] keys) {
		ArrayList<Matcher<JSONObject>> keyMatchers = new ArrayList<Matcher<JSONObject>>();
		for (String key : keys) {
			keyMatchers.add(hasKey(key));
		}
		return allOf((Iterable) keyMatchers);
	}

	@Factory
	public static Matcher<JSONObject> hasAnyKey(String[] keys) {
		ArrayList<Matcher<JSONObject>> keyMatchers = new ArrayList<Matcher<JSONObject>>();
		for (String key : keys) {
			keyMatchers.add(hasKey(key));
		}
		return anyOf((Iterable) keyMatchers);
	}

	@Factory
	public static Matcher<JSONObject> hasCopyrightText(String copyrightText) {
		return hasKey("copyrightText", withValue(copyrightText));
	}

	@Factory
	public static Matcher<JSONObject> hasIntroductoryText(String introText) {
		return hasKey("introductoryText", withValue(introText));
	}

	@Factory
	public static Matcher<JSONObject> hasIntroText(String introText) {
		return hasKey("introText", withValue(introText));
	}

	@Factory
	public static Matcher<JSONObject> hasSidebarText(String sidebarText) {
		return hasKey("sidebarText", withValue(sidebarText));
	}

	@Factory
	public static Matcher<JSONObject> hasShortDescription(
			String shortDescription) {
		return hasKey("shortDescription", withValue(shortDescription));
	}

	@Factory
	public static Matcher<JSONObject> hasItemCount(int itemCount) {
		return hasKey("countItems", withValue(new Long(itemCount)));
	}

	@Factory
	public static Matcher<JSONObject> hasResultsCount(int resultsCount) {
		return hasKey("resultsCount", withValue(new Long(resultsCount)));
	}
	
	public static <T> T withValue(T value) {
		return value;
	}

	@Factory
	public static <T> T[] withValueIn(T[] values) {
		return values;
	}

	@Factory
	public static Matcher<JSONObject> hasSubObject(String key,
			Matcher<JSONObject> matcher) {
		if (matcher == null)
			return new MatchJSONSubObject(key, nullValue(JSONObject.class));
		return new MatchJSONSubObject(key, matcher);
	}

	@Factory
	public static <T> Matcher<JSONObject> hasArray(String key,
			ArrayList<Matcher<T>> matchers) {
		if (matchers == null)
			return new MatchJSONSubObject(key, nullValue(JSONObject.class));
		return new MatchJSONArray<T>(key, matchers);
	}

//	@Factory
//	public static Matcher<JSONObject> hasArray(String key,
//			ArrayList<Matcher<Long>> matchers) {
//		if (matchers == null)
//			return new MatchJSONSubObject(key, nullValue(JSONObject.class));
//		return new MatchJSONArray(key, matchers);
//	}
	
	@Factory
	public static Matcher<JSONObject> isEntityElement(int id,
			String entityType, boolean data) {
		return allOf(hasKey("data", withValue(data)), entityElementMatch(id, entityType));
	}
	
	@Factory
	public static Matcher<JSONObject> isEntityElement(int id,
			String entityType, long data) {
		return allOf(hasKey("data", withValue(new Long(data))), entityElementMatch(id, entityType));
	}
	
	@Factory
	public static Matcher<JSONObject> isEntityElement(int id,
			String entityType, String data) {
		return allOf(hasKey("data", withValue(data)), entityElementMatch(id, entityType));
	}
	
	@Factory
	public static Matcher<JSONObject> isEntityElement(int id,
			String entityType, Matcher<JSONObject> dataMatcher) {
		return allOf(hasSubObject("data", dataMatcher), entityElementMatch(id, entityType));
	}
	
	@Factory
	public static Matcher<JSONObject> isEntityElementWithArray(int id,
			String entityType, ArrayList<Matcher<JSONObject>> data) {
		return allOf(hasArray("data", data), entityElementMatch(id, entityType));
	}
	
	private static Matcher<JSONObject> entityElementMatch(int id,
			String entityType) {
		return allOf(hasEntityReference("/" + entityType + "/" + id),
      				 hasEntityURL("http://localhost:8080/dspace-rest/" + entityType + "/" + id),
				     hasEntityId(),
				     hasKey("displayTitle"),
				     hasKey("entityProperties"));
	}
}
