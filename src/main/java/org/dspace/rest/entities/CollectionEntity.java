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
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.ItemIterator;
import org.dspace.core.Context;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityFieldRequired;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;

/**
 * Entity describing collection
 * @see CollectionEntityId
 * @see Collection
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CollectionEntity {

    private static Logger log = Logger.getLogger(CollectionEntity.class);
    
    @EntityId
    private int id;
    @EntityFieldRequired
    private String name;
    @EntityFieldRequired
    private Boolean canEdit;
    private String handle, licence;
    private int type;
    private int countItems;
    private List<Object> items = new ArrayList<Object>();
    private List<Object> communities = new ArrayList<Object>();
    private String short_description;
    private String intro_text;
    private String copyright_text;
    private String sidebar_text;
    private String provenance;
    private Object logo;

    public CollectionEntity(final String uid, final Context context, final int level, final DetailDepth depth) throws SQLException {
        this(Collection.find(context, Integer.parseInt(uid)), level, depth);
    }

    public CollectionEntity(final Collection collection, int level, final DetailDepth depth) throws SQLException {
        log.debug("Creating collection entity.");
        // Only include full when above maximum depth
        final boolean includeFullNextLevel = depth.includeFullDetails(++level);
        if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; include full? " + includeFullNextLevel + "; next level " + level);
        loadCollectionData(collection);
        
        final ItemIterator i = collection.getAllItems();
        this.items = ItemBuilder.builder(!includeFullNextLevel, depth).build(i, level);
        this.countItems = items.size();

        for (Community c : collection.getCommunities()) {
            communities.add(includeFullNextLevel ? new CommunityEntity(c, level, depth) : new CommunityEntityId(c));
        }
    }
    
    private void loadCollectionData(Collection collection) throws SQLException {
    	this.id = collection.getID();
        this.canEdit = collection.canEditBoolean();
        this.handle = collection.getHandle();
        this.name = collection.getName();
        this.type = collection.getType();
        this.licence = collection.getLicense();
        this.short_description = collection.getMetadata("short_description");
        this.intro_text = collection.getMetadata("introductory_text");
        this.copyright_text = collection.getMetadata("copyright_text");
        this.sidebar_text = collection.getMetadata("side_bar_text");
        this.provenance = collection.getMetadata("provenance_description");
        if (collection.getLogo() != null) {
            this.logo = new BitstreamEntityId(collection.getLogo());
        }
        
    }

    public CollectionEntity() {
        // check calling package/class in order to prevent chaining
        boolean includeFull = false;

        this.canEdit = false;
        this.handle = "123456789/0";
        this.name = "Sample collection";
        this.type = 4;
        this.id = 92;
        this.countItems = 10921;
        this.licence = "Example licence";
        this.items.add(new ItemEntity());
        this.communities.add(includeFull ? new CommunityEntity() : new CommunityEntityId());
    }

    public String getLicence() {
        return this.licence;
    }

    public List<?> getItems() {
        return this.items;
    }

    public List<?> getCommunities() {
        return this.communities;
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

    public int getId() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }

    public int getCountItems() {
        return this.countItems;
    }

    public String getShortDescription() {
        return this.short_description;
    }

    public String getIntroText() {
        return this.intro_text;
    }

    public String getCopyrightText() {
        return this.copyright_text;
    }

    public String getSidebarText() {
        return this.sidebar_text;
    }

    public String getProvenance() {
        return this.provenance;
    }

    public Object getLogo() {
        return this.logo;
    }

    @Override
    public String toString() {
        return "collection id:" + this.id + ", stuff.....";
    }
}
