package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollection;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionWithId;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunity;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class TwoCollectionsUnderTopLevelCommunity {
	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isCollectionWithId(1));
			add(isCollectionWithId(2));
		}
	};
	
	public static final ArrayList<Matcher<JSONObject>> subCommunityListWithIdMatchers = new ArrayList<Matcher<JSONObject>>() {{ add(isCommunityWithId(4)); }};
	
	public static final Matcher<JSONObject> parentCommunity = isCommunity(2,
		"Community no 1", "123456789/2",
		"Introductory text for community no 1",
		"Short description of community no 1",
		"Side bar text for community 1", "Copyright information", 0,
		null, subCommunityListWithIdMatchers, 
		emptyMatcherList(), collectionListWithIdOnlyMatchers);
	
	
	public static final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(parentCommunity);
		}
	};

	public static final Matcher<JSONObject> collectionOne = isCollection(1, "Collection 1", "123456789/6",
			"Introductory Text for collection 1",
			"Short Description for Collection 1",
			"Side bar text for collection 1",
			"Copyright information for collection 1",
			"Licence for collection 1",
			"Provenance for collection 1", 0, communityListMatchers, emptyMatcherList());

	public static final Matcher<JSONObject> collectionTwo = isCollection(2, "Collection 2", "123456789/7",
			"Introductory Text for collection 2",
			"Short Description for Collection 2",
			"Side bar text for collection 2",
			"Copyright text for collection 2",
			"Licence text for collection 2",
			"Provenance text for collection 2", 0, communityListMatchers, emptyMatcherList());
	
	
	public static final ArrayList<Matcher<JSONObject>> collectionListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(collectionOne);
			add(collectionTwo);
		}
	};
	
}
