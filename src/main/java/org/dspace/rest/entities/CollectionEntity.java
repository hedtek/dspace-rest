/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.entities;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.content.Collection;
import org.dspace.rest.data.Collections;

/**
 * Represents a collection for rendering.
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CollectionEntity extends BasicEntity {

    private static Logger log = Logger.getLogger(CollectionEntity.class);

    private final Boolean canEdit;
    private final String handle, licence;
    private final int countItems;
    private final List<Object> items;
    private final List<Object> communities;
    private final String short_description;
    private final String intro_text;
    private final String copyright_text;
    private final String sidebar_text;
    private final String provenance;
    private final Object logo;

    public CollectionEntity(final Collection collection, final int level, final DetailDepth depth,  final List<Object> items,
    final List<Object> communities, final int itemsCount) throws SQLException {
        super (collection.getID(), Type.COLLECTION, collection.getName(), collection.getType());
        // Only include full when above maximum depth
        final int nextLevel = level + 1;
        if (log.isDebugEnabled()) log.debug("Creating collection entity: DepthDetail is " + depth + "; include full? " + depth.includeFullDetails(nextLevel) + "; next level " + nextLevel);
        
        this.canEdit = collection.canEditBoolean();
        this.handle = collection.getHandle();
        this.licence = collection.getLicense();
        this.short_description = collection.getMetadata("short_description");
        this.intro_text = collection.getMetadata("introductory_text");
        this.copyright_text = collection.getMetadata("copyright_text");
        this.sidebar_text = collection.getMetadata("side_bar_text");
        this.provenance = collection.getMetadata("provenance_description");
        if (collection.getLogo() == null) {
            this.logo = null;
        } else {
            this.logo = new BitstreamEntityId(collection.getLogo());
        }
        
        this.communities = communities;
        this.items = items;
        this.countItems = itemsCount;
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

    public String getHandle() {
        return this.handle;
    }

    public boolean getCanEdit() {
        return this.canEdit;
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
}
