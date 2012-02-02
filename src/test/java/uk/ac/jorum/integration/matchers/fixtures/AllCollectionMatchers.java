/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollection;
import static uk.ac.jorum.integration.matchers.CollectionMatchers.isCollectionWithId;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class AllCollectionMatchers {

	public static Matcher<JSONObject> firstCollection(int itemCount,
			ArrayList<Matcher<JSONObject>> communities,
			ArrayList<Matcher<JSONObject>> items) {
		return isCollection(1, "Collection 1", "123456789/6",
				"Introductory Text for collection 1",
				"Short Description for Collection 1",
				"Side bar text for collection 1",
				"Copyright information for collection 1",
				"Licence for collection 1", "Provenance for collection 1",
				itemCount, communities, items);
	}
	
	public static Matcher<JSONObject> firstCollectionId() {
		return isCollectionWithId(1);
	}
	
	public static Matcher<JSONObject> secondCollection(int itemCount,
			ArrayList<Matcher<JSONObject>> communities,
			ArrayList<Matcher<JSONObject>> items ) {
		return isCollection(2,
				"Collection 2", "123456789/7",
				"Introductory Text for collection 2",
				"Short Description for Collection 2",
				"Side bar text for collection 2",
				"Copyright text for collection 2", "Licence text for collection 2",
				"Provenance text for collection 2", itemCount, communities,
				items);
	}
	
	public static Matcher<JSONObject> secondCollectionId() {
		return isCollectionWithId(2);
	}
	
	public static Matcher<JSONObject> thirdCollection(int itemCount,
			ArrayList<Matcher<JSONObject>> communities,
			ArrayList<Matcher<JSONObject>> items ) {
		
		return isCollection(
				1, "Collection 1", "123456789/4", "Collection 1", "Collection 1",
				"Collection 1", "Collection 1", "Collection 1", "Collection 1", itemCount,
				communities, items);
	}
	
	
	public static Matcher<JSONObject> fourthCollection(int itemCount,
			ArrayList<Matcher<JSONObject>> communities,
			ArrayList<Matcher<JSONObject>> items) {

		return isCollection(2, "Collection 2", "123456789/5", "Collection 2",
				"Collection 2", "Collection 2", "Collection 2", "Collection 2",
				"Collection 2", itemCount, communities, items);
	}

	public static final ArrayList<Matcher<JSONObject>> collectionListWithIdMatchers() {
		return new ArrayList<Matcher<JSONObject>>() {
			{
				add(AllCollectionMatchers.firstCollectionId());
			}
		};
	}
}
