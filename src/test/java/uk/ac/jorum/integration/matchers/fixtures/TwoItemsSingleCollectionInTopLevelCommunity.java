package uk.ac.jorum.integration.matchers.fixtures;


import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItemWithId;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class TwoItemsSingleCollectionInTopLevelCommunity {
	public static final ArrayList<Matcher<JSONObject>> subCommunityListWithIdMatchers = AllCommunityMatchers.subCommunityListWithIdMatchers();

	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdMatchers = AllCollectionMatchers.collectionListWithIdMatchers();

	public static final ArrayList<Matcher<JSONObject>> communityListWithIdMatchers = AllCommunityMatchers.communityListWithIdMatchers();
	
	public static final ArrayList<Matcher<JSONObject>> itemListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isItemWithId(1));
			add(isItemWithId(2));
		}
	};

	public static final Matcher<JSONObject> owningCollection = AllCollectionMatchers
			.firstCollection(2, communityListWithIdMatchers,
					itemListWithIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> collectionListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(owningCollection);
		}
	};

	public static final Matcher<JSONObject> parentCommunity = AllCommunityMatchers
			.firstCommunity(2, subCommunityListWithIdMatchers,
					emptyMatcherList(), collectionListWithIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(parentCommunity);
		}
	};

	public static final Matcher<JSONObject> firstItem = AllItemMatchers
			.firstItem(owningCollection, communityListMatchers,
					collectionListMatchers);

	public static final Matcher<JSONObject> secondItem = AllItemMatchers
			.secondItem(owningCollection, communityListMatchers,
					collectionListMatchers);

	public static final ArrayList<Matcher<JSONObject>> itemMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(firstItem);
			add(secondItem);
		}
	};
}
