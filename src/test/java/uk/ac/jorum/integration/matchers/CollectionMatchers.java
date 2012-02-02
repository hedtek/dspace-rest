/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.allOf;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;
import static uk.ac.jorum.integration.matchers.EntityMatchers.cannotBeEdited;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasCopyrightText;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasHandle;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasIntroText;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasItemCount;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasKeys;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasName;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasShortDescription;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasSidebarText;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasType;
import static uk.ac.jorum.integration.matchers.EntityMatchers.withValue;

import java.util.ArrayList;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class CollectionMatchers {
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
	public static Matcher<JSONObject> isCollectionSearchResult(int id,
			String name) {
		final String[] collectionKeys = { "canEdit", "communities",
				"copyrightText", "countItems", "handle", "introText", "items",
				"logo", "provenance", "shortDescription", "sidebarText" };
		return allOf(hasId(id), 
					hasName(name), 
					hasKeys(collectionKeys),
					hasType(3));
  	}
  
  @Factory
  public static Matcher<JSONObject> isCollectionWithId(int id) {
    return hasId(id);
  }
}
