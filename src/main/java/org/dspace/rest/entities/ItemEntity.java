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
import org.dspace.content.crosswalk.DisseminationCrosswalk;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityFieldRequired;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;

/**
 * Entity describing item
 * @see ItemEntityId
 * @see Item
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class ItemEntity extends ItemEntityId {

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
    
    @EntityId
    private int id;
    @EntityFieldRequired
    private String name;
    @EntityFieldRequired
    private Boolean canEdit;
    private String handle;
    private int type;
    List<BundleEntityId> bundles = new ArrayList<BundleEntityId>();
    List<BitstreamEntityId> bitstreams = new ArrayList<BitstreamEntityId>();
    List<Object> collections = new ArrayList<Object>();
    List<CommunityEntityId> communities = new ArrayList<CommunityEntityId>();
    List<MetadataEntity> metadata = new ArrayList<MetadataEntity>();
    Date lastModified;
    Object owningCollection;
    boolean isArchived, isWithdrawn;
    UserEntity submitter;
    
    public ItemEntity(String uid, Context context, int level, final DetailDepth depth) throws SQLException {
        this(Item.find(context, Integer.parseInt(uid)), level, depth);
    } 

    public ItemEntity(Item item, int level, final DetailDepth depth) throws SQLException {
        // Only include full when above maximum depth
        final boolean includeFullNextLevel = depth.includeFullDetails(++level);
        if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; include full? " + includeFullNextLevel + "; next level " + level);
        
        this.canEdit = item.canEdit();
        this.handle = item.getHandle();
        this.name = item.getName();
        this.type = item.getType();
        this.id = item.getID();
        this.lastModified = item.getLastModified();
        Collection ownCol = item.getOwningCollection();
        if (ownCol != null) {
            this.owningCollection = includeFullNextLevel ? new CollectionEntity(ownCol, level, depth) : new CollectionEntityId(ownCol);
        }
        this.isArchived = item.isArchived();
        this.isWithdrawn = item.isWithdrawn();
        
        this.submitter = buildUserEntity(item);

        Bundle[] bun = item.getBundles();
        Bitstream[] bst = item.getNonInternalBitstreams();
        Collection[] col = item.getCollections();
        Community[] com = item.getCommunities();
        for (Bundle b : bun) {
            this.bundles.add(includeFullNextLevel ? new BundleEntity(b, level, depth) : new BundleEntityId(b));
        }
        for (Bitstream b : bst) {
            this.bitstreams.add(includeFullNextLevel ? new BitstreamEntity(b, level, depth) : new BitstreamEntityId(b));
        }
        for (Collection c : col) {
            this.collections.add(includeFullNextLevel ? new CollectionEntity(c, level, depth) : new CollectionEntityId(c));
        }
        for (Community c : com) {
            this.communities.add(includeFullNextLevel ? new CommunityEntity(c, level, depth) : new CommunityEntityId(c));
        }

        DCValue[] dcValues = item.getMetadata(Item.ANY, Item.ANY, Item.ANY, Item.ANY);
        for (DCValue dcValue : dcValues)
        {
            this.metadata.add(new MetadataEntity(dcValue));
        }
    }

    public ItemEntity() {
        this.canEdit = false;
        this.handle = "123456789/0";
        this.name = "Item";
        this.type = 3;
        this.id = 22;
        this.bundles.add(new BundleEntityId());
        this.bitstreams.add(new BitstreamEntityId());
        this.collections.add(new CollectionEntityId());
        this.communities.add(new CommunityEntityId());
        this.metadata.add(new MetadataEntity("","","",""));
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

    public int getId() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }

    public List<BundleEntityId> getBundles() {
        return this.bundles;
    }

    @Override
    public String toString() {
        return "id:" + this.id + ", stuff.....";
    }
}
