/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunity;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class AllCommunityMatchers {
	public static final Matcher<JSONObject> firstCommunity(int itemCount,
			ArrayList<Matcher<JSONObject>> subCommunities,
			ArrayList<Matcher<JSONObject>> recentSubmissions,
			ArrayList<Matcher<JSONObject>> collections) {
		return isCommunity(2, "Community no 1", "123456789/2",
				"Introductory text for community no 1",
				"Short description of community no 1",
				"Side bar text for community 1", "Copyright information",
				itemCount, null, subCommunities, recentSubmissions, collections);
	}

	public static final Matcher<JSONObject> firstCommunityId() {
		return isCommunityWithId(2);
	}

	public static final Matcher<JSONObject> secondCommunity(int itemCount,
			ArrayList<Matcher<JSONObject>> subCommunities,
			ArrayList<Matcher<JSONObject>> recentSubmissions,
			ArrayList<Matcher<JSONObject>> collections) {
		return isCommunity(3, "Top level community no 2", "123456789/3", null,
				null, null, null, itemCount, null, subCommunities,
				recentSubmissions, collections);
	}

	public static final Matcher<JSONObject> secondCommunityId() {
		return isCommunityWithId(3);
	}

	public static final Matcher<JSONObject> thirdCommunity(int itemCount,
			Matcher<JSONObject> parent,
			ArrayList<Matcher<JSONObject>> subCommunities,
			ArrayList<Matcher<JSONObject>> recentSubmissions,
			ArrayList<Matcher<JSONObject>> collections) {
		return isCommunity(4, "Sub-community", "123456789/4",
				"Introductory text for the sub-community",
				"This is a sub-community for a top-level community", "", "", 0,
				parent, subCommunities, recentSubmissions, collections);
	}

	public static final Matcher<JSONObject> thirdCommunityId() {
		return isCommunityWithId(4);
	}

	public static final ArrayList<Matcher<JSONObject>> communityListWithIdMatchers() {
		return new ArrayList<Matcher<JSONObject>>() {
			{
				add(AllCommunityMatchers.firstCommunityId());
			}
		};
	}

	public static final ArrayList<Matcher<JSONObject>> subCommunityListWithIdMatchers() {
		return new ArrayList<Matcher<JSONObject>>() {
			{
				add(AllCommunityMatchers.thirdCommunityId());
			}
		};
	}

}
