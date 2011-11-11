package uk.ac.jorum.integration.matchers.fixtures;
import static uk.ac.jorum.integration.matchers.SearchMatchers.isSearchResultInfoWith;

import java.io.Serializable;
import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class AllSearchMatchers {
	
	public static final Matcher<Serializable> emptySearchResult = isSearchResultInfoWith(-1, new int [0]);
	
	public static final ArrayList<Matcher<Serializable>> emptySearchResultList = new ArrayList<Matcher<Serializable>>(){{
		add(emptySearchResult);
	}
	};
}
