/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers;

import static uk.ac.jorum.integration.matchers.EntityMatchers.*;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;

import java.util.ArrayList;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import static org.hamcrest.CoreMatchers.allOf;

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
  public static Matcher<JSONObject> isCollectionWithId(int id) {
    return hasId(id);
  }
}
