package org.dspace.rest.providers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.dspace.core.Context;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.entities.BitstreamEntity;
import org.dspace.rest.entities.CollectionEntity;
import org.dspace.rest.entities.DetailDepth;
import org.dspace.rest.entities.GroupEntity;
import org.dspace.rest.entities.ItemEntity;
import org.dspace.rest.entities.UserEntity;
import org.dspace.rest.params.Parameters;
import org.dspace.rest.params.Route;
import org.sakaiproject.entitybus.exception.EntityException;

public class Binder {
    
    public static Binder forUsers() throws SecurityException, NoSuchMethodException {
        Map<String, String> func2actionMapGET = new HashMap<String, String>();
        func2actionMapGET.put("getEmail", "email");
        func2actionMapGET.put("getFirstName", "firstName");
        func2actionMapGET.put("getFullName", "fullName");
        func2actionMapGET.put("getHandle", "handle");
        func2actionMapGET.put("getId", "id");
        func2actionMapGET.put("getLanguage", "language");
        func2actionMapGET.put("getLastName", "lastName");
        func2actionMapGET.put("getName", "name");
        func2actionMapGET.put("getNetId", "netId");
        func2actionMapGET.put("getRequireCertificate", "requireCertificate");
        func2actionMapGET.put("getSelfRegistered", "selfRegistered");
        func2actionMapGET.put("getType", "type");
        return build(func2actionMapGET, UserEntity.class);
    }

    @SuppressWarnings("rawtypes")
    private static Binder build(Map<String, String> func2actionMapGET,
            Class processedEntity) throws NoSuchMethodException {
        return new Binder(new MappedAttributeBinding(mappings(func2actionMapGET, processedEntity), 
                new ReflectionValuer(constructor(processedEntity))));
    }
    
    public static Binder forCommunities() throws SecurityException, NoSuchMethodException {
        return new Binder(new CommunitiesAttributeValuer());
    }
    
    public static Binder forCollections() throws SecurityException, NoSuchMethodException {
        Map<String, String> func2actionMapGET = new HashMap<String, String>();
        func2actionMapGET.put("getId", "id");
        func2actionMapGET.put("getName", "name");
        func2actionMapGET.put("getLicence", "licence");
        func2actionMapGET.put("getItems", "items");
        func2actionMapGET.put("getHandle", "handle");
        func2actionMapGET.put("getCanEdit", "canedit");
        func2actionMapGET.put("getCommunities", "communities");
        func2actionMapGET.put("getCountItems", "countItems");
        func2actionMapGET.put("getType", "type");
        func2actionMapGET.put("getShortDescription", "shortDescription");
        func2actionMapGET.put("getIntroText", "introText");
        func2actionMapGET.put("getCopyrightText", "copyrightText");
        func2actionMapGET.put("getSidebarText", "sidebarText");
        func2actionMapGET.put("getProvenance", "provenance");
        func2actionMapGET.put("getLogo", "logo");
        return build(func2actionMapGET, CollectionEntity.class);
    }
    
    public static Binder forBitstream() throws SecurityException, NoSuchMethodException {
        Map<String, String> func2actionMapGET = new HashMap<String, String>();
        func2actionMapGET.put("getMimeType", "mimeType");
        func2actionMapGET.put("getBundles", "bundles");
        func2actionMapGET.put("getCheckSum", "checkSum");
        func2actionMapGET.put("getCheckSumAlgorithm", "checkSumAlgorithm");
        func2actionMapGET.put("getDescription", "description");
        func2actionMapGET.put("getFormatDescription", "formatDescription");
        func2actionMapGET.put("getSequenceId", "sequenceId");
        func2actionMapGET.put("getSize", "size");
        func2actionMapGET.put("getSource", "source");
        func2actionMapGET.put("getStoreNumber", "storeNumber");
        func2actionMapGET.put("getUserFormatDescription", "userFormatDescription");
        func2actionMapGET.put("getName", "name");
        func2actionMapGET.put("getHandle", "handle");
        func2actionMapGET.put("getId", "id");
        func2actionMapGET.put("getType", "type");
        return build(func2actionMapGET, BitstreamEntity.class);
    }
    
    public static Binder forItem() throws SecurityException, NoSuchMethodException {
        Map<String, String> func2actionMapGET = new HashMap<String, String>();
        func2actionMapGET.put("getMetadata", "metadata");
        func2actionMapGET.put("getSubmitter", "submitter");
        func2actionMapGET.put("getIsArchived", "isArchived");
        func2actionMapGET.put("getIsWithdrawn", "isWithdrawn");
        func2actionMapGET.put("getOwningCollection", "owningCollection");
        func2actionMapGET.put("getLastModified", "lastModified");
        func2actionMapGET.put("getCollections", "collections");
        func2actionMapGET.put("getCommunities", "communities");
        func2actionMapGET.put("getName", "name");
        func2actionMapGET.put("getBitstreams", "bitstreams");
        func2actionMapGET.put("getHandle", "handle");
        func2actionMapGET.put("getCanEdit", "canedit");
        func2actionMapGET.put("getId", "id");
        func2actionMapGET.put("getType", "type");
        func2actionMapGET.put("getBundles", "bundles");
        return build(func2actionMapGET, ItemEntity.class);
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Constructor<?> constructor(Class processedEntity)
            throws NoSuchMethodException {
        return (Constructor<?>) processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, DetailDepth.class});
    }

    private static Map<String, String> mappings(Map<String, String> func2actionMapGET,
            Class<?> processedEntity) throws NoSuchMethodException {
        final Map<String, String> func2actionMapGET_rev = new HashMap<String, String>();
        final Method[] entityMethods = processedEntity.getMethods();
        for (Method m : entityMethods) {
            final String fieldGET = func2actionMapGET.get(m.getName());
            if (fieldGET != null) {
                func2actionMapGET_rev.put(fieldGET, m.getName());
            }
        }
        return func2actionMapGET_rev;
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

    private static final class MappedAttributeBinding implements AttributeValuer {
        private final Map<String, String> func2actionMapGET_rev ;
        private final ReflectionValuer valuer;

        private MappedAttributeBinding(Map<String, String> func2actionMapGET_rev, final ReflectionValuer valuer) {
            super();
            this.func2actionMapGET_rev = func2actionMapGET_rev;
            this.valuer = valuer;
        }
        
        private boolean isMapped(final String attributeSegment) {
            return func2actionMapGET_rev.containsKey(attributeSegment);
        }
        
        private String segmentMapping(final String attributeSegment) {
            return func2actionMapGET_rev.get(attributeSegment);
        }

        @Override
        public Object valueAttribute(final String id, Parameters parameters,
                final Context context, final String attributeSegment) {
            if (isMapped(attributeSegment)) {
                return valuer.attributeValueFor(id, parameters, context, segmentMapping(attributeSegment));
            } else {
                throw new EntityException("Bad request", "Method not supported " + attributeSegment, 400);
            }
        }

    }
    
    private static final class ReflectionValuer {
        private final Constructor<?> entityConstructor;

        private ReflectionValuer(Constructor<?> entityConstructor) {
            super();
            this.entityConstructor = entityConstructor;
        }
        
        public Object attributeValueFor(final String id, Parameters parameters,
                final Context context, final String attributeAccessorName) {
            try {
                final Object CE = entityConstructor.newInstance(id, context, 1, parameters.getDetailDepth().getDepth());
                return evoke(attributeAccessorName, CE);
            } catch (Exception ex) {
                throw new EntityException("Internal server error", "Cannot create entity", 500);
            }
        }

        private Object evoke(String function, Object CE) {
            try {
                final Method method = CE.getClass().getMethod(function, new Class<?>[]{});
                final Object result = method.invoke(CE);
                return result;
            } catch (Exception ex) {
                throw new EntityException("Internal server error", "Cannot call method " + function, 500);
            }
        }
    }
}
