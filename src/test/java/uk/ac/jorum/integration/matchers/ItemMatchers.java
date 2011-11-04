package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.allOf;
import static uk.ac.jorum.integration.matchers.EntityMatchers.cannotBeEdited;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasHandle;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasId;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasKeys;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasName;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasSubObject;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasType;
import static uk.ac.jorum.integration.matchers.EntityMatchers.withValue;

import java.util.ArrayList;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class ItemMatchers {
	 @Factory
		public static Matcher<JSONObject> isItem(int id, String name,
				String handle, int itemCount, long lastModified, boolean isArchived, boolean isWithdrawn,
				Matcher<JSONObject> owningCollection,
				ArrayList<Matcher<JSONObject>> communities,
				ArrayList<Matcher<JSONObject>> collections) {
	     String [] keys = {"bitstream", "metadata", "bundles", "submitter"};
	     
	     return allOf(
	        cannotBeEdited(),
	        hasId(id),
	        hasType(2),
	        hasName(name),
	        hasHandle(handle),
	        hasKey("isArchived", withValue(isArchived)),
	        hasKey("isWithdrawn", withValue(isWithdrawn)),
	        hasKey("lastModified", withValue(lastModified)),      
	        hasSubObject("owningCollection", owningCollection),
	        hasArray("communities", communities),
	        hasArray("collections", collections),
	        hasKeys(keys)
	      );
	  }

	  @Factory
	  public static Matcher<JSONObject> isItemWithId(int id) {
	    return hasId(id);
	  }
}
