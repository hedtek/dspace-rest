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
			add(isCollectionWithId(1));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> firstItemListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isItemWithId(1));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> firstBundleIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBundleWithId(1));
			add(isBundleWithId(2));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> firstBitstreamIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBitstreamWithId(1));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> secondCommunityListIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.secondCommunityId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> secondCollectionListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isCollectionWithId(2));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> secondItemListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isItemWithId(2));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> secondBundleIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBundleWithId(3));
			add(isBundleWithId(4));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> secondBitstreamIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBitstreamWithId(3));
		}
	};

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
			firstCommunityListIdMatchers, firstItemListWithIdMatchers);

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

	public static final ArrayList<Matcher<JSONObject>> bundleMatchersForItem1 = TwoItemsSingleCollectionInTopLevelCommunity.bundleMatchersForItem1;

	public static final ArrayList<Matcher<JSONObject>> bitstreamMatchersForItem1 = TwoItemsSingleCollectionInTopLevelCommunity.bitstreamMatchersForItem1;

	public static final ArrayList<Matcher<JSONObject>> bundleMatchersForItem2 = TwoItemsSingleCollectionInTopLevelCommunity.bundleMatchersForItem2;

	public static final ArrayList<Matcher<JSONObject>> bitstreamMatchersForItem2 = TwoItemsSingleCollectionInTopLevelCommunity.bitstreamMatchersForItem2;

	public static final Matcher<JSONObject> firstItem = isItem(1,
			"First Upload", "123456789/6", true, false, firstOwningCollection,
			submitter, firstCommunityMatcherList, firstCollectionMatcherList,
			bundleMatchersForItem1, bitstreamMatchersForItem1);

	public static final Matcher<JSONObject> secondItem = isItem(2,
			"Second Upload", "123456789/7", true, false,
			secondOwningCollection, submitter, secondCommunityMatcherList,
			secondCollectionMatcherList, bundleMatchersForItem2,
			bitstreamMatchersForItem2);

	public static final Matcher<JSONObject> itemInFirstCollection = isItemWithMetadataId(
			1, "First Upload", "123456789/6", true, false,
			isCollectionWithId(1), submitter, firstCommunityListIdMatchers,
			firstCollectionListWithIdMatchers, firstBundleIdMatchers,
			firstBitstreamIdMatchers);

	public static final Matcher<JSONObject> itemInSecondCollection = isItemWithMetadataId(
			2, "Second Upload", "123456789/7", true, false,
			isCollectionWithId(2), submitter, secondCommunityListIdMatchers,
			secondCollectionListWithIdMatchers, secondBundleIdMatchers,
			secondBitstreamIdMatchers);

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
