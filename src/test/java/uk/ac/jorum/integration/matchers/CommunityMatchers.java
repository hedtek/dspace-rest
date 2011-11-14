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

public class CommunityMatchers {

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
        hasKey("administrators", null)
      );
  }

  @Factory
  public static Matcher<JSONObject> isCommunityWithId(int id) {
    return hasId(id);
  }
  
}
