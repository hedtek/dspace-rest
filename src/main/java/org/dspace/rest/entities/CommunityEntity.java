/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.browse.BrowseEngine;
import org.dspace.browse.BrowseException;
import org.dspace.browse.BrowseIndex;
import org.dspace.browse.BrowseInfo;
import org.dspace.browse.BrowserScope;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.rest.data.Collections;
import org.dspace.rest.data.Communities;
import org.dspace.sort.SortException;
import org.dspace.sort.SortOption;

/**
 * Entity describing community, basic version
 * @see CommunityEntityId
 * @see Community
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CommunityEntity extends CommunityEntityId {
    
    private static List<Object> recentSubmissions(Context context, int level,
            final DetailDepth depth, final Community community) throws SQLException {
        List<Object> recentSubmissions = new ArrayList<Object>();
        try {
            Item[] recentItems = recentSubmissions(community, context);
            for (Item i : recentItems) {
                recentSubmissions.add(depth.includeFullDetails(level) ? new ItemEntity(i, level, depth) : new ItemEntityId(i));
            }
        } catch (BrowseException e) {
            log.debug("Failed to find recent submissions. Continuing with entity retreival.", e);
        } catch (SortException e) {
            log.debug("Failed to find recent submissions. Continuing with entity retreival.", e);
        }
        return recentSubmissions;
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
    private static Item[] recentSubmissions(final Community community, final Context context)
        throws BrowseException, SortException
    {
    
        final BrowseInfo results = new BrowseEngine(context).browseMini(scope(community, context));
        return results.getItemResults(context);
    }

    private static Object logo(Community community) throws SQLException {
        final Object logo;
        if (community.getLogo() == null) {
            logo = null;
        } else {
            logo = new BitstreamEntityId(community.getLogo());
        }
        return logo;
    }

    private static Logger log = Logger.getLogger(CommunityEntity.class);
    
    private final String name;
    private final Boolean canEdit;
    private final String handle;
    private final int type;
    private final int countItems;
    private final List<Entity> collections;
    private final List<Entity> subCommunities;
    private final List<Object> recentSubmissions;
 // Is this intentional?
    private final Object administrators = null;
    private final Object parent;
    private final String short_description;
    private final String introductory_text;
    private final String copyright_text;
    private final String side_bar_text;
    private final Object logo;

    public CommunityEntity(Community community, Context context, int level, final DetailDepth depth) throws SQLException {
            super(community.getID());
            this.canEdit = community.canEditBoolean();
            this.handle = community.getHandle();
            this.name = community.getName();
            this.type = community.getType();
            // Is this intentional?
            this.countItems = 0;

            this.short_description = community.getMetadata("short_description");
            this.introductory_text = community.getMetadata("introductory_text");
            this.copyright_text = community.getMetadata("copyright_text");
            this.side_bar_text = community.getMetadata("side_bar_text");
            
            this.logo = logo(community);
            

            // Only include full when above maximum depth
            final int nextLevel = level + 1;
            if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; next level " + nextLevel);
            
            this.collections = Collections.collections(community, nextLevel, depth);
            this.subCommunities = Communities.subcommunities(community, nextLevel, depth);
            this.parent = Communities.parent(nextLevel, depth, community);
            
            this.recentSubmissions = recentSubmissions(context, level, depth, community);
            
    }

    public CommunityEntity(Community community, int level, final DetailDepth depth) throws SQLException {
        this(community, level, depth, new ArrayList<Object>(), community.countItems());
    }


    public CommunityEntity(final Community community, final int level, final DetailDepth depth, final List<Object> recentSubmissions,
            final int itemsCount) throws SQLException {
        super(community.getID());

        this.canEdit = community.canEditBoolean();
        this.handle = community.getHandle();
        this.name = community.getName();
        this.type = community.getType();
        // See above
        this.countItems = itemsCount;


        this.short_description = community.getMetadata("short_description");
        this.introductory_text = community.getMetadata("introductory_text");
        this.copyright_text = community.getMetadata("copyright_text");
        this.side_bar_text = community.getMetadata("side_bar_text");

        this.logo = logo(community);
        
        this.recentSubmissions = recentSubmissions;
        
        // Only include full when above maximum depth
        final int nextLevel = level + 1;
        if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; next level " + nextLevel);

        this.collections = Collections.collections(community, nextLevel, depth);
        this.subCommunities = Communities.subcommunities(community, nextLevel, depth);        
        this.parent = Communities.parent(nextLevel, depth, community);
    }

    public List<Entity> getCollections() {
        return this.collections;
    }

    public List<Entity> getSubCommunities() {
        return this.subCommunities;
    }

    public Object getParentCommunity() {
        return this.parent;
    }

    public String getName() {
        return this.name;
    }

    public String getHandle() {
        return this.handle;
    }

    public boolean getCanEdit() {
        return this.canEdit;
    }

    public Object getLogo() {
        return this.logo;
    }

    public int getType() {
        return this.type;
    }

    public int getCountItems() {
        return this.countItems;
    }

    public List<?> getRecentSubmissions() {
        return this.recentSubmissions;
    }

    public Object getAdministrators() {
        return this.administrators;
    }

    public String getShortDescription() {
        return this.short_description;
    }

    public String getCopyrightText() {
        return this.copyright_text;
    }

    public String getSidebarText() {
        return this.side_bar_text;
    }

    public String getIntroductoryText() {
        return this.introductory_text;
    }
}
