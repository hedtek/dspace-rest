package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollection;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionWithId;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunity;
import static uk.ac.jorum.integration.matchers.BundleMatchers.isBundleWithId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;
import static uk.ac.jorum.integration.matchers.ItemMatchers.*;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import org.mortbay.util.ajax.JSON;

public class SingleItemInTwoCollectionsOneTopLevelCommunity {

	public static final ArrayList<Matcher<JSONObject>> subCommunityListWithIdMatchers = TwoCollectionsUnderTopLevelCommunity.subCommunityListWithIdMatchers;
	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdMatchers = TwoCollectionsUnderTopLevelCommunity.collectionListWithIdOnlyMatchers;

	public static final Matcher<JSONObject> parentCommunity = isCommunity(2,
			"Community no 1", "123456789/2",
			"Introductory text for community no 1",
			"Short description of community no 1",
			"Side bar text for community 1", "Copyright information", 2, null,
			subCommunityListWithIdMatchers, emptyMatcherList(),
			collectionListWithIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(parentCommunity);
		}
	};

	public static final ArrayList<Matcher<JSONObject>> communityListIdMatchers = TwoItemsInTwoDifferentCollectionsUnderSameCommunity.communityListIdMatchers;

	public static final ArrayList<Matcher<JSONObject>> itemIdMatchers = SingleItemSingleCollectionTopLevelCommunity.itemListWithIdMatchers;

	public static final Matcher<JSONObject> owningCollection = isCollection(1,
			"Collection 1", "123456789/6",
			"Introductory Text for collection 1",
			"Short Description for Collection 1",
			"Side bar text for collection 1",
			"Copyright information for collection 1",
			"Licence for collection 1", "Provenance for collection 1", 1,
			communityListIdMatchers, itemIdMatchers);

	public static final Matcher<JSONObject> nonOwningCollection = isCollection(
			2, "Collection 2", "123456789/7",
			"Introductory Text for collection 2",
			"Short Description for Collection 2",
			"Side bar text for collection 2",
			"Copyright text for collection 2", "Licence text for collection 2",
			"Provenance text for collection 2", 1, communityListIdMatchers,
			itemIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> collectionListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(owningCollection);
			add(nonOwningCollection);
		}
	};

	public static final Matcher<JSONObject> submitter = SingleItemSingleCollectionTopLevelCommunity.submitter;

	public static final ArrayList<Matcher<JSONObject>> bundleIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBundleWithId(1));
			add(isBundleWithId(2));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> bitstreamIdMatchers = SingleItemSingleCollectionTopLevelCommunity.bitstreamIdMatchers;

	public static final ArrayList<Matcher<JSONObject>> bundleMatchers = SingleItemSingleCollectionTopLevelCommunity.bundleMatchers;

	public static final ArrayList<Matcher<JSONObject>> bitstreamMatchers = SingleItemSingleCollectionTopLevelCommunity.bitstreamMatchers;

	public static final Matcher<JSONObject> item = isItem(1, "First Upload",
			"123456789/8", true, false, owningCollection, submitter,
			communityListMatchers, collectionListMatchers, bundleMatchers,
			bitstreamMatchers);

	public static final ArrayList<Matcher<JSONObject>> itemMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(item);
		}
	};

	public static final Matcher<JSONObject> itemForCollection = isItemWithMetadataId(
			1, "First Upload", "123456789/8", true, false,
			isCollectionWithId(1), submitter, communityListIdMatchers,
			collectionListWithIdMatchers, bundleIdMatchers, bitstreamIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> itemForCollectionMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(itemForCollection);
		}
	};

}
