package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.BundleMatchers.isBundleWithId;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollection;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionWithId;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunity;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItem;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItemWithId;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItemWithMetadataId;

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

	public static final Matcher<JSONObject> nonOwningCollection = AllCollectionMatchers
			.secondCollection(1, communityListIdMatchers, itemIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> collectionListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(owningCollection);
			add(nonOwningCollection);
		}
	};

	public static final Matcher<JSONObject> item = AllItemMatchers.firstItem(
			owningCollection, communityListMatchers, collectionListMatchers);

	public static final ArrayList<Matcher<JSONObject>> itemMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(item);
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
