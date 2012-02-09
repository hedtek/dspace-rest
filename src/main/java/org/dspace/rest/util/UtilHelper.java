/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.util;

import org.dspace.rest.providers.AbstractBaseProvider;

/**
 * Here are implemented and coupled helper methods used by several providers
 * especially in the custom actions
 * @see AbstractBaseProvider
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class UtilHelper {
    
    protected UtilHelper() {
    }
    // methods for getObjects
    public static final int PARENTS = 1;
    public static final int CHILDREN = 2;
    public static final int COLLECTIONS = 3;
    public static final int RECENT_SUBMISSIONS = 4;
    public static final int COMMUNITIES_INVOLVED = 5;
    public static final int ITEMS_INVOLVED = 6;
    public static final int ITEM_PERMISSION = 7;
    public static final int ITEM_IN_COMMUNITIES = 8;
    public static final int ITEM_IN_COLLECTIONS = 9;
    // sort methods for GenComparator
    public static final int SORT_ID = 210;
    public static final int SORT_NAME = 211;
    public static final int SORT_LASTMODIFIED = 212;
    public static final int SORT_SUBMITTER = 213;
    public static final int SORT_COUNT_ITEMS = 214;
    public static final int SORT_LANGUAGE = 215;
    public static final int SORT_LASTNAME = 216;
    public static final int SORT_FULL_NAME = 217;
    public static final int SORT_ID_REV = 310;
    public static final int SORT_NAME_REV = 311;
    public static final int SORT_LASTMODIFIED_REV = 312;
    public static final int SORT_SUBMITTER_REV = 313;
    public static final int SORT_COUNT_ITEMS_REV = 314;
    public static final int SORT_LANGUAGE_REV = 315;
    public static final int SORT_LASTNAME_REV = 316;
    public static final int SORT_FULL_NAME_REV = 317;
}
