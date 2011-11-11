/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class TwoTopLevelCommunities {
	public static final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.firstCommunity(0, emptyMatcherList(),
					emptyMatcherList(), emptyMatcherList()));

			add(AllCommunityMatchers.secondCommunity(0, emptyMatcherList(),
					emptyMatcherList(), emptyMatcherList()));
		}
	};
}
