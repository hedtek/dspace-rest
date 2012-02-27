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
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.collection.Collections;
import org.dspace.rest.entities.BitstreamEntityId;

/**
 * Entity describing community, basic version
 * @see CommunityEntityId
 * @see Community
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CommunityEntity extends CommunityEntityId {
    
    private static Object logo(Community community) throws SQLException {
        final Object logo;
        if (community.getLogo() == null) {
            logo = null;
        } else {
            logo = new BitstreamEntityId(community.getLogo());
        }
        return logo;
    }

    static Logger log = Logger.getLogger(CommunityEntity.class);
    
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

    public CommunityEntity(Community community, int level, final DetailDepth depth) throws SQLException {
        this(community, level, depth, new ArrayList<Object>(), community.countItems());
    }

    CommunityEntity(final Community community, final int level, final DetailDepth depth, final List<Object> recentSubmissions,
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
