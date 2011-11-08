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
			add(isCommunityWithId(4));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isCollectionWithId(1));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> communityListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isCommunityWithId(2));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> itemListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isItemWithId(1));
		}
	};
	public static final Matcher<JSONObject> owningCollection = isCollection(1,
			"Collection 1", "123456789/6",
			"Introductory Text for collection 1",
			"Short Description for Collection 1",
			"Side bar text for collection 1",
			"Copyright information for collection 1",
			"Licence for collection 1", "Provenance for collection 1", 1,
			communityListWithIdMatchers, itemListWithIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> bundleIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBundleWithId(1));
		}
	};

	public static final Matcher<JSONObject> submitter = AllUserMatchers
			.firstUser();

	public static final Matcher<JSONObject> bitstream = isBitstream(1,
			"firstUpload.txt", "Text", "text/plain", bundleIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> bitstreamMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(bitstream);
		}
	};

	public static final ArrayList<Matcher<JSONObject>> bitstreamIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isBitstreamWithId(1));
			add(isBitstreamWithId(2));
		}
	};

	public static final Matcher<JSONObject> bundle1 = isBundle(1, "ORIGINAL",
			itemListWithIdMatchers, bitstreamIdMatchers);
	public static final Matcher<JSONObject> bundle2 = isBundle(2, "LICENSE",
			itemListWithIdMatchers, bitstreamIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> bundleMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(bundle1);
			add(bundle2);
		}
	};

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

	public static final Matcher<JSONObject> item = isItem(1, "First Upload",
			"123456789/7", true, false, owningCollection, submitter,
			communityListMatchers, collectionListMatchers, bundleMatchers,
			bitstreamMatchers);

	public static final ArrayList<Matcher<JSONObject>> itemMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(item);
		}
	};
}
