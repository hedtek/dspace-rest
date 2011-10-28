package uk.ac.jorum.integration.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import uk.ac.jorum.integration.HTTPStatusCode;

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
