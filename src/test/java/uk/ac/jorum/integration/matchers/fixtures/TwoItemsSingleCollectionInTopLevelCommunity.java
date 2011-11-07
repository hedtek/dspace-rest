package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.BitstreamsMatchers.isBitstream;
import static uk.ac.jorum.integration.matchers.BitstreamsMatchers.isBitstreamWithId;
import static uk.ac.jorum.integration.matchers.BundleMatchers.isBundle;
import static uk.ac.jorum.integration.matchers.BundleMatchers.isBundleWithId;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollection;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionWithId;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunity;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItem;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItemWithId;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class TwoItemsSingleCollectionInTopLevelCommunity {
	public static final ArrayList<Matcher<JSONObject>> subCommunityListWithIdMatchers = 
			SingleItemSingleCollectionTopLevelCommunity.subCommunityListWithIdMatchers;

	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdMatchers = 
			SingleItemSingleCollectionTopLevelCommunity.collectionListWithIdMatchers;

	public static final ArrayList<Matcher<JSONObject>> communityListWithIdMatchers = 
			SingleItemSingleCollectionTopLevelCommunity.communityListWithIdMatchers;

	public static final ArrayList<Matcher<JSONObject>> itemListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isItemWithId(1));
			add(isItemWithId(2));
		}
	};
	
	public static final Matcher<JSONObject> owningCollection = isCollection(1,
			"Collection 1", "123456789/6",
			"Introductory Text for collection 1",
			"Short Description for Collection 1",
			"Side bar text for collection 1",
			"Copyright information for collection 1",
			"Licence for collection 1", "Provenance for collection 1", 2,
			communityListWithIdMatchers, itemListWithIdMatchers);
	
	public static final ArrayList<Matcher<JSONObject>> collectionListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(owningCollection);
		}
	};

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
	
	public static final Matcher<JSONObject> submitter = SingleItemSingleCollectionTopLevelCommunity.submitter;
	
	public static final ArrayList<Matcher<JSONObject>> bundleMatchersForItem1 = SingleItemSingleCollectionTopLevelCommunity.bundleMatchers;
	
	public static final ArrayList<Matcher<JSONObject>> bitstreamMatchersForItem1 = SingleItemSingleCollectionTopLevelCommunity.bitstreamMatchers;
	
	public static final ArrayList<Matcher<JSONObject>> bitstreamIdMatchersForItem2 = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBitstreamWithId(3));
			add(isBitstreamWithId(4));
		}
	};
	
	public static final ArrayList<Matcher<JSONObject>> bundleMatchersForItem2 = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBundle(3, "ORIGINAL", itemListWithIdMatchers, bitstreamIdMatchersForItem2));
			add(isBundle(4, "LICENSE", itemListWithIdMatchers, bitstreamIdMatchersForItem2));
		}
	};
	
	public static final ArrayList<Matcher<JSONObject>> bundleIdMatchersForItem2 = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBundleWithId(3));
		}
	};
	
	public static final ArrayList<Matcher<JSONObject>> bitstreamMatchersForItem2 = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBitstream(3, "secondUpload.txt", "Text", "text/plain", bundleIdMatchersForItem2));
		}
	};

	public static final Matcher<JSONObject> firstItem = isItem(1, "First Upload",
			"123456789/7", true, false, owningCollection, submitter,
			communityListMatchers, collectionListMatchers, bundleMatchersForItem1, bitstreamMatchersForItem1);

	public static final Matcher<JSONObject> secondItem = isItem(2, "Second upload",
			"123456789/8", true, false, owningCollection, submitter,
			communityListMatchers, collectionListMatchers, bundleMatchersForItem2, bitstreamMatchersForItem2);

	public static final ArrayList<Matcher<JSONObject>> itemMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(firstItem);
			add(secondItem);
		}
	};
}
