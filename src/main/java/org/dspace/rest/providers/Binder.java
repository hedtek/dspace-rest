package org.dspace.rest.providers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.dspace.core.Context;
import org.dspace.rest.entities.BitstreamEntity;
import org.dspace.rest.entities.CollectionEntity;
import org.dspace.rest.entities.CommunityEntity;
import org.dspace.rest.entities.DetailDepth;
import org.dspace.rest.entities.GroupEntity;
import org.dspace.rest.entities.ItemEntity;
import org.dspace.rest.entities.UserEntity;
import org.dspace.rest.params.DetailDepthParameters;
import org.dspace.rest.params.Parameters;
import org.sakaiproject.entitybus.EntityReference;
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
        return new Binder(mappings(func2actionMapGET, processedEntity), constructor(processedEntity));
    }
    
    public static Binder forCommunities() throws SecurityException, NoSuchMethodException {
        Map<String, String> func2actionMapGET = new HashMap<String, String>();
        func2actionMapGET.put("getId", "id");
        func2actionMapGET.put("getName", "name");
        func2actionMapGET.put("getCountItems", "countItems");
        func2actionMapGET.put("getHandle", "handle");
        func2actionMapGET.put("getType", "type");
        func2actionMapGET.put("getCollections", "collections");
        func2actionMapGET.put("getCanEdit", "canedit");
        func2actionMapGET.put("getParentCommunity", "anchestor");
        func2actionMapGET.put("getSubCommunities", "children");
        func2actionMapGET.put("getRecentSubmissions", "recent");
        func2actionMapGET.put("getShortDescription", "shortDescription");
        func2actionMapGET.put("getCopyrightText", "copyrightText");
        func2actionMapGET.put("getSidebarText", "sidebarText");
        func2actionMapGET.put("getIntroductoryText", "introductoryText");
        func2actionMapGET.put("getLogo", "logo");
        return build(func2actionMapGET, CommunityEntity.class);
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
        return build(func2actionMapGET, GroupEntity.class);
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
    
    private final Map<String, String> func2actionMapGET_rev ;
    private final Constructor<?> entityConstructor;

    private Binder(Map<String, String> func2actionMapGET_rev,
            Constructor<?> entityConstructor) {
        super();
        this.func2actionMapGET_rev = func2actionMapGET_rev;
        this.entityConstructor = entityConstructor;
    }

    public Object resolve(final String id, String[] segments, Parameters parameters,
            final Context context) {
        if (func2actionMapGET_rev.containsKey(segments[3])) {
            Object result;
            String function = func2actionMapGET_rev.get(segments[3]);
            Object CE = new Object();
            try {
                CE = entityConstructor.newInstance(id, context, 1, parameters.getDetailDepth().getDepth());
            } catch (Exception ex) {
                throw new EntityException("Internal server error", "Cannot create entity", 500);
            }
            try {
                Method method = CE.getClass().getMethod(function, new Class<?>[]{});
                result = method.invoke(CE); 
            } catch (Exception ex) {
                throw new EntityException("Internal server error", "Cannot call method " + function, 500);
            }
            return result;
        } else {
            throw new EntityException("Bad request", "Method not supported " + segments[3], 400);
        }
    }

}
