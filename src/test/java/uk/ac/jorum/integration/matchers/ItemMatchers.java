/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static uk.ac.jorum.integration.matchers.ContainsJSONKey.hasKey;
import static uk.ac.jorum.integration.matchers.EntityMatchers.cannotBeEdited;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasArray;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasId;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasKeys;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasName;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasSubObject;
import static uk.ac.jorum.integration.matchers.EntityMatchers.hasType;
import static uk.ac.jorum.integration.matchers.EntityMatchers.withValue;
import static uk.ac.jorum.integration.matchers.MetadataMatcher.isMetadataItem;

import java.util.ArrayList;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class ItemMatchers {

	static final ArrayList<Matcher<JSONObject>> metadataMatchers = new ArrayList<Matcher<JSONObject>>() {
		{
			add((isMetadataItem("contributor", "author")));
			add((isMetadataItem("date", "accessioned")));
			add((isMetadataItem("date", "available")));
			add((isMetadataItem("date", "issued")));
			add((isMetadataItem("identifier", "uri")));
			add((isMetadataItem("description", nullValue(JSONObject.class))));
			add((isMetadataItem("description", "provenance")));
			add((isMetadataItem("description", "abstract")));
			add((isMetadataItem("description", "sponsorship")));
			add((isMetadataItem("language", "iso")));
			add((isMetadataItem("subject", nullValue(JSONObject.class))));
			add((isMetadataItem("title", nullValue(JSONObject.class))));
		}
	};

	@Factory
	public static Matcher<JSONObject> isItem(int id, String name,
			boolean isArchived, boolean isWithdrawn,
			Matcher<JSONObject> owningCollection,
			Matcher<JSONObject> submitter,
			ArrayList<Matcher<JSONObject>> communities,
			ArrayList<Matcher<JSONObject>> collections,
			ArrayList<Matcher<JSONObject>> bundles,
			ArrayList<Matcher<JSONObject>> bitstreams) {
		String[] keys = { "lastModified" };

		return allOf(cannotBeEdited(), hasId(id), hasType(2), hasName(name),
				hasKey("isArchived", withValue(isArchived)),
				hasKey("isWithdrawn", withValue(isWithdrawn)),
				hasSubObject("owningCollection", owningCollection),
				hasSubObject("submitter", submitter),
				hasArray("metadata", metadataMatchers),
				hasArray("communities", communities),
				hasArray("collections", collections),
				hasArray("bundles", bundles),
				hasArray("bitstreams", bitstreams), hasKeys(keys));
	}

	@Factory
	public static Matcher<JSONObject> isItemWithId(int id) {
		return hasId(id);
	}

	@Factory
	public static ArrayList<Matcher<JSONObject>> getMetadataMatchers() {
		return metadataMatchers;
	}
	
  	@Factory
	public static Matcher<JSONObject> isItemSearchResult(int id,
			String name) {
		final String[] itemKeys = { "bitstreams", "bundles", "canEdit",
				"collections", "communities", "handle", "isArchived",
				"isWithdrawn", "lastModified", "metadata", "owningCollection",
				"submitter" };
		return allOf(hasId(id), 
					hasName(name), 
					hasKeys(itemKeys),
					hasType(2));
  	} 
}
