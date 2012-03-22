package org.dspace.rest.data.item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.DCValue;
import org.dspace.content.Item;

public class ItemWithMetadataEntity extends ItemEntityId {

    private final String name;
    private final List<MetadataEntity> metadata;
    private final int type;

    public ItemWithMetadataEntity(Item item) throws SQLException {
        super(item);
        this.name = item.getName();
        this.type = item.getType();

        metadata = new ArrayList<MetadataEntity>();
        final DCValue[] dcValues = item.getMetadata(Item.ANY, Item.ANY, Item.ANY, Item.ANY);
        for (DCValue dcValue : dcValues)
        {
            this.metadata.add(new MetadataEntity(dcValue));
        }
    }

    public final List<MetadataEntity> getMetadata() {
        return this.metadata;
    }

    public final  String getName() {
        return this.name;
    }

    public final int getType() {
        return this.type;
    }

}