/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.data.collection;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.Collection;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.bitstream.BitstreamEntityId;

/**
 * Represents a collection for rendering.
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CollectionEntity extends CollectionWithItemsEntity {
    
    private final Boolean canEdit;
    private final String handle, licence;
    private final String short_description;
    private final String intro_text;
    private final String copyright_text;
    private final String sidebar_text;
    private final String provenance;
    private final Object logo;

    public CollectionEntity(final Collection collection, final List<Entity> items,
    final List<Entity> communities, final int itemsCount) throws SQLException {
        super (collection.getID(), collection.getName(), collection.getType(), items, communities, itemsCount);
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
    }

    public String getLicence() {
        return this.licence;
    }

    public String getHandle() {
        return this.handle;
    }

    public boolean getCanEdit() {
        return this.canEdit;
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
