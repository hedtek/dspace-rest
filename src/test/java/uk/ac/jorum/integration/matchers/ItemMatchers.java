package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;
import static uk.ac.jorum.integration.matchers.EntityMatchers.cannotBeEdited;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasHandle;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasKeys;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasName;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasSubObject;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasType;
import static uk.ac.jorum.integration.matchers.EntityMatchers.withValue;
import static uk.ac.jorum.integration.matchers.MetadataMatcher.isMetadataItem;

import java.util.ArrayList;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class ItemMatchers {

    
    static final ArrayList<Matcher<JSONObject>> metadataMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add((isMetadataItem(0, "contributor", "author")));
			add((isMetadataItem(0, "date", "accessioned")));
			add((isMetadataItem(0, "date", "available")));
			add((isMetadataItem(0, "date", "issued")));
			add((isMetadataItem(0, "identifier", "uri")));
			add((isMetadataItem(0, "description", nullValue(JSONObject.class))));
			add((isMetadataItem(0, "description", "provenance")));
			add((isMetadataItem(0, "description", "abstract")));
			add((isMetadataItem(0, "description", "sponsorship")));
			add((isMetadataItem(0, "language", "iso")));
			add((isMetadataItem(0, "subject", nullValue(JSONObject.class))));
			add((isMetadataItem(0, "title", nullValue(JSONObject.class))));
		}
	 };
	 
	 @Factory
		public static Matcher<JSONObject> isItem(int id, String name,
				String handle, boolean isArchived, boolean isWithdrawn,
				Matcher<JSONObject> owningCollection,
				Matcher<JSONObject> submitter,
				ArrayList<Matcher<JSONObject>> communities,
				ArrayList<Matcher<JSONObject>> collections,
				ArrayList<Matcher<JSONObject>> bundles,
				ArrayList<Matcher<JSONObject>> bitstreams)
		{
	     String [] keys = {"submitter", "lastModified"};
	     
	     return allOf(
	        cannotBeEdited(),
	        hasId(id),
	        hasType(2),
	        hasName(name),
	        hasHandle(handle),
	        hasKey("isArchived", withValue(isArchived)),
	        hasKey("isWithdrawn", withValue(isWithdrawn)),
	        hasSubObject("owningCollection", owningCollection),
	        hasSubObject("submitter", submitter),
	        hasArray("metadata", metadataMatchers),
	        hasArray("communities", communities),
	        hasArray("collections", collections),
	        hasArray("bundles", bundles),
	        hasArray("bitstreams", bitstreams),
	        hasKeys(keys)
	      );
	  }

	  @Factory
	  public static Matcher<JSONObject> isItemWithId(int id) {
	    return hasId(id);
	  }
	  
	  @Factory
	  public static ArrayList<Matcher<JSONObject>> getMetadataMatchers() {
		  return metadataMatchers;
	  }
}
