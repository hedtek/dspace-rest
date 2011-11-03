package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.not;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;
import static uk.ac.jorum.integration.matchers.EntityMatchers.cannotBeEdited;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasEntityId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasEntityReference;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasEntityURL;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasHandle;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasName;
import static uk.ac.jorum.integration.matchers.EntityMatchers.withValue;

import java.util.ArrayList;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;

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
  public static Matcher<JSONObject> hasIntroText(String introText) {
    return hasKey("introText", withValue(introText));
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
  public static Matcher<JSONObject> hasSubObject(String key, Matcher<JSONObject> matcher) {
    if (matcher == null)
      return new MatchJSONSubObject(key, nullValue(JSONObject.class));
    return new MatchJSONSubObject(key, matcher);
  }

  @Factory
  public static Matcher<JSONObject> hasArray(String key, ArrayList<Matcher<JSONObject>> matchers) {
    if (matchers == null)
      return new MatchJSONSubObject(key, nullValue(JSONObject.class));
    return new MatchJSONArray(key, matchers);
  }

  @Factory
	public static Matcher<JSONObject> isCommunity(int id, String name,
			String handle, String introText, String shortDescription,
			String sidebarText, String copyrightText, int itemCount,
			Matcher<JSONObject> parentCommunity,
			ArrayList<Matcher<JSONObject>> subCommunities,
			ArrayList<Matcher<JSONObject>> recentSubmissions,
			ArrayList<Matcher<JSONObject>> collections) {
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
        hasSubObject("parentCommunity", parentCommunity),
        hasArray("recentSubmissions", recentSubmissions),
        hasArray("subCommunities", subCommunities),
        hasArray("collections", collections),
        hasArray("administrators", null)
      );
  }

  @Factory
  public static Matcher<JSONObject> isCommunityId(int id) {
    return allOf(
      hasId(id)
    );
  }
  
  @Factory
	public static Matcher<JSONObject> isCollection(int id, String name,
			String handle, String introText, String shortDescription,
			String sidebarText, String copyrightText, String licence,
			String provenance, int itemCount,
			ArrayList<Matcher<JSONObject>> communities, ArrayList<Matcher<JSONObject>> items) {
    return allOf(
        cannotBeEdited(),
        hasId(id),
        hasType(3),
        hasName(name),
        hasItemCount(itemCount),
        hasHandle(handle),
        hasIntroText(introText),
        hasSidebarText(sidebarText),
        hasShortDescription(shortDescription),
        hasCopyrightText(copyrightText),
        hasKey("licence", withValue(licence)),
        hasKey("provenance", withValue(provenance)),
        hasArray("communities", communities),
        hasArray("items", items)
      );
  }
  
    
  
  @Factory
  public static Matcher<JSONObject> isCollectionId(int id) {
    return allOf(
      hasId(id),
      not(hasAnyKey(new String[] {
        "name",
        "handle"
      }))
    );
  }

}
