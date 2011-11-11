package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasKeys;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasResultsCount;

import java.io.Serializable;
import java.util.ArrayList;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class SearchMatchers {
	@Factory
	public static Matcher<Serializable> isSearchResultInfoWith(int resultsCount, int[] resultIds) {
		String[] searchResultKeys = { "id", "name", "resultHandles", "resultTypes" };
		ArrayList<Matcher<Long>> ids = new ArrayList<Matcher<Long>>();
		for(int id:resultIds) {
			ids.add(equalTo(new Long(id)));
		}
		return allOf(	        
	        hasId(0),
	        hasResultsCount(resultsCount),
	        hasArray("resultIDs", ids),
	        hasKeys(searchResultKeys)
        );
	}
}
