package org.dspace.rest.data.base;


public class BasicEntity extends Entity {

    private final String name;
    private final int dspaceEntityTypeId;

    public BasicEntity(final int id, final Type type, final String name, final int dspaceEntityTypeId) {
        super(id, type);
        this.name = name;
        this.dspaceEntityTypeId = dspaceEntityTypeId;
    }

    public final String getName() {
        return this.name;
    }

    public final int getType() {
        return this.dspaceEntityTypeId;
    }

}