/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers.fixtures;

import static uk.ac.jorum.integration.matchers.BundleMatchers.isBundle;
import static uk.ac.jorum.integration.matchers.BundleMatchers.isBundleWithId;

import java.util.ArrayList;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

public class AllBundleMatchers {

	public static final Matcher<JSONObject> firstBundle() {

		return isBundle(1, "ORIGINAL", AllItemMatchers.firstItemIdList(),
				AllBitstreamMatchers.firstBitstreamIdList());
	}
	
	public static final Matcher<JSONObject> firstBundleId() {
		return isBundleWithId(1);
	}

	public static final Matcher<JSONObject> secondBundle() {
		return isBundle(2, "LICENSE", AllItemMatchers.firstItemIdList(),
				AllBitstreamMatchers.firstBitstreamIdList());
	}
	
	public static final Matcher<JSONObject> secondBundleId() {
		return isBundleWithId(2);
	}

	public static final Matcher<JSONObject> thirdBundle() {
		return isBundle(3, "ORIGINAL", AllItemMatchers.secondItemIdList(),
				AllBitstreamMatchers.secondBitstreamIdList());
	}
	
	public static final Matcher<JSONObject> thirdBundleId() {
		return isBundleWithId(3);
	}

	public static final Matcher<JSONObject> fourthBundle() {
		return isBundle(4, "LICENSE", AllItemMatchers.secondItemIdList(),
				AllBitstreamMatchers.secondBitstreamIdList());
	}
	
	public static final Matcher<JSONObject> fourthBundleId() {
		return isBundleWithId(4);
	}

	public static ArrayList<Matcher<JSONObject>> firstBundleList() {
		return new ArrayList<Matcher<JSONObject>>() {
			{
				add(firstBundle());
			}
		};
	}

	public static ArrayList<Matcher<JSONObject>> firstBundleWithLicenceList() {
		return new ArrayList<Matcher<JSONObject>>() {
			{
				add(firstBundle());
				add(secondBundle());
			}
		};
	}

	public static ArrayList<Matcher<JSONObject>> firstBundleIdWithLicenceList() {
		return new ArrayList<Matcher<JSONObject>>() {
			{
				add(firstBundleId());
				add(secondBundleId());
			}
		};
	}
	
	public static ArrayList<Matcher<JSONObject>> secondBundleList() {
		return new ArrayList<Matcher<JSONObject>>() {
			{
				add(thirdBundle());
				add(fourthBundle());
			}
		};
	}

	public static ArrayList<Matcher<JSONObject>> secondBundleIdList() {
		return new ArrayList<Matcher<JSONObject>>() {
			{
				add(thirdBundleId());
				add(fourthBundleId());
			}
		};
	}
}
