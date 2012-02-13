/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.entities;

import org.dspace.browse.BrowseEngine;
import org.dspace.browse.BrowseException;
import org.dspace.browse.BrowseIndex;
import org.dspace.browse.BrowseInfo;
import org.dspace.browse.BrowserScope;
import org.dspace.content.Community;
import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.sort.SortException;
import org.dspace.sort.SortOption;


/**
 * Fetches sets of items which are recent submissions
 * to the archive
 * 
 * @author Richard Jones
 *
 */
public class RecentSubmissions
{
    /**
     * Obtain the recent submissions from the given container object.  This
     * method uses the configuration to determine which field and how many
     * items to retrieve from the DSpace Object.
     * 
     * If the object you pass in is not a Community or Collection (e.g. an Item
     * is a DSpaceObject which cannot be used here), an exception will be thrown
     * 
     * @param dso   DSpaceObject: Community or Collection
     * @return      The recently submitted items
     * @throws RecentSubmissionsException
     * @throws BrowseException 
     * @throws SortException 
     */
    public static Item[] getRecentSubmissions(final Community community, final Context context)
        throws BrowseException, SortException
    {

        final BrowseInfo results = new BrowseEngine(context).browseMini(scope(community, context));
        return results.getItemResults(context);
    }

    private static BrowserScope scope(final Community community, final Context context)
            throws BrowseException, SortException {
        final BrowserScope bs = new BrowserScope(context);
        final BrowseIndex bi = BrowseIndex.getItemBrowseIndex();

        // get our configuration
        final String source = ConfigurationManager.getProperty("recent.submissions.sort-option");
        final String count = ConfigurationManager.getProperty("recent.submissions.count");

        // fill in the scope
        bs.setBrowseIndex(bi);
        bs.setOrder(SortOption.DESCENDING);
        bs.setResultsPerPage(Integer.parseInt(count));
        bs.setBrowseContainer(community);
        for (SortOption so : SortOption.getSortOptions())
        {
            if (so.getName().equals(source))
            {
                bs.setSortBy(so.getNumber());
            }
        }
        return bs;
    }
}
