package org.dspace.rest.data.base;

public class Entity {

    public enum Type {
        COLLECTION, COMMUNITY, ITEM, BITSTREAM, BUNDLE
    }
    
    private final int id;
    private final Type type;

    public Entity(final int id, final Type type) {
        super();
        this.id = id;
        this.type = type;
    }

    public final int getId() {
        return this.id;
    }

    @Override
    public final boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof Entity)) {
            return false;
        } else {
            final Entity other = (Entity) obj;
            if (this.type == other.type) {
                return other.id == this.id;
            } else {
                return false;
            }
        }
    }

    @Override
    public final int hashCode() {
        return id;
    }

    @Override
    public final String toString() {
        return "Entity (" + this.type + ") with id:" + this.id;
    }
}