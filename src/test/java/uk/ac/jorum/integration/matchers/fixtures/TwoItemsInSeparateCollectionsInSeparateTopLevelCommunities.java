package uk.ac.jorum.integration.matchers.fixtures;

import java.util.ArrayList;

import static uk.ac.jorum.integration.matchers.BundleMatchers.isBundleWithId;
import static uk.ac.jorum.integration.matchers.BitstreamsMatchers.isBitstreamWithId;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollection;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionWithId;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunity;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItem;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItemWithId;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItemWithMetadataId;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class TwoItemsInSeparateCollectionsInSeparateTopLevelCommunities {
	public static final ArrayList<Matcher<JSONObject>> firstCommunityListIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.firstCommunityId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> firstCollectionListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCollectionMatchers.firstCollectionId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> secondCommunityListIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.secondCommunityId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> secondCollectionListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCollectionMatchers.secondCollectionId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> secondItemListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllItemMatchers.secondItemId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> secondBundleIdMatchers = AllBundleMatchers
			.secondBundleIdList();

	public static final ArrayList<Matcher<JSONObject>> secondBitstreamIdMatchers = AllBitstreamMatchers
			.secondBitstreamIdList();

	public static final Matcher<JSONObject> submitter = AllUserMatchers
			.firstUser();

	public static final Matcher<JSONObject> firstCommunity = AllCommunityMatchers
			.firstCommunity(1, emptyMatcherList(), emptyMatcherList(),
					firstCollectionListWithIdMatchers);

	public static final Matcher<JSONObject> secondCommunity = AllCommunityMatchers
			.secondCommunity(1, emptyMatcherList(), emptyMatcherList(),
					secondCollectionListWithIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> firstCommunityMatcherList = new ArrayList<Matcher<JSONObject>>() {
		{
			add(firstCommunity);
		}
	};

	public static final ArrayList<Matcher<JSONObject>> secondCommunityMatcherList = new ArrayList<Matcher<JSONObject>>() {
		{
			add(secondCommunity);
		}
	};

	public static final Matcher<JSONObject> firstOwningCollection = isCollection(
			1, "Collection 1", "123456789/4", "Collection 1", "Collection 1",
			"Collection 1", "Collection 1", "Collection 1", "Collection 1", 1,
			firstCommunityListIdMatchers, AllItemMatchers.firstItemIdList());

	public static final Matcher<JSONObject> secondOwningCollection = isCollection(
			2, "Collection 2", "123456789/5", "Collection 2", "Collection 2",
			"Collection 2", "Collection 2", "Collection 2", "Collection 2", 1,
			secondCommunityListIdMatchers, secondItemListWithIdMatchers);;

	public static final ArrayList<Matcher<JSONObject>> firstCollectionMatcherList = new ArrayList<Matcher<JSONObject>>() {
		{
			add(firstOwningCollection);
		}
	};

	public static final ArrayList<Matcher<JSONObject>> secondCollectionMatcherList = new ArrayList<Matcher<JSONObject>>() {
		{
			add(secondOwningCollection);
		}
	};

	public static final Matcher<JSONObject> firstItem = AllItemMatchers
			.firstItem(firstOwningCollection, firstCommunityMatcherList,
					firstCollectionMatcherList);

	public static final Matcher<JSONObject> secondItem = AllItemMatchers
			.secondItem(secondOwningCollection, secondCommunityMatcherList,
					secondCollectionMatcherList);

	public static final Matcher<JSONObject> itemInFirstCollection = AllItemMatchers
			.firstItemWithIdOnlyMetadata(
					AllCollectionMatchers.firstCollectionId(),
					firstCommunityListIdMatchers,
					firstCollectionListWithIdMatchers);

	public static final Matcher<JSONObject> itemInSecondCollection = AllItemMatchers
			.secondItemWithIdOnlyMetadata(
					AllCollectionMatchers.secondCollectionId(),
					secondCommunityListIdMatchers,
					secondCollectionListWithIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> itemMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(firstItem);
			add(secondItem);
		}
	};

	public static final ArrayList<Matcher<JSONObject>> firstItemForCollectionMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(itemInFirstCollection);
		}
	};

	public static final ArrayList<Matcher<JSONObject>> secondItemForCollectionMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(itemInSecondCollection);
		}
	};
}
