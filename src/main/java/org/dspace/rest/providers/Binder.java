package org.dspace.rest.providers;

import java.sql.SQLException;

import org.dspace.core.Context;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.entities.BitstreamEntity;
import org.dspace.rest.entities.CollectionEntity;
import org.dspace.rest.entities.GroupEntity;
import org.dspace.rest.entities.ItemEntity;
import org.dspace.rest.entities.UserEntity;
import org.dspace.rest.params.Parameters;
import org.dspace.rest.params.Route;

public class Binder {
    
    public static Binder forUsers() throws SecurityException, NoSuchMethodException {
        return new Binder(new UserAttributeValuer());
    }
    
    private static class UserAttributeValuer extends DirectAttributeValuer {

        @Override
        protected Object value(String id, Parameters parameters,
                Context context, String attributeSegment) throws SQLException {
            if("firstName".equals(attributeSegment)) {
                return new UserEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getFirstName();
            } else if("fullName".equals(attributeSegment)) {
                return new UserEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getFullName();
            } else if("id".equals(attributeSegment)) {
                return new UserEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getId();
            } else if("lastName".equals(attributeSegment)) {
                return new UserEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getLastName();
            } else if("type".equals(attributeSegment)) {
                return new UserEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getType();
            } else {
                return null;
            }
        }

        @Override
        protected Operation operation() {
            return Operation.GET_USER_ENTITIES;
        }
        
    }
    
    public static Binder forCommunities() throws SecurityException, NoSuchMethodException {
        return new Binder(new CommunitiesAttributeValuer());
    }
    
    public static Binder forCollections() throws SecurityException, NoSuchMethodException {
        return new Binder(new CollectionAttributeValuer());
    }
    
    private static class CollectionAttributeValuer extends DirectAttributeValuer {

        @Override
        protected Object value(String id, Parameters parameters,
                Context context, String attributeSegment) throws SQLException {
            if("id".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getId();
            } else if("id".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getId();
            } else if("name".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getName();
            } else if("licence".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getLicence();
            } else if("items".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getItems();
            } else if("handle".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getHandle();
            } else if("canedit".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getCanEdit();
            } else if("communities".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getCommunities();
            } else if("countItems".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getCountItems();
            } else if("type".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getType();
            } else if("shortDescription".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getShortDescription();
            } else if("introText".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getIntroText();
            } else if("copyrightText".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getCopyrightText();
            } else if("sidebarText".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getSidebarText();
            } else if("provenance".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getProvenance();
            } else if("logo".equals(attributeSegment)) {
                return new CollectionEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getLogo();
            } else {
                return null;
            }
        }

        @Override
        protected Operation operation() {
            return Operation.GET_COLLECTIONS;
        }
        
    }
    public static Binder forBitstream() throws SecurityException, NoSuchMethodException {
        return new Binder(new BitstreamAttributeValuer());
    }
    
    private static class BitstreamAttributeValuer extends DirectAttributeValuer {

        @Override
        protected Object value(String id, Parameters parameters,
                Context context, String attributeSegment) throws SQLException {
            if("mimeType".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getMimeType();
            } else if("bundles".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getBundles();
            } else if("checkSum".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getCheckSum();
            } else if("checkSumAlgorithm".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getCheckSumAlgorithm();
            } else if("description".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getDescription();
            } else if("formatDescription".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getFormatDescription();
            } else if("sequenceId".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getSequenceId();
            } else if("size".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getSize();
            } else if("source".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getSource();
            } else if("storeNumber".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getStoreNumber();
            } else if("userFormatDescription".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getUserFormatDescription();
            } else if("name".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getName();
            } else if("handle".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getHandle();
            } else if("id".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getId();
            } else if("type".equals(attributeSegment)) {
                return new BitstreamEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getType();
            } else {
                return null;
            }
        }

        @Override
        protected Operation operation() {
            return Operation.GET_BITSTREAM;
        }
        
    }
    
    public static Binder forItem() throws SecurityException, NoSuchMethodException {
        return new Binder(new ItemAttributeValuer());
    }
    
    private static class ItemAttributeValuer extends DirectAttributeValuer {

        @Override
        protected Object value(String id, Parameters parameters,
                Context context, String attributeSegment) throws SQLException {
            if("metadata".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getMetadata();
            } else if("submitter".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getSubmitter();
            } else if("isArchived".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getIsArchived();
            } else if("isWithdrawn".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getIsWithdrawn();
            } else if("owningCollection".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getOwningCollection();
            } else if("lastModified".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getLastModified();
            } else if("collections".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getCollections();
            } else if("communities".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getCommunities();
            } else if("name".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getName();
            } else if("bitstreams".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getBitstreams();
            } else if("handle".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getHandle();
            } else if("canedit".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getCanEdit();
            } else if("id".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getId();
            } else if("type".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getType();
            } else if("bundles".equals(attributeSegment)) {
                return new ItemEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getBundles();
            } else {
                return null;
            }
        }

        @Override
        protected Operation operation() {
            return Operation.GET_ITEMS;
        }
        
    }

    
    public static Binder forGroup() throws SecurityException, NoSuchMethodException {
        return new Binder(new GroupAttributeValuer());
    }
    
    private static class GroupAttributeValuer extends DirectAttributeValuer {

        @Override
        protected Object value(String id, Parameters parameters,
                Context context, String attributeSegment) throws SQLException {
            
            if ("handle".equals(attributeSegment)) {
                return new GroupEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getHandle();
            } else if ("id".equals(attributeSegment)) {
                return new GroupEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getId();
            } else if ("isEmpty".equals(attributeSegment)) {
                return new GroupEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getIsEmpty();
            } else if ("members".equals(attributeSegment)) {
                return new GroupEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getMembers();
            } else if ("memberGroups".equals(attributeSegment)) {
                return new GroupEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getMemberGroups();
            } else if ("name".equals(attributeSegment)) {
                return new GroupEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getName();
            } else if ("type".equals(attributeSegment)) {
                return new GroupEntity(id, context, 1, parameters.getDetailDepth().getDepth()).getType();
            } else {
                return null;
            }
        }

        @Override
        protected Operation operation() {
            return Operation.GET_GROUP_ENTITIES;
        }
        
    }
    
    
    private final AttributeValuer valuer;

    private Binder(final AttributeValuer valuer) {
        super();
        this.valuer = valuer;
    }

    public Object resolve(final String id, Route route, Parameters parameters,
            final Context context) {
        return valuer.valueAttribute(id, parameters, context, route.attributeSegment());
    }
}
