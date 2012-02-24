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

import org.apache.commons.collections.CollectionUtils;
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
import org.dspace.sort.SortException;
import org.dspace.sort.SortOption;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityFieldRequired;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityTitle;

/**
 * Entity describing community, basic version
 * @see CommunityEntityId
 * @see Community
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CommunityEntity extends CommunityEntityId {
    
    private static List<Object> recentSubmissions(Context context, int level,
            final DetailDepth depth, final Community community,
            final boolean includeFullNextLevel) throws SQLException {
        List<Object> recentSubmissions = new ArrayList<Object>();
        try {
            Item[] recentItems = recentSubmissions(community, context);
            for (Item i : recentItems) {
                recentSubmissions.add(includeFullNextLevel ? new ItemEntity(i, level, depth) : new ItemEntityId(i));
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

    private static Object parent(int level, final DetailDepth depth,
            final Community community, final boolean includeFullNextLevel)
            throws SQLException {
        Object parent;
        try {
            Community parentCommunity = community.getParentCommunity();
            if(parentCommunity == null) {
                parent = null;
            } else {
                if(includeFullNextLevel) {
                    parent = new CommunityEntity(parentCommunity, level, depth);
                } else {
                    parent = new CommunityEntityId(parentCommunity);
                }
            }

        } catch (NullPointerException ex) {
            parent = null;
        }
        return parent;
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

    private static List<Object> subcommunities(Community community, int level,
            final DetailDepth depth, final boolean includeFullNextLevel)
            throws SQLException {
        List<Object> subCommunities = new ArrayList<Object>();
        Community[] coms = community.getSubcommunities();
        for (Community c : coms) {
            subCommunities.add(includeFullNextLevel ? new CommunityEntity(c, level, depth) : new CommunityEntityId(c));
        }
        return subCommunities;
    }
    
    private static Logger log = Logger.getLogger(CommunityEntity.class);
    
    private String name;
    private Boolean canEdit;
    private String handle;
    private int type;
    private int countItems;
    private List<Entity> collections = new ArrayList<Entity>();
    private List<Object> subCommunities = new ArrayList<Object>();
    private List<Object> recentSubmissions = new ArrayList<Object>();
    private Object administrators;
    private Object parent;
    private String short_description, introductory_text, copyright_text, side_bar_text;
    private Object logo;

    public CommunityEntity(Community community, Context context, int level, final DetailDepth depth) throws SQLException {
            super(community.getID());
            this.canEdit = community.canEditBoolean();
            this.handle = community.getHandle();
            this.name = community.getName();
            this.type = community.getType();

            // Only include full when above maximum depth
            final boolean includeFullNextLevel = depth.includeFullDetails(++level);
            if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; include full? " + includeFullNextLevel + "; next level " + level);
            
            
            this.short_description = community.getMetadata("short_description");
            this.introductory_text = community.getMetadata("introductory_text");
            this.copyright_text = community.getMetadata("copyright_text");
            this.side_bar_text = community.getMetadata("side_bar_text");

            this.logo = logo(community);
            this.collections = Collections.collections(community, level, depth, includeFullNextLevel);
            this.subCommunities = subcommunities(community, level, depth, includeFullNextLevel);
            this.parent = parent(level, depth, community, includeFullNextLevel);
            
            this.recentSubmissions = recentSubmissions(context, level, depth, community, includeFullNextLevel);
    }

    public CommunityEntity(Community community, int level, final DetailDepth depth) throws SQLException {
        this(community, level, depth, new ArrayList<Object>());
    }


    public CommunityEntity(Community community, int level, final DetailDepth depth, final List<Object> recentSubmissions) throws SQLException {
        super(community.getID());
        // Only include full when above maximum depth
        final boolean includeFullNextLevel = depth.includeFullDetails(++level);
        if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; include full? " + includeFullNextLevel + "; next level " + level);

        this.canEdit = community.canEditBoolean();
        this.handle = community.getHandle();
        this.name = community.getName();
        this.type = community.getType();
        this.countItems = community.countItems();
        this.short_description = community.getMetadata("short_description");
        this.introductory_text = community.getMetadata("introductory_text");
        this.copyright_text = community.getMetadata("copyright_text");
        this.side_bar_text = community.getMetadata("side_bar_text");

        this.logo = logo(community);
        this.collections = Collections.collections(community, level, depth, includeFullNextLevel);
        this.subCommunities = subcommunities(community, level, depth, includeFullNextLevel);        
        this.parent = parent(level, depth, community, includeFullNextLevel);
        this.recentSubmissions = recentSubmissions;
    }

    

    public List<Entity> getCollections() {
        return this.collections;
    }

    public List<?> getSubCommunities() {
        return this.subCommunities;
    }

    public Object getParentCommunity() {
        return this.parent;
    }

    public String getName() {
        return this.name;
    }

    @EntityTitle
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
