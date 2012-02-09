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

public class TwoCollectionsUnderTopLevelCommunity {
	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCollectionMatchers.firstCollectionId());
			add(AllCollectionMatchers.secondCollectionId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> subCommunityListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.thirdCommunityId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.firstCommunity(0,
					subCommunityListWithIdMatchers, emptyMatcherList(),
					collectionListWithIdOnlyMatchers));
		}
	};
	
    public static final ArrayList<Matcher<JSONObject>> communityListIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {
        {
            add(AllCommunityMatchers.firstCommunityId());
        }
    };
    
	public static final ArrayList<Matcher<JSONObject>> collectionListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCollectionMatchers.firstCollection(0, communityListMatchers,
					emptyMatcherList()));
			add(AllCollectionMatchers.secondCollection(0,
					communityListMatchers, emptyMatcherList()));
		}
	};
	
	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdOnlyCommunitiesMatchers = new ArrayList<Matcher<JSONObject>>() {
        {
            add(AllCollectionMatchers.firstCollection(0, communityListIdOnlyMatchers,
                    emptyMatcherList()));
            add(AllCollectionMatchers.secondCollection(0,
                    communityListIdOnlyMatchers, emptyMatcherList()));
        }
    };	

}
