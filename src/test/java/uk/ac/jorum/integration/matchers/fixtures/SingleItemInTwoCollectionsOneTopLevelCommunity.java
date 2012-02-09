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

public class SingleItemInTwoCollectionsOneTopLevelCommunity {
	public static final ArrayList<Matcher<JSONObject>> subCommunityListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.thirdCommunityId());
		}
	};
	
	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdMatchers =  new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCollectionMatchers.firstCollectionId());
			add(AllCollectionMatchers.secondCollectionId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.firstCommunity(2,
					subCommunityListWithIdMatchers, emptyMatcherList(),
					collectionListWithIdMatchers));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> communityListIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.firstCommunityId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> itemIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllItemMatchers.firstItemId());
		}
	};

	public static final Matcher<JSONObject> owningCollection = AllCollectionMatchers
			.firstCollection(1, communityListIdMatchers, itemIdMatchers);

    public static final Matcher<JSONObject> owningCollectionIdOnly = AllCollectionMatchers
            .firstCollectionId();

	public static final Matcher<JSONObject> nonOwningCollection = AllCollectionMatchers
			.secondCollection(1, communityListIdMatchers, itemIdMatchers);

    public static final ArrayList<Matcher<JSONObject>> collectionListMatchers = new ArrayList<Matcher<JSONObject>>() {
        {
            add(owningCollection);
            add(nonOwningCollection);
        }
    };

    public static final ArrayList<Matcher<JSONObject>> communityListIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {
        {
            add(AllCommunityMatchers.firstCommunityId());
        }
    };

	public static final Matcher<JSONObject> item = AllItemMatchers.firstItem(
			owningCollection, communityListMatchers, collectionListMatchers);

    public static final Matcher<JSONObject> itemIdOnly = AllItemMatchers.firstItemWithIdOnlyMetadata(
            owningCollectionIdOnly, communityListIdOnlyMatchers, collectionListWithIdMatchers);
    
	public static final ArrayList<Matcher<JSONObject>> itemListIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(itemIdOnly);
		}
	};

	public static final Matcher<JSONObject> itemForCollection = AllItemMatchers
			.firstItemWithIdOnlyMetadata(
					AllCollectionMatchers.firstCollectionId(),
					communityListIdMatchers, collectionListWithIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> itemForCollectionMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(itemForCollection);
		}
	};

}
