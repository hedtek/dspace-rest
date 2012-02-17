/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasKeys;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasResultsCount;

import java.util.ArrayList;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class SearchMatchers {
	@Factory
	public static Matcher<JSONObject> isSearchResultInfoWith(int resultsCount, int[] resultIds) {
		String[] searchResultKeys = { "resultHandles", "resultTypes" };
		ArrayList<Matcher<Long>> ids = new ArrayList<Matcher<Long>>();
		for(int id:resultIds) {
			ids.add(equalTo(new Long(id)));
		}
		return allOf(	        
	        hasResultsCount(resultsCount),
	        hasArray("resultIDs", ids),
	        hasKeys(searchResultKeys)
        );
	}
	
	@Factory
	public static Matcher<JSONObject> isHarvestResultInfoWith(int resultsCount) {
		return allOf(	        
	        hasId(0),
	        hasResultsCount(resultsCount)
        );
	}
}
