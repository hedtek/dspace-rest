package uk.ac.jorum.integration.matchers.fixtures;

import static org.hamcrest.CoreMatchers.not;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionWithId;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class CollectionsInSiblingCommunities {


	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {

			{
				add(not(isCollectionWithId(6)));
			}
		};

	public static final ArrayList<Matcher<JSONObject>> communityListWithIdOnlyMatchers = new ArrayList<Matcher<JSONObject>>() {

			{
				add(not(isCommunityWithId(6)));
			}
		};
		
		
}
