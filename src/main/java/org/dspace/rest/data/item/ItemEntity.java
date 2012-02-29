/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.data.item;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.content.Item;
import org.dspace.eperson.EPerson;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.bundle.BundleEntityId;
import org.dspace.rest.data.collection.Collections;
import org.dspace.rest.entities.UserEntity;

/**
 * Entity describing item
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class ItemEntity extends ItemWithMetadataEntity {

    private static Logger log = Logger.getLogger(ItemEntity.class);

    private static UserEntity buildUserEntity(Item item) throws SQLException {
        final EPerson submitter = item.getSubmitter();
        if(submitter == null) {
            return null;
        }
        else {
            return new UserEntity(submitter);
        }
    }
    
    private final Boolean canEdit;
    private final String handle;
    private final List<BundleEntityId> bundles;
    private final List<Entity> bitstreams;
    private final List<Entity> collections;
    private final List<Entity> communities;
    private final Date lastModified;
    private final Entity owningCollection;
    private final boolean isArchived;
    private final boolean isWithdrawn;
    private final UserEntity submitter;
    
    ItemEntity(Item item, int level, final DetailDepth depth, final Entity owningCollection, 
            final List<BundleEntityId> bundles, List<Entity> bitstreams, List<Entity> communities) throws SQLException {
        super(item);
        if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; level " + level);
        this.owningCollection = owningCollection;
        this.bundles = bundles;
        this.bitstreams = bitstreams;
        this.communities = communities;
        
        this.canEdit = item.canEdit();
        this.handle = item.getHandle();
        this.lastModified = item.getLastModified();
        
        this.isArchived = item.isArchived();
        this.isWithdrawn = item.isWithdrawn();
        
        this.submitter = buildUserEntity(item);
        this.collections = Collections.build(level + 1, depth, item.getCollections());
    }

    public UserEntity getSubmitter() {
        return this.submitter;
    }

    public boolean getIsArchived() {
        return this.isArchived;
    }

    public boolean getIsWithdrawn() {
        return this.isWithdrawn;
    }

    public Entity getOwningCollection() {
        return this.owningCollection;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public List<Entity> getCollections() {
        return this.collections;
    }

    public List<Entity> getCommunities() {
        return this.communities;
    }

    public List<Entity> getBitstreams() {
        return this.bitstreams;
    }

    public String getHandle() {
        return this.handle;
    }

    public boolean getCanEdit() {
        return this.canEdit;
    }

    public List<BundleEntityId> getBundles() {
        return this.bundles;
    }
}
