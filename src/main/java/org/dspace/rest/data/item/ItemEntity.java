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
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.collection.Collections;
import org.dspace.rest.entities.UserEntity;

/**
 * Entity describing item
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class ItemEntity extends ItemForDisplay {

    private static Logger log = Logger.getLogger(ItemEntity.class);

    private final Boolean canEdit;
    private final String handle;
    private final List<Entity> collections;
    private final Date lastModified;
    private final boolean isWithdrawn;
    private final List<Entity> bitstreams;
    private final List<Entity> communities;
    
    ItemEntity(Item item, int level, final DetailDepth depth, final Entity owningCollection, 
            final List<Entity> bundles, List<Entity> bitstreams, List<Entity> communities,
            final UserEntity submitter) throws SQLException {
        super(item, owningCollection, bundles, submitter);
        if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; level " + level);
        this.bitstreams = bitstreams;
        this.communities = communities;
        this.canEdit = item.canEdit();
        this.handle = item.getHandle();
        this.lastModified = item.getLastModified();
        
        this.isWithdrawn = item.isWithdrawn();
        
        this.collections = Collections.build(level + 1, depth, item.getCollections());
    }

    public boolean getIsWithdrawn() {
        return this.isWithdrawn;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public List<Entity> getCollections() {
        return this.collections;
    }

    public String getHandle() {
        return this.handle;
    }

    public boolean getCanEdit() {
        return this.canEdit;
    }

    public final List<Entity> getBitstreams() {
        return this.bitstreams;
    }

    public final List<Entity> getCommunities() {
        return this.communities;
    }
}
