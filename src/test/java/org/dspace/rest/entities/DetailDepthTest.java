package org.dspace.rest.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.dspace.rest.data.base.DetailDepth;
import org.junit.Test;

public class DetailDepthTest {

    @Test
    public void forAllIndexShouldNotIncludeFullForLevelTwo() {
        assertFalse(DetailDepth.FOR_ALL_INDEX.includeFullDetails(2));
    }

    @Test
    public void forAllIndexShouldIncludeFullForLevelOne() {
        assertTrue(DetailDepth.FOR_ALL_INDEX.includeFullDetails(1));
    }
    
    @Test
    public void forAllIndexShouldNotIncludeFullForLevelThree() {
        assertFalse(DetailDepth.FOR_ALL_INDEX.includeFullDetails(3));
    }

    @Test
    public void forAllIndexShouldNotIncludeFullForLevelFour() {
        assertFalse(DetailDepth.FOR_ALL_INDEX.includeFullDetails(4));
    }
}
