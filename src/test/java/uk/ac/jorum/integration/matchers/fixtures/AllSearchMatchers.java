/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers.fixtures;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionSearchResult;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionWithId;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunitySearchResult;
import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItemSearchResult;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItemWithId;
import static uk.ac.jorum.integration.matchers.SearchMatchers.isHarvestResultInfoWith;
import static uk.ac.jorum.integration.matchers.SearchMatchers.isSearchResultInfoWith;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class AllSearchMatchers {
	
	private static final Matcher<JSONObject> emptySearchResultInfo = isSearchResultInfoWith(0, new int [0]);
	
	public static final ArrayList<Matcher<JSONObject>> emptySearchResultList = new ArrayList<Matcher<JSONObject>>(){{
		add(emptySearchResultInfo);
	}};
	
	private static final int[] resultIdsForJavaCommunity = {2};
	private static final Matcher<JSONObject> searchResultInfoWithJavaCommunity = isSearchResultInfoWith(1, resultIdsForJavaCommunity);

	public static final ArrayList<Matcher<JSONObject>> searchResultListWithJavaCommunity = new ArrayList<Matcher<JSONObject>>(){{
		add(searchResultInfoWithJavaCommunity);
		add(isCommunitySearchResult(2, "Java Community"));
	}};
	
	public static final ArrayList<Matcher<JSONObject>> searchResultListWithJavaCommunityId = new ArrayList<Matcher<JSONObject>>(){{
		add(searchResultInfoWithJavaCommunity);
		add(isCommunityWithId(2));
	}};
	
	private static final int[] resultIdsForSinatraCollection = {2, 5};
	private static final Matcher<JSONObject> searchResultInfoWithSinatraCollection = isSearchResultInfoWith(2, resultIdsForSinatraCollection);

	public static final ArrayList<Matcher<JSONObject>> searchResultListWithSinatraCollection = new ArrayList<Matcher<JSONObject>>(){{
		add(searchResultInfoWithSinatraCollection);
		add(isCollectionSearchResult(2, "Sinatra"));
		add(isItemSearchResult(5, "sinatra tutorials by fankie"));
	}};
	
	public static final ArrayList<Matcher<JSONObject>> searchResultListWithSinatraCollectionId = new ArrayList<Matcher<JSONObject>>(){{
		add(searchResultInfoWithSinatraCollection);
		add(isCollectionWithId(2));
		add(isItemWithId(5));
	}};

	private static final int[] resultIdsForProductBacklogItem = { 18 };
	private static final Matcher<JSONObject> searchResultInfoWithProductBacklogItem = isSearchResultInfoWith(1, resultIdsForProductBacklogItem);

	public static final ArrayList<Matcher<JSONObject>> searchResultListWithProductBacklogItem = new ArrayList<Matcher<JSONObject>>(){{
		add(searchResultInfoWithProductBacklogItem);
		add(isItemSearchResult(18, "Product Backlog book"));
	}};
	
	public static final ArrayList<Matcher<JSONObject>> searchResultListWithProductBacklogItemId = new ArrayList<Matcher<JSONObject>>(){{
		add(searchResultInfoWithProductBacklogItem);
		add(isItemWithId(18));
	}};
	
	private static final int[] resultIdsForTutorialItems = { 5,	3, 7, 10, 14, 19, 23, 24 };
	private static final Matcher<JSONObject> searchResultInfoWithTutorialItems = isSearchResultInfoWith(8, resultIdsForTutorialItems);
	
	public static final ArrayList<Matcher<JSONObject>> searchResultListWithTutorialItem = new ArrayList<Matcher<JSONObject>>(){{
		add(searchResultInfoWithTutorialItems);
		add(isItemSearchResult(5, "sinatra tutorials by fankie"));
		add(isItemSearchResult(3, "rails tutorial"));
		add(isItemSearchResult(7, "guides and tutorials"));
		add(isItemSearchResult(10, "Hibernate tutorials"));
		add(isItemSearchResult(14, "Maven tutorials"));
		add(isItemSearchResult(19, "Scrum Tutorials"));
		add(isItemSearchResult(23, "XP Tutorial"));
		add(isItemSearchResult(24, "Waterfall Model Tutorial"));
	}};
	
	private static final int[] resultIdsForScrumTutorialItems = { 19 };
	private static final Matcher<JSONObject> searchResultInfoWithScrumTutorialItems = isSearchResultInfoWith(1, resultIdsForScrumTutorialItems);

	public static final ArrayList<Matcher<JSONObject>> searchResultListWithScrumTutorialItem = new ArrayList<Matcher<JSONObject>>(){{
		add(searchResultInfoWithScrumTutorialItems);
		add(isItemSearchResult(19, "Scrum Tutorials"));
	}};
	

	private static final int[] resultIdsForPomSprintItems = { 12, 17 };
	private static final Matcher<JSONObject> searchResultInfoWithPomSprintItems = isSearchResultInfoWith(2, resultIdsForPomSprintItems);

	public static final ArrayList<Matcher<JSONObject>> searchResultListWithPomSprintItems = new ArrayList<Matcher<JSONObject>>(){{
		add(searchResultInfoWithPomSprintItems);
		add(isItemSearchResult(12, "Check out my POM"));
		add(isItemSearchResult(17, "Jamaican Sprints"));
	}};
	

	public static final ArrayList<Matcher<JSONObject>> harvestResultListWithAllItems = new ArrayList<Matcher<JSONObject>>(){{
		add(isHarvestResultInfoWith(22));
		add(isItemSearchResult(1, "rails guide"));
		add(isItemSearchResult(2, "rails 3 in action"));
		add(isItemSearchResult(3, "rails tutorial"));
		add(isItemSearchResult(4, "agile web development with rails"));
		add(isItemSearchResult(5, "sinatra tutorials by fankie"));
		add(isItemSearchResult(6, "ioc and di"));
		add(isItemSearchResult(7, "guides and tutorials"));
		add(isItemSearchResult(8, "OMG its ORM!!!"));
		add(isItemSearchResult(9, "JDBC Zuck's"));
		add(isItemSearchResult(10, "Hibernate tutorials"));
		add(isItemSearchResult(11, "SQL and Driver"));
		add(isItemSearchResult(12, "Check out my POM"));
		add(isItemSearchResult(13, "Dependencies guide"));
		add(isItemSearchResult(14, "Maven tutorials"));
		add(isItemSearchResult(16, "Scrum Master Certification Guide"));
		add(isItemSearchResult(17, "Jamaican Sprints"));
		add(isItemSearchResult(18, "Product Backlog book"));
		add(isItemSearchResult(19, "Scrum Tutorials"));
		add(isItemSearchResult(21, "Programming in Pairs"));
		add(isItemSearchResult(22, "TDD by example"));
		add(isItemSearchResult(23, "XP Tutorial"));
		add(isItemSearchResult(24, "Waterfall Model Tutorial"));
	}};
	
	public static final ArrayList<Matcher<JSONObject>> harvestResultListWithAllItemsIdOnly = new ArrayList<Matcher<JSONObject>>(){{
		add(isHarvestResultInfoWith(22));
		add(isItemWithId(1));
		add(isItemWithId(2));
		add(isItemWithId(3));
		add(isItemWithId(4));
		add(isItemWithId(5));
		add(isItemWithId(6));
		add(isItemWithId(7));
		add(isItemWithId(8));
		add(isItemWithId(9));
		add(isItemWithId(10));
		add(isItemWithId(11));
		add(isItemWithId(12));
		add(isItemWithId(13));
		add(isItemWithId(14));
		add(isItemWithId(16));
		add(isItemWithId(17));
		add(isItemWithId(18));
		add(isItemWithId(19));
		add(isItemWithId(21));
		add(isItemWithId(22));
		add(isItemWithId(23));
		add(isItemWithId(24));
	}};
	
	
	public static final ArrayList<Matcher<JSONObject>> emptyHarvestResultList = new ArrayList<Matcher<JSONObject>>(){{
		add(isHarvestResultInfoWith(0));
	}};
	
	

	public static final ArrayList<Matcher<JSONObject>> harvestResultListWithItemsAfterTenth = new ArrayList<Matcher<JSONObject>>(){{
		add(isHarvestResultInfoWith(18));
		add(isItemSearchResult(2, "rails 3 in action"));
		add(isItemSearchResult(3, "rails tutorial"));
		add(isItemSearchResult(5, "sinatra tutorials by fankie"));
		add(isItemSearchResult(6, "ioc and di"));
		add(isItemSearchResult(7, "guides and tutorials"));
		add(isItemSearchResult(9, "JDBC Zuck's"));
		add(isItemSearchResult(10, "Hibernate tutorials"));
		add(isItemSearchResult(12, "Check out my POM"));
		add(isItemSearchResult(13, "Dependencies guide"));
		add(isItemSearchResult(14, "Maven tutorials"));
		add(isItemSearchResult(16, "Scrum Master Certification Guide"));
		add(isItemSearchResult(17, "Jamaican Sprints"));
		add(isItemSearchResult(18, "Product Backlog book"));
		add(isItemSearchResult(19, "Scrum Tutorials"));
		add(isItemSearchResult(21, "Programming in Pairs"));
		add(isItemSearchResult(22, "TDD by example"));
		add(isItemSearchResult(23, "XP Tutorial"));
		add(isItemSearchResult(24, "Waterfall Model Tutorial"));
	}};
	
	public static final ArrayList<Matcher<JSONObject>> harvestResultListWithItemsBeforeTenth = new ArrayList<Matcher<JSONObject>>(){{
		add(isHarvestResultInfoWith(4));
		add(isItemSearchResult(1, "rails guide"));
		add(isItemSearchResult(4, "agile web development with rails"));
		add(isItemSearchResult(8, "OMG its ORM!!!"));
		add(isItemSearchResult(11, "SQL and Driver"));
	}};
	
	public static final ArrayList<Matcher<JSONObject>> harvestResultListWithItemsBetweenNinthTenth = new ArrayList<Matcher<JSONObject>>(){{
		add(isHarvestResultInfoWith(2));
		add(isItemSearchResult(1, "rails guide"));
		add(isItemSearchResult(8, "OMG its ORM!!!"));
	}};

	public static final ArrayList<Matcher<JSONObject>> harvestResultListWithinRubyCommunity = new ArrayList<Matcher<JSONObject>>(){{
		add(isHarvestResultInfoWith(5));
		add(isItemSearchResult(1, "rails guide"));
		add(isItemSearchResult(2, "rails 3 in action"));
		add(isItemSearchResult(3, "rails tutorial"));
		add(isItemSearchResult(4, "agile web development with rails"));
		add(isItemSearchResult(5, "sinatra tutorials by fankie"));
	}};
	
	public static final ArrayList<Matcher<JSONObject>> harvestResultListWithinRubyCommunityBetweenNinthTenth = new ArrayList<Matcher<JSONObject>>(){{
		add(isHarvestResultInfoWith(1));
		add(isItemSearchResult(1, "rails guide"));
	}};
	
	public static final ArrayList<Matcher<JSONObject>> harvestResultListWithinRailsCollection = new ArrayList<Matcher<JSONObject>>(){{
		add(isHarvestResultInfoWith(4));
		add(isItemSearchResult(1, "rails guide"));
		add(isItemSearchResult(2, "rails 3 in action"));
		add(isItemSearchResult(3, "rails tutorial"));
		add(isItemSearchResult(4, "agile web development with rails"));
	}};
	
	public static final ArrayList<Matcher<JSONObject>> harvestResultListWithinRailsCollectionBetweenNinthTenth = harvestResultListWithinRubyCommunityBetweenNinthTenth;
}
