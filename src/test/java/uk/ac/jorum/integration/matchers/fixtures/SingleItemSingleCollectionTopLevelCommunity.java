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

public class SingleItemSingleCollectionTopLevelCommunity {

	public static final ArrayList<Matcher<JSONObject>> subCommunityListWithIdMatchers = AllCommunityMatchers.subCommunityListWithIdMatchers();

	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdMatchers = AllCollectionMatchers.collectionListWithIdMatchers();

	public static final ArrayList<Matcher<JSONObject>> communityListWithIdMatchers = AllCommunityMatchers.communityListWithIdMatchers();

	public static final ArrayList<Matcher<JSONObject>> itemListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllItemMatchers.firstItemId());
		}
	};
	public static final Matcher<JSONObject> owningCollection = AllCollectionMatchers
			.firstCollection(1, communityListWithIdMatchers,
					itemListWithIdMatchers);
	
    public static final Matcher<JSONObject> owningCollectionIdOnly = AllCollectionMatchers
            .firstCollectionId();

	public static final Matcher<JSONObject> submitter = AllUserMatchers.firstUser();

	public static final ArrayList<Matcher<JSONObject>> bitstreamMatchers = AllBitstreamMatchers
			.firstBitstreamList();

	public static final ArrayList<Matcher<JSONObject>> bitstreamIdMatchers = AllBitstreamMatchers
			.firstBitstreamIdList();

	public static final ArrayList<Matcher<JSONObject>> bundleMatchers = AllBundleMatchers
			.firstBundleWithLicenceList();

	public static final ArrayList<Matcher<JSONObject>> collectionListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(owningCollection);
		}
	};
	
	public static final ArrayList<Matcher<JSONObject>> collectionListIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {
        {
            add(owningCollectionIdOnly);
        }
    };
    
	public static final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.firstCommunity(1,
					subCommunityListWithIdMatchers, emptyMatcherList(),
					collectionListWithIdMatchers));
		}
	};

    public static final ArrayList<Matcher<JSONObject>> communityListIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {
        {
            add(AllCommunityMatchers.firstCommunityId());
        }
    };
    
	public static final Matcher<JSONObject> item = AllItemMatchers.firstItem(
			owningCollection, communityListMatchers, collectionListMatchers);

	
    public static final Matcher<JSONObject> itemInItemsList = AllItemMatchers.firstItemWithIdOnlyMetadata(
            owningCollectionIdOnly, communityListIdOnlyMatchers, collectionListIdOnlyMatchers);

    public static final ArrayList<Matcher<JSONObject>> idOnlyItemMatchers = new ArrayList<Matcher<JSONObject>>() {
        {
            add(itemInItemsList);
        }
    };
    
    
}
