package org.dspace.rest.data.item;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.Item;
import org.dspace.eperson.EPerson;
import org.dspace.rest.data.base.AbstractBuilder;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.FetchGroup;
import org.dspace.rest.data.bitstream.BulkBitstreamBuilder;
import org.dspace.rest.data.bundle.BulkBundleBuilder;
import org.dspace.rest.data.collection.CollectionBuilder;
import org.dspace.rest.data.collection.Collections;
import org.dspace.rest.data.community.Communities;
import org.dspace.rest.entities.UserEntity;

public class ItemBuilder extends AbstractBuilder {
    
    private final Item item;

    private DetailDepth depth  = DetailDepth.STANDARD;
    private int level = 1;
    
    public ItemBuilder(Item item) {
        super();
        this.item = item;
    }
    
    public ItemBuilder till(final DetailDepth depth) {
        this.depth = depth;
        return this;
    }
    
    public ItemBuilder on(final int level) {
        this.level = level;
        return this;
    }
    
    public ItemEntity buildFull() throws SQLException {
        final int nextLevel = level + 1;
        final List<Entity> bundles 
            = new BulkBundleBuilder(item.getBundles()).till(depth).on(nextLevel).withFull(depth.includeFullDetails(nextLevel)).build();
        final List<Entity> bitstreams = new BulkBitstreamBuilder(item.getNonInternalBitstreams()).on(nextLevel).till(depth).build();
        final List<Entity> communities = Communities.toEntities(nextLevel, depth, item.getCommunities());
        final Entity owningCollection = Collections.owner(item, nextLevel, depth);
        return new ItemEntity(item, level, depth, owningCollection, bundles, bitstreams, communities, buildUserEntity());
    }

    public ItemBuilder with(FetchGroup fetchGroup) {
        setFetchGroup(fetchGroup);
        return this;
    }
    
    public Entity build() throws SQLException {
        switch (getFetchGroup()) {
        case MINIMAL:
            return new ItemEntityId(item);
        case LIGHT:
            return new ItemWithMetadataEntity(item);
        case DISPLAY:
            return forDisplay();
        default:
            return buildFull();
        }
    }

    private Entity forDisplay() throws SQLException {
        final List<Entity> bundles = new BulkBundleBuilder(item.getBundles()).noItems();
        final Entity owningCollection = new CollectionBuilder(item.getOwningCollection()).basicCommunities();
        return new ItemForDisplay(item, owningCollection, bundles, buildUserEntity());
    }
    
    public ItemBuilder withFull(boolean includeFullDetails) {
        setFull(includeFullDetails);
        return this;
    }

    public ItemBuilder withIdOnly(boolean idOnly) {
        setIdOnly(idOnly);
        return this;
    }

    private UserEntity buildUserEntity() throws SQLException {
        final EPerson submitter = item.getSubmitter();
        if(submitter == null) {
            return null;
        }
        else {
            return new UserEntity(submitter);
        }
    }
}
