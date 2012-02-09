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

public class SingleCollectionUnderTopLevelCommunity {
	
	public static final ArrayList<Matcher<JSONObject>> subCommunityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.thirdCommunityId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> collectionListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCollectionMatchers.firstCollectionId());
		}
	};

	public static final Matcher<JSONObject> parentCommunity = AllCommunityMatchers
			.firstCommunity(0, subCommunityListMatchers, emptyMatcherList(),
					collectionListMatchers);

	public static final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(parentCommunity);
		}
	};
	
	public static final ArrayList<Matcher<JSONObject>> communityListIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {
        {
            add(AllCommunityMatchers.firstCommunityId());
        }
    };

}
