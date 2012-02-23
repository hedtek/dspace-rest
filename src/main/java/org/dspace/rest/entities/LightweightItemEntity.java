package org.dspace.rest.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.content.DCValue;
import org.dspace.content.Item;

public class LightweightItemEntity extends ItemEntityId {

    private final String name;
    private final List<MetadataEntity> metadata;

    public LightweightItemEntity(Item item) throws SQLException {
        super(item);
        this.name = item.getName();

        metadata = new ArrayList<MetadataEntity>();
        final DCValue[] dcValues = item.getMetadata(Item.ANY, Item.ANY, Item.ANY, Item.ANY);
        for (DCValue dcValue : dcValues)
        {
            this.metadata.add(new MetadataEntity(dcValue));
        }
    }

    public List<MetadataEntity> getMetadata() {
        return this.metadata;
    }

    public String getName() {
        return this.name;
    }

}