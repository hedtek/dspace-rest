package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.BitstreamsMatchers.isBitstream;
import static uk.ac.jorum.integration.matchers.BitstreamsMatchers.isBitstreamWithId;
import static uk.ac.jorum.integration.matchers.BundleMatchers.isBundle;
import static uk.ac.jorum.integration.matchers.BundleMatchers.isBundleWithId;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollection;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunity;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItem;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItemWithId;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class TwoItemsInTwoDifferentCollectionsUnderSameCommunity {

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

	public static final ArrayList<Matcher<JSONObject>> communityListIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isCommunityWithId(2));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> itemIdMatchersFirstCollection = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isItemWithId(1));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> itemIdMatchersSecondCollection = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isItemWithId(2));
		}
	};

	public static final Matcher<JSONObject> owningCollectionForFirstItem = isCollection(
			1, "Collection 1", "123456789/6",
			"Introductory Text for collection 1",
			"Short Description for Collection 1",
			"Side bar text for collection 1",
			"Copyright information for collection 1",
			"Licence for collection 1", "Provenance for collection 1", 1,
			communityListIdMatchers, itemIdMatchersFirstCollection);

	public static final Matcher<JSONObject> owningCollectionForSecondItem = isCollection(
			2, "Collection 2", "123456789/7",
			"Introductory Text for collection 2",
			"Short Description for Collection 2",
			"Side bar text for collection 2",
			"Copyright text for collection 2", "Licence text for collection 2",
			"Provenance text for collection 2", 1, communityListIdMatchers,
			itemIdMatchersSecondCollection);

	public static final ArrayList<Matcher<JSONObject>> collectionListMatchersForFirstItem = new ArrayList<Matcher<JSONObject>>() {
		{
			add(owningCollectionForFirstItem);
		}
	};

	public static final ArrayList<Matcher<JSONObject>> collectionListMatchersForSecondItem = new ArrayList<Matcher<JSONObject>>() {
		{
			add(owningCollectionForSecondItem);
		}
	};

	public static final Matcher<JSONObject> firstItem = AllItemMatchers
			.firstItem(owningCollectionForFirstItem, communityListMatchers,
					collectionListMatchersForFirstItem);

	public static final Matcher<JSONObject> secondItem = AllItemMatchers.secondItem(owningCollectionForSecondItem, communityListMatchers, collectionListMatchersForSecondItem);

	public static final ArrayList<Matcher<JSONObject>> itemMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(firstItem);
			add(secondItem);
		}
	};
}
