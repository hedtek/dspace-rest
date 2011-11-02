package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.not;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;

import java.util.ArrayList;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;

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
	public static  Matcher<JSONObject> hasIdIn(Integer[] ids) {
    ArrayList<Matcher<JSONObject>> idMatchers = new ArrayList<Matcher<JSONObject>>();
    for(Integer id : ids) {
      idMatchers.add(hasKey("id", withValue(new Long(id))));
    }
    return anyOf((Iterable) idMatchers);
	}
	
	@Factory
	public static  Matcher<JSONObject> hasIdIn(int[] ids) {
		Long[] long_ids = new Long[ids.length];
		for(int i = 0; i < ids.length; i++) {
			long_ids[i] = new Long(ids[i]);
		}
		return hasKey("id", withValueIn(long_ids));
		
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
  public static Matcher<JSONObject> hasEntityTitle() {
    return hasKey("entityTitle");
  }
	
	@Factory
	public static Matcher<JSONObject> hasType(int type){
		return hasKey("type", withValue(new Long(type)));		
	}
	
	@Factory
	public static Matcher<JSONObject> hasEntityId() {
		return hasKey("entityId");
	}

  @Factory
  public static Matcher<JSONObject> hasKeys(String[] keys) {
    ArrayList<Matcher<JSONObject>> keyMatchers = new ArrayList<Matcher<JSONObject>>();
    for(String key : keys) {
      keyMatchers.add(hasKey(key));
    }
    return allOf((Iterable)keyMatchers);
  }

  @Factory
  public static Matcher<JSONObject> hasAnyKey(String[] keys) {
    ArrayList<Matcher<JSONObject>> keyMatchers = new ArrayList<Matcher<JSONObject>>();
    for(String key : keys) {
      keyMatchers.add(hasKey(key));
    }
    return anyOf((Iterable)keyMatchers);
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
  public static Matcher<JSONObject> hasSidebarText(String sidebarText) {
    return hasKey("sidebarText", withValue(sidebarText));
  }

  @Factory
  public static Matcher<JSONObject> hasShortDescription(String shortDescription) {
    return hasKey("shortDescription", withValue(shortDescription));
  }

  @Factory
  public static Matcher<JSONObject> hasItemCount(int itemCount) {
    return hasKey("countItems", withValue(new Long(itemCount)));
  }

	public static <T> T withValue(T value) {
		return value;
	}

	@Factory
	public static <T> T[] withValueIn(T[] values) {
		return values;
	}

  @Factory
  public static Matcher<JSONObject> isCommunity(int id, String name, String handle, String introText, String shortDescription, String sidebarText, String copyrightText, int itemCount, Matcher<JSONObject> parentCommunity, Iterable<Matcher<JSONObject>> subCommunities, Iterable<Matcher<JSONObject>> recentSubmissions, Iterable<Matcher<JSONObject>> collections) {
    return allOf(
        cannotBeEdited(),
        hasId(id),
        hasType(4),
        hasName(name),
        hasItemCount(itemCount),
        hasHandle(handle),
        hasIntroductoryText(introText),
        hasSidebarText(sidebarText),
        hasShortDescription(shortDescription),
        hasCopyrightText(copyrightText),
        hasEntityReference("/communities/" + id),
        hasEntityURL("http://localhost:8080/dspace-rest/communities/" + id),
        hasEntityId(),
        hasKeys(new String[] {
          "recentSubmissions",
          "subCommunities",
          "parentCommunity",
          "administrators",
          "collections"
        })
      );
  }

  @Factory
  public static Matcher<JSONObject> isCommunityId(int id) {
    return allOf(
      hasId(id),
      hasEntityReference("/communities/" + id),
      hasEntityURL("http://localhost:8080/dspace-rest/communities/" + id),
      hasEntityId(),
      not(hasAnyKey(new String[] {
        "name",
        "recentSubmissions",
        "handle"
      }))
    );
  }

}
