/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package uk.ac.jorum.integration.matchers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class HasFileContent extends TypeSafeMatcher<String> {

	private String streamContent = "";
	
	private HasFileContent(String filePath) throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = "";
		while((line=reader.readLine())!=null) {
			streamContent += line;
		}
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Stream content should be: " + streamContent);
	}

	@Override
	public boolean matchesSafely(String bitstreamContent) {
		return streamContent.trim().equals(bitstreamContent.trim());
	}
	
	@Factory
	public static Matcher<String> hasFileContent(String filePath) throws FileNotFoundException, IOException {
		return new HasFileContent(filePath);
	}	
}
