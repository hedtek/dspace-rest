/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers.fixtures;
import static uk.ac.jorum.integration.matchers.SearchMatchers.isSearchResultInfoWith;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.*;

import java.io.Serializable;
import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class AllSearchMatchers {
	
	private static final Matcher<JSONObject> emptySearchResultInfo = isSearchResultInfoWith(-1, new int [0]);
	
	public static final ArrayList<Matcher<JSONObject>> emptySearchResultList = new ArrayList<Matcher<JSONObject>>(){{
		add(emptySearchResultInfo);
	}};
	
	private static final int[] resultIdsForJavaCommunityId = {2};
	private static final Matcher<JSONObject> searchResultInfoWithJavaCommunity = isSearchResultInfoWith(0, resultIdsForJavaCommunityId);

	public static final ArrayList<Matcher<JSONObject>> searchResultListWithJavaCommunity = new ArrayList<Matcher<JSONObject>>(){{
		add(searchResultInfoWithJavaCommunity);
		add(isCommunitySearchResultWithId(2, "Java Community"));
	}};
}
