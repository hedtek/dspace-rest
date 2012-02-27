/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.data.community;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.content.Community;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.collection.Collections;

/**
 * Presents a detailed view of a community
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CommunityEntity extends LightCommunity {
    
    private static Logger log = Logger.getLogger(CommunityEntity.class);
    
    private final Boolean canEdit;
    private final String handle;
    private final int countItems;
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

    public CommunityEntity(Community community, int level, final DetailDepth depth) throws SQLException {
        this(community, level, depth, new ArrayList<Object>(), community.countItems());
    }

    CommunityEntity(final Community community, final int level, final DetailDepth depth, final List<Object> recentSubmissions,
            final int itemsCount) throws SQLException {
        super(community, Collections.collections(community, level + 1, depth));

        this.canEdit = community.canEditBoolean();
        this.handle = community.getHandle();
        this.countItems = itemsCount;


        this.short_description = community.getMetadata("short_description");
        this.introductory_text = community.getMetadata("introductory_text");
        this.copyright_text = community.getMetadata("copyright_text");
        this.side_bar_text = community.getMetadata("side_bar_text");

        this.logo = Communities.logo(community);
        
        this.recentSubmissions = recentSubmissions;
        
        // Only include full when above maximum depth
        final int nextLevel = level + 1;
        if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; next level " + nextLevel);

        this.subCommunities = Communities.subcommunities(community, nextLevel, depth);        
        this.parent = Communities.parent(nextLevel, depth, community);
    }
    
    public List<Entity> getSubCommunities() {
        return this.subCommunities;
    }

    public Object getParentCommunity() {
        return this.parent;
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
