/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.entities;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.dspace.browse.BrowseEngine;
import org.dspace.browse.BrowseException;
import org.dspace.browse.BrowseIndex;
import org.dspace.browse.BrowseInfo;
import org.dspace.browse.BrowserScope;
import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.sort.SortException;
import org.dspace.sort.SortOption;


/**
 * Basic class for representing the set of items which are recent submissions
 * to the archive
 * 
 * @author Richard Jones
 *
 */
public class RecentSubmissions
{
    /** logger */
    private static Logger log = Logger.getLogger(RecentSubmissions.class);


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
    public static RecentSubmissions getRecentSubmissions(DSpaceObject dso, Context context)
        throws BrowseException, SortException
    {
        // get our configuration
        String source = ConfigurationManager.getProperty("recent.submissions.sort-option");
        String count = ConfigurationManager.getProperty("recent.submissions.count");

        // prep our engine and scope
        BrowseEngine be = new BrowseEngine(context);
        BrowserScope bs = new BrowserScope(context);
        BrowseIndex bi = BrowseIndex.getItemBrowseIndex();

        // fill in the scope with the relevant gubbins
        bs.setBrowseIndex(bi);
        bs.setOrder(SortOption.DESCENDING);
        bs.setResultsPerPage(Integer.parseInt(count));
        bs.setBrowseContainer(dso);
        for (SortOption so : SortOption.getSortOptions())
        {
            if (so.getName().equals(source))
            {
                bs.setSortBy(so.getNumber());
            }
        }

        BrowseInfo results = be.browseMini(bs);

        Item[] items = results.getItemResults(context);

        RecentSubmissions rs = new RecentSubmissions(items);

        return rs;
    }
    
	/** The set of items being represented */
	private Item[] items;
	
	/**
	 * Construct a new RecentSubmissions object to represent the passed
	 * array of items
	 * 
	 * @param items
	 */
	public RecentSubmissions(Item[] items)
	{
		this.items = (Item[]) ArrayUtils.clone(items);
	}

	/**
	 * obtain the number of recent submissions available
	 * 
	 * @return	the number of items
	 */
	public int count()
	{
		return items.length;
	}
	
	/**
	 * Obtain the array of items
	 * 
	 * @return	an array of items
	 */
	public Item[] getRecentSubmissions()
	{
		return (Item[])ArrayUtils.clone(items);
	}
	
	/**
	 * Get the item which is in the i'th position.  Therefore i = 1 gets the
	 * most recently submitted item, while i = 3 gets the 3rd most recently
	 * submitted item
	 * 
	 * @param i		the position of the item to retrieve
	 * @return		the Item
	 */
	public Item getRecentSubmission(int i)
	{
		if (i < items.length)
		{
			return items[i];
		}
		else
		{
			return null;
		}
	}

}
