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
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
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

    private static Logger log = Logger.getLogger(CommunityEntity.class);
    
    @EntityId
    private int id;
    @EntityFieldRequired
    public String name;
    @EntityFieldRequired
    private Boolean canEdit;
    private String handle;
    private int type;
    private int countItems;
    private List<Object> collections = new ArrayList<Object>();
    private List<Object> subCommunities = new ArrayList<Object>();
    private List<Object> recentSubmissions = new ArrayList<Object>();
    private Object administrators;
    private Object parent;
    private String short_description, introductory_text, copyright_text, side_bar_text;
    private Object logo;

    public CommunityEntity(String uid, Context context, int level, final DetailDepth depth) throws SQLException {
        log.debug("Creating community entity.");
        try {
            final Community community = Community.find(context, Integer.parseInt(uid));
            this.id = community.getID();
            this.canEdit = community.canEditBoolean();
            this.handle = community.getHandle();
            this.name = community.getName();
            this.type = community.getType();

            // Only include full when above maximum depth
            final boolean includeFullNextLevel = depth.includeFullDetails(++level);
            if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; include full? " + includeFullNextLevel + "; next level " + level);
            
            if (community.getLogo() != null)
            //this.logo = includeFull ? new BitstreamEntity(Integer.toString(res.getLogo().getID()), context, level, uparams) : new BitstreamEntityId(Integer.toString(res.getLogo().getID()), context);
            this.logo = new BitstreamEntityId(Integer.toString(community.getLogo().getID()), context);
            this.short_description = community.getMetadata("short_description");
            this.introductory_text = community.getMetadata("introductory_text");
            this.copyright_text = community.getMetadata("copyright_text");
            this.side_bar_text = community.getMetadata("side_bar_text");

                Collection[] cols = community.getCollections();
            for (Collection c : cols) {
                this.collections.add(includeFullNextLevel ? new CollectionEntity(c, level, depth) : new CollectionEntityId(c));
            }
            
            Community[] coms = community.getSubcommunities();
            for (Community c : coms) {
                this.subCommunities.add(includeFullNextLevel ? new CommunityEntity(c, level, depth) : new CommunityEntityId(c));
            }
            try {
                Community parentCommunity = community.getParentCommunity();
                if(parentCommunity == null) {
                    this.parent = null;
                } else {
                    if(includeFullNextLevel) {
                        this.parent = new CommunityEntity(parentCommunity, level, depth);
                    } else {
                        this.parent = new CommunityEntityId(parentCommunity);
                    }
                }

            } catch (NullPointerException ex) {
                this.parent = null;
            }
            try {
                Item[] recentSubmissions = recentSubmissions(community, context);
                for (Item i : recentSubmissions) {
                    this.recentSubmissions.add(includeFullNextLevel ? new ItemEntity(i, level, depth) : new ItemEntityId(i));
                }
            } catch (BrowseException e) {
                log.debug("Failed to find recent submissions. Continuing with entity retreival.", e);
            } catch (SortException e) {
                log.debug("Failed to find recent submissions. Continuing with entity retreival.", e);
            }
        } catch (NumberFormatException ex) {
        }
    }

    public CommunityEntity(Community community, int level, final DetailDepth depth) throws SQLException {
        log.debug("Creating community");
        // Only include full when above maximum depth
        final boolean includeFullNextLevel = depth.includeFullDetails(++level);
        if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; include full? " + includeFullNextLevel + "; next level " + level);

        this.canEdit = community.canEditBoolean();
        this.handle = community.getHandle();
        this.name = community.getName();
        this.type = community.getType();
        this.id = community.getID();
        this.countItems = community.countItems();
        this.short_description = community.getMetadata("short_description");
        this.introductory_text = community.getMetadata("introductory_text");
        this.copyright_text = community.getMetadata("copyright_text");
        this.side_bar_text = community.getMetadata("side_bar_text");

        if (community.getLogo() != null)
        //this.logo = includeFull ? new BitstreamEntity(community.getLogo(), level, uparams) : new BitstreamEntityId(community.getLogo());
        this.logo = new BitstreamEntityId(community.getLogo());
        
        Collection[] cols = community.getCollections();
        for (Collection c : cols) {
            this.collections.add(includeFullNextLevel ? new CollectionEntity(c, level, depth) : new CollectionEntityId(c));
        }
        Community[] coms = community.getSubcommunities();
        for (Community c : coms) {
            this.subCommunities.add(includeFullNextLevel ? new CommunityEntity(c, level, depth) : new CommunityEntityId(c));
        }
        try {
            this.parent = includeFullNextLevel ? new CommunityEntity(community.getParentCommunity(), level, depth) : new CommunityEntityId(community.getParentCommunity());
        } catch (NullPointerException ne) {
            this.parent = null;
        }
    }

    public CommunityEntity() {
        // check calling package/class in order to prevent chaining
        boolean includeFull = false;
        this.canEdit = true;
        this.handle = "123456789/0";
        this.name = "Community Name";
        this.type = 5;
        this.id = 6;
        this.countItems = 1001;
        this.collections.add(includeFull ? new CollectionEntity() : new CollectionEntityId());
        this.subCommunities.add(includeFull ? new CommunityEntity() : new CommunityEntityId());
        this.parent = includeFull ? new CommunityEntity() : new CommunityEntityId();
        this.short_description = "short description";
        this.introductory_text = "introductory_text";
        this.copyright_text = "copyright_text";
        this.side_bar_text = "side_bar_text";
    }

    public List<?> getCollections() {
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
        //return null;
    }

    @Override
    public int getId() {
        return this.id;
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

    @Override
    public String toString() {
        return "id:" + this.id + ", stuff.....";
    }

    private BrowserScope scope(final Community community, final Context context)
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
    private Item[] recentSubmissions(final Community community, final Context context)
        throws BrowseException, SortException
    {
    
        final BrowseInfo results = new BrowseEngine(context).browseMini(scope(community, context));
        return results.getItemResults(context);
    }
}
