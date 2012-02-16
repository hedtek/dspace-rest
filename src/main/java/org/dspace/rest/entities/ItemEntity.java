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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.content.Bitstream;
import org.dspace.content.Bundle;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.DCValue;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;

/**
 * Entity describing item
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class ItemEntity extends ItemEntityId {

    private static Logger log = Logger.getLogger(ItemEntity.class);

    private static List<BundleEntityId> build(int level, final DetailDepth depth,
            final boolean includeFullNextLevel, final Bundle[] bun)
            throws SQLException {
        List<BundleEntityId> bundles = new ArrayList<BundleEntityId>();
        for (Bundle b : bun) {
            bundles.add(includeFullNextLevel ? new BundleEntity(b, level, depth) : new BundleEntityId(b));
        }
        return bundles;
    }

    private static Object buildOwningCollection(Item item, int level,
            final DetailDepth depth, final boolean includeFullNextLevel)
            throws SQLException {
        Object owningCollection;
        final Collection ownCol = item.getOwningCollection();
        if (ownCol == null) {
            owningCollection = null;
        } else {
            owningCollection = includeFullNextLevel ? new CollectionEntity(ownCol, level, depth) : new CollectionEntityId(ownCol);
        }
        return owningCollection;
    }

    private static UserEntity buildUserEntity(Item item) throws SQLException {
        final EPerson submitter = item.getSubmitter();
        if(submitter == null) {
            return null;
        }
        else {
            return new UserEntity(submitter);
        }
    }
    
    private final String name;
    private final Boolean canEdit;
    private final String handle;
    private final int type;
    private final List<BundleEntityId> bundles;
    private final List<BitstreamEntityId> bitstreams = new ArrayList<BitstreamEntityId>();
    private final List<Object> collections = new ArrayList<Object>();
    private final List<CommunityEntityId> communities = new ArrayList<CommunityEntityId>();
    private final List<MetadataEntity> metadata = new ArrayList<MetadataEntity>();
    private final Date lastModified;
    private final Object owningCollection;
    private final boolean isArchived;
    private final boolean isWithdrawn;
    private final UserEntity submitter;
    
    public ItemEntity(String uid, Context context, final DetailDepth depth) throws SQLException {
        this(Item.find(context, Integer.parseInt(uid)), 1, depth);
    } 

    public ItemEntity(Item item, int level, final DetailDepth depth) throws SQLException {
        super(item);
        // Only include full when above maximum depth
        final boolean includeFullNextLevel = depth.includeFullDetails(++level);
        if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; include full? " + includeFullNextLevel + "; next level " + level);
        
        this.canEdit = item.canEdit();
        this.handle = item.getHandle();
        this.name = item.getName();
        this.type = item.getType();
        this.lastModified = item.getLastModified();
        
        this.owningCollection = buildOwningCollection(item, level, depth, includeFullNextLevel);
        
        this.isArchived = item.isArchived();
        this.isWithdrawn = item.isWithdrawn();
        
        this.submitter = buildUserEntity(item);

        this.bundles = build(level, depth, includeFullNextLevel, item.getBundles());
        
        final Bitstream[] bst = item.getNonInternalBitstreams();
        for (Bitstream b : bst) {
            this.bitstreams.add(includeFullNextLevel ? new BitstreamEntity(b, level, depth) : new BitstreamEntityId(b));
        }
        
        final Collection[] col = item.getCollections();
        for (Collection c : col) {
            this.collections.add(includeFullNextLevel ? new CollectionEntity(c, level, depth) : new CollectionEntityId(c));
        }
        
        final Community[] com = item.getCommunities();
        for (Community c : com) {
            this.communities.add(includeFullNextLevel ? new CommunityEntity(c, level, depth) : new CommunityEntityId(c));
        }

        final DCValue[] dcValues = item.getMetadata(Item.ANY, Item.ANY, Item.ANY, Item.ANY);
        for (DCValue dcValue : dcValues)
        {
            this.metadata.add(new MetadataEntity(dcValue));
        }
    }

    public List<MetadataEntity> getMetadata()
    {
        return this.metadata;
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

    public Object getOwningCollection() {
        return this.owningCollection;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public List<Object> getCollections() {
        return this.collections;
    }

    public List<CommunityEntityId> getCommunities() {
        return this.communities;
    }

    public String getName() {
        return this.name;
    }

    public List<BitstreamEntityId> getBitstreams() {
        return this.bitstreams;
    }

    public String getHandle() {
        return this.handle;
    }

    public boolean getCanEdit() {
        return this.canEdit;
    }

    public int getType() {
        return this.type;
    }

    public List<BundleEntityId> getBundles() {
        return this.bundles;
    }
}
