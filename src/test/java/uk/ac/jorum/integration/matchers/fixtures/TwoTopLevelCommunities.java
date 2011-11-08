package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunity;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class TwoTopLevelCommunities {
	public static final Matcher<JSONObject> topCommunityOne = isCommunity(2,
			"Community no 1", "123456789/2",
			"Introductory text for community no 1",
			"Short description of community no 1",
			"Side bar text for community 1", "Copyright information", 0,
			null, emptyMatcherList(), emptyMatcherList(),
			emptyMatcherList());
	
	public static final Matcher<JSONObject> topCommunityTwo = isCommunity(3,
			"Top level community no 2", "123456789/3",
			null, null,null, null, 0, null, 
			emptyMatcherList(), emptyMatcherList(),	emptyMatcherList());
	
	public static final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(topCommunityOne);
			add(topCommunityTwo);
		}
	};
}
