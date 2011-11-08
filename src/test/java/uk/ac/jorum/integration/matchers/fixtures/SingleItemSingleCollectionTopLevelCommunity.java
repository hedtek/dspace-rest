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
import static uk.ac.jorum.integration.matchers.UserMatchers.isUser;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class SingleItemSingleCollectionTopLevelCommunity {

	public static final ArrayList<Matcher<JSONObject>> subCommunityListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.thirdCommunityId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCollectionMatchers.firstCollectionId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> communityListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.firstCommunityId());
		}
	};

	public static final ArrayList<Matcher<JSONObject>> itemListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllItemMatchers.firstItemId());
		}
	};
	public static final Matcher<JSONObject> owningCollection = AllCollectionMatchers
			.firstCollection(1, communityListWithIdMatchers,
					itemListWithIdMatchers);

	public static final Matcher<JSONObject> submitter = AllUserMatchers
			.firstUser();

	public static final ArrayList<Matcher<JSONObject>> bitstreamMatchers = AllBitstreamMatchers
			.firstBitstreamList();

	public static final ArrayList<Matcher<JSONObject>> bitstreamIdMatchers = AllBitstreamMatchers
			.firstBitstreamIdList();

	public static final ArrayList<Matcher<JSONObject>> bundleMatchers = AllBundleMatchers
			.firstBundleList();

	public static final ArrayList<Matcher<JSONObject>> collectionListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(owningCollection);
		}
	};

	public static final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(AllCommunityMatchers.firstCommunity(1,
					subCommunityListWithIdMatchers, emptyMatcherList(),
					collectionListWithIdMatchers));
		}
	};

	public static final Matcher<JSONObject> item = AllItemMatchers.firstItem(
			owningCollection, communityListMatchers, collectionListMatchers);

	public static final ArrayList<Matcher<JSONObject>> itemMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(item);
		}
	};
}
