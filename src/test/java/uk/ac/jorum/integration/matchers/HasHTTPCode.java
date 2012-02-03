/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers;

import org.dspace.rest.diagnose.HTTPStatusCode;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;


public class HasHTTPCode extends TypeSafeMatcher<Integer> {

	private HTTPStatusCode code;
	@Override
	public void describeTo(Description description) {
		description.appendText("Response code should be: " + code);
	}

	@Override
	public boolean matchesSafely(Integer item) {
		return code.getCode() == item;
	}
	
	private HasHTTPCode(HTTPStatusCode code) {
        this.code = code;
    }
	
	@Factory
	public static Matcher<Integer> hasHTTPCode( HTTPStatusCode code ) {
	    return new HasHTTPCode(code);
	}

}
