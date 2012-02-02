/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.CommunityMatchers.isCommunityWithId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.emptyMatcherList;
import static uk.ac.jorum.integration.matchers.ItemMatchers.isItemWithId;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class TwoItemsInTwoDifferentCollectionsUnderSameCommunity {

	public static final ArrayList<Matcher<JSONObject>> subCommunityListWithIdMatchers = TwoCollectionsUnderTopLevelCommunity.subCommunityListWithIdMatchers;
	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdMatchers = TwoCollectionsUnderTopLevelCommunity.collectionListWithIdOnlyMatchers;

	public static final Matcher<JSONObject> parentCommunity = AllCommunityMatchers
			.firstCommunity(2, subCommunityListWithIdMatchers,
					emptyMatcherList(), collectionListWithIdMatchers);

	public static final ArrayList<Matcher<JSONObject>> communityListMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(parentCommunity);
		}
	};

	public static final ArrayList<Matcher<JSONObject>> communityListIdMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isCommunityWithId(2));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> itemIdMatchersFirstCollection = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isItemWithId(1));
		}
	};

	public static final ArrayList<Matcher<JSONObject>> itemIdMatchersSecondCollection = new ArrayList<Matcher<JSONObject>>() {
		{
			add(isItemWithId(2));
		}
	};

	public static final Matcher<JSONObject> owningCollectionForFirstItem = AllCollectionMatchers
			.firstCollection(1, communityListIdMatchers,
					itemIdMatchersFirstCollection);

	public static final Matcher<JSONObject> owningCollectionForSecondItem = AllCollectionMatchers
			.secondCollection(1, communityListIdMatchers,
					itemIdMatchersSecondCollection);

	public static final ArrayList<Matcher<JSONObject>> collectionListMatchersForFirstItem = new ArrayList<Matcher<JSONObject>>() {
		{
			add(owningCollectionForFirstItem);
		}
	};

	public static final ArrayList<Matcher<JSONObject>> collectionListMatchersForSecondItem = new ArrayList<Matcher<JSONObject>>() {
		{
			add(owningCollectionForSecondItem);
		}
	};

	public static final Matcher<JSONObject> firstItem = AllItemMatchers
			.firstItem(owningCollectionForFirstItem, communityListMatchers,
					collectionListMatchersForFirstItem);

	public static final Matcher<JSONObject> secondItem = AllItemMatchers
			.secondItem(owningCollectionForSecondItem, communityListMatchers,
					collectionListMatchersForSecondItem);

	public static final ArrayList<Matcher<JSONObject>> itemMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add(firstItem);
			add(secondItem);
		}
	};
}
