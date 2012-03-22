package org.dspace.rest.params;

import java.sql.SQLException;
import java.util.List;

import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.FetchGroup;
import org.dspace.rest.data.collection.CollectionEntity;
import org.dspace.rest.data.collection.Collections;
import org.dspace.rest.data.community.Communities;
import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

public class Parameters {
    

private final RequestStorage requestStore;
    
    public Parameters(RequestStorage requestStore) {
        super();
        this.requestStore = requestStore;
    }

    public EntityBuildParameters getEntityBuild() {
        return EntityBuildParameters.build(requestStore);
    }

    public DetailDepthParameters getDetailDepth() {
        return DetailDepthParameters.build(requestStore);
    }

    public PaginationParameters getPagination() {
        return new PaginationParameters(requestStore);
    }

    public SortParameters getSort() {
        return new SortParameters(requestStore);
    }

    public StatusParameters getStatus() {
        return new StatusParameters(requestStore);
    }
    
    public DurationParameters getDuration() {
        return new DurationParameters(requestStore);
    }

    /**
     * Remove items from list in order to display only requested items
     * (according to _start, _limit etc.)
     * @param entities
     */
    public void removeTrailing(List<?> entities) {
        getPagination().removeTrailing(entities);
    }
    
    public void sort(final List<?> entities) {
        /**
         * if the full info are requested and there are sorting requirements
         * process entities through sorting filter first
         */
        if (!getEntityBuild().isIdOnly()) {
            getSort().sort(entities);
        }
    }

    public ScopeParameters getScope(Context context) {
        return ScopeParameters.build(requestStore, context);
    }

    public Entity collection(final String uid, final Context context) throws SQLException {
        return Collections.build(uid, context, getDetailDepth().getDepth(), getEntityBuild().isIdOnly());
    }

    public CollectionEntity collectionEntity(String id, Context context) throws SQLException {
        return Collections.collection(id, context, getDetailDepth().getDepth());
    }

    public Object lightCollectionWithItems(String uid, Context context) throws SQLException {
        return Collections.collectionWithItems(uid, context, getPagination().pagination());
    }

    public List<Entity> communities(
            final Context context) throws SQLException {
        final boolean topLevelOnly = getEntityBuild().isTopLevelOnly();
        final FetchGroup fetchGroup = getEntityBuild().getFetchGroup();
        final List<Entity> entities = new Communities(context).get(topLevelOnly, fetchGroup);
        sort(entities);
        removeTrailing(entities);
        return entities;
    }

    public Entity community(final String uid, final Context context) throws SQLException {
        return getEntityBuild().community(uid, context, getDetailDepth().getDepth());
    }

    public Entity item(String uid, Context context) throws SQLException {
        final DetailDepth depth = getDetailDepth().getDepth();
        return getEntityBuild().item(context, uid, depth);
    }

    public List<Entity> items(final Context context) throws SQLException {
        final List<Entity> entities = getEntityBuild().items(context);
        sort(entities);
        removeTrailing(entities);
        return entities;
    }

    public Entity toEntity(final Item item) throws SQLException {
        return getEntityBuild().item(item, getDetailDepth().getDepth());
    }

    public Entity bitstream(final String id, final Context context) throws SQLException {
        final DetailDepth depth = getDetailDepth().getDepth();
        return getEntityBuild().bitstream(context, depth, id);
    }
}
