/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import org.dspace.content.*;
import org.dspace.content.Collection;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityFieldRequired;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;
import org.sakaiproject.entitybus.exception.EntityException;
import org.dspace.authorize.AuthorizeException;

import java.util.*;

import org.dspace.content.crosswalk.*;
import org.sakaiproject.entitybus.EntityView;
import org.sakaiproject.entitybus.EntityReference;
import org.dspace.core.Context;

import java.sql.SQLException;
import java.io.StringWriter;
import org.jdom.output.XMLOutputter;
import org.jdom.Element;
import org.dspace.rest.util.UtilHelper;
import org.dspace.rest.util.UserRequestParams;

/**
 * Entity describing item
 * @see ItemEntityId
 * @see Item
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class ItemEntity extends ItemEntityId {

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
    //String metadata;
    Date lastModified;
    Object owningCollection;
    boolean isArchived, isWithdrawn;
    UserEntity submitter;
    private DisseminationCrosswalk xHTMLHeadCrosswalk;

    // TODO inspect and add additional fields
    public ItemEntity(String uid, Context context, int level, UserRequestParams uparams) throws SQLException {
        Item res = Item.find(context, Integer.parseInt(uid));
        this.id = res.getID();
        this.canEdit = res.canEdit();
        this.handle = res.getHandle();
        this.name = res.getName();
        this.type = res.getType();
        this.lastModified = res.getLastModified();
        this.isArchived = res.isArchived();
        this.isWithdrawn = res.isWithdrawn();
        this.submitter = new UserEntity(res.getSubmitter());

        Bundle[] bun = res.getBundles();
        Bitstream[] bst = res.getNonInternalBitstreams();
        Collection[] col = res.getCollections();
        Community[] com = res.getCommunities();
        boolean includeFull = false;
        level++;
        if (level <= uparams.getDetail()) {
            includeFull = true;
        }

        Collection ownCol = res.getOwningCollection();
        if (ownCol != null) {
            this.owningCollection = includeFull ? new CollectionEntity(ownCol, level, uparams) : new CollectionEntityId(ownCol);
        }
        for (Bundle b : bun) {
            this.bundles.add(includeFull ? new BundleEntity(b, level, uparams) : new BundleEntityId(b));
        }
        for (Bitstream b : bst) {
            this.bitstreams.add(includeFull ? new BitstreamEntity(b, level, uparams) : new BitstreamEntityId(b));
        }
        for (Collection c : col) {
            this.collections.add(includeFull ? new CollectionEntity(c, level, uparams) : new CollectionEntityId(c));
        }
        for (Community c : com) {
            this.communities.add(includeFull ? new CommunityEntity(c, level, uparams) : new CommunityEntityId(c));
        }

        DCValue[] dcValues = res.getMetadata(Item.ANY, Item.ANY, Item.ANY, Item.ANY);
        for (DCValue dcValue : dcValues)
        {
            this.metadata.add(new MetadataEntity(dcValue));
        }
    }

    public ItemEntity(Item item, int level, UserRequestParams uparams) throws SQLException {
        // Don't include full details for items deep in the data tree.
        boolean includeFull = false;
        level++;
        if (level <= uparams.getDetail()) {
            includeFull = true;
        }

        this.canEdit = item.canEdit();
        this.handle = item.getHandle();
        this.name = item.getName();
        this.type = item.getType();
        this.id = item.getID();
        this.lastModified = item.getLastModified();
        Collection ownCol = item.getOwningCollection();
        if (ownCol != null) {
            this.owningCollection = includeFull ? new CollectionEntity(ownCol, level, uparams) : new CollectionEntityId(ownCol);
        }
        this.isArchived = item.isArchived();
        this.isWithdrawn = item.isWithdrawn();
        this.submitter = new UserEntity(item.getSubmitter());

        Bundle[] bun = item.getBundles();
        Bitstream[] bst = item.getNonInternalBitstreams();
        Collection[] col = item.getCollections();
        Community[] com = item.getCommunities();
        for (Bundle b : bun) {
            this.bundles.add(includeFull ? new BundleEntity(b, level, uparams) : new BundleEntityId(b));
        }
        for (Bitstream b : bst) {
            this.bitstreams.add(includeFull ? new BitstreamEntity(b, level, uparams) : new BitstreamEntityId(b));
        }
        for (Collection c : col) {
            this.collections.add(includeFull ? new CollectionEntity(c, level, uparams) : new CollectionEntityId(c));
        }
        for (Community c : com) {
            this.communities.add(includeFull ? new CommunityEntity(c, level, uparams) : new CommunityEntityId(c));
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
