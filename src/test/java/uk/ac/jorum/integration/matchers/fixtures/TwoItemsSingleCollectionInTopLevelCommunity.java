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

	public static final Matcher<JSONObject> firstItem = AllItemMatchers.firstItem(owningCollection, communityListMatchers, collectionListMatchers);

	public static final Matcher<JSONObject> secondItem = AllItemMatchers.secondItem(owningCollection, communityListMatchers, collectionListMatchers);

	public static final ArrayList<Matcher<JSONObject>> itemMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(firstItem);
			add(secondItem);
		}
	};
}
