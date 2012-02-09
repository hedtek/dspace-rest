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
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.exception.EntityException;

public class Binder {
    public static Binder forUsers() throws SecurityException, NoSuchMethodException {
        Map<String, String> func2actionMapGET = new HashMap<String, String>();
        Map<String, Class<?>[]> funcParamsGET = new HashMap<String, Class<?>[]>();
        Map<String, String> func2actionMapGET_rev = new HashMap<String, String>();
        
        Class processedEntity = UserEntity.class;
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
        Constructor<?>  entityConstructor = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, DetailDepth.class});
        initMappings(func2actionMapGET, funcParamsGET, func2actionMapGET_rev, processedEntity);
        return new Binder(func2actionMapGET, funcParamsGET, func2actionMapGET_rev, entityConstructor);
    }
    
    public static Binder forCommunities() throws SecurityException, NoSuchMethodException {
        Map<String, String> func2actionMapGET = new HashMap<String, String>();
        Map<String, Class<?>[]> funcParamsGET = new HashMap<String, Class<?>[]>();
        Map<String, String> func2actionMapGET_rev = new HashMap<String, String>();
        
        Class processedEntity = CommunityEntity.class;
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
        Constructor<?>  entityConstructor = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, DetailDepth.class});
        initMappings(func2actionMapGET, funcParamsGET, func2actionMapGET_rev, processedEntity);
        return new Binder(func2actionMapGET, funcParamsGET, func2actionMapGET_rev, entityConstructor);
    }
    
    public static Binder forCollections() throws SecurityException, NoSuchMethodException {
        Class processedEntity = CollectionEntity.class;

        Map<String, String> func2actionMapGET = new HashMap<String, String>();
        Map<String, Class<?>[]> funcParamsGET = new HashMap<String, Class<?>[]>();
        Map<String, String> func2actionMapGET_rev = new HashMap<String, String>();
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
        Constructor<?>  entityConstructor = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, DetailDepth.class});
        initMappings(func2actionMapGET, funcParamsGET, func2actionMapGET_rev, processedEntity);
        return new Binder(func2actionMapGET, funcParamsGET, func2actionMapGET_rev, entityConstructor);
    }
    
    public static Binder forBitstream() throws SecurityException, NoSuchMethodException {
        Class processedEntity = BitstreamEntity.class;
        Map<String, String> func2actionMapGET = new HashMap<String, String>();
        Map<String, Class<?>[]> funcParamsGET = new HashMap<String, Class<?>[]>();
        Map<String, String> func2actionMapGET_rev = new HashMap<String, String>();
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
        Constructor<?>  entityConstructor = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, DetailDepth.class});
        initMappings(func2actionMapGET, funcParamsGET, func2actionMapGET_rev, processedEntity);
        return new Binder(func2actionMapGET, funcParamsGET, func2actionMapGET_rev, entityConstructor);
    }
    
    public static Binder forItem() throws SecurityException, NoSuchMethodException {
        Class processedEntity = ItemEntity.class;
        Map<String, String> func2actionMapGET = new HashMap<String, String>();
        Map<String, Class<?>[]> funcParamsGET = new HashMap<String, Class<?>[]>();
        Map<String, String> func2actionMapGET_rev = new HashMap<String, String>();
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
        Constructor<?>  entityConstructor = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, DetailDepth.class});
        initMappings(func2actionMapGET, funcParamsGET, func2actionMapGET_rev, processedEntity);
        return new Binder(func2actionMapGET, funcParamsGET, func2actionMapGET_rev, entityConstructor);
    }

    
    public static Binder forGroup() throws SecurityException, NoSuchMethodException {
        Class processedEntity = GroupEntity.class;
        Map<String, String> func2actionMapGET = new HashMap<String, String>();
        Map<String, Class<?>[]> funcParamsGET = new HashMap<String, Class<?>[]>();
        Map<String, String> func2actionMapGET_rev = new HashMap<String, String>();
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
        Constructor<?>  entityConstructor = processedEntity.getDeclaredConstructor(new Class<?>[]{String.class, Context.class, Integer.TYPE, DetailDepth.class});
        initMappings(func2actionMapGET, funcParamsGET, func2actionMapGET_rev, processedEntity);
        return new Binder(func2actionMapGET, funcParamsGET, func2actionMapGET_rev, entityConstructor);
    }

    private final Map<String, String> func2actionMapGET;
    private final Map<String, Class<?>[]> funcParamsGET;
    private final Map<String, String> func2actionMapGET_rev ;
    private final Constructor<?> entityConstructor;

    private Binder(Map<String, String> func2actionMapGET,
            Map<String, Class<?>[]> funcParamsGET,
            Map<String, String> func2actionMapGET_rev,
            Constructor<?> entityConstructor) {
        super();
        this.func2actionMapGET = func2actionMapGET;
        this.funcParamsGET = funcParamsGET;
        this.func2actionMapGET_rev = func2actionMapGET_rev;
        this.entityConstructor = entityConstructor;
    }


    private static void initMappings(Map<String, String> func2actionMapGET,
            Map<String, Class<?>[]> funcParamsGET,
            Map<String, String> func2actionMapGET_rev,
            Class<?> processedEntity) throws NoSuchMethodException {
        // scan for methods;
        Method[] entityMethods = processedEntity.getMethods();
        for (Method m : entityMethods) {
            //log.info("checked method " + m.getName());
            String fieldGET = func2actionMapGET.get(m.getName());
            if (fieldGET != null) {
                addParameters(fieldGET, m.getParameterTypes(), funcParamsGET);
                addMethod(fieldGET, m.getName(), func2actionMapGET_rev);
            }
        }
    }
    

    private static void addParameters(String function, Class<?>[] parameters, Map<String, Class<?>[]> mappings_parameters) {
        mappings_parameters.put(function, parameters);
    }

    private Class<?>[] getParameters(String function, Map<String, Class<?>[]> mappings_parameters) {
        return mappings_parameters.get(function);
    }

    private static void addMethod(String field, String function, Map<String, String> mappings_rev) {
        mappings_rev.put(field, function);
    }

    private String getMethod(String field, Map<String, String> mappings_rev) {
        return mappings_rev.get(field);
    }
    

    public Object resolve(EntityReference reference, String[] segments,
            DetailDepthParameters depth, final Context context) {
        if (func2actionMapGET_rev.containsKey(segments[3])) {
            Object result;
            String function = getMethod(segments[3], func2actionMapGET_rev);
            

            Object CE = new Object();
            try {

                CE = entityConstructor.newInstance(reference.getId(), context, 1, depth.getDepth());
            } catch (Exception ex) {
                throw new EntityException("Internal server error", "Cannot create entity", 500);
            }

            try {
                Method method = CE.getClass().getMethod(function, new Class<?>[]{});
                //Method method = CE.getClass().getMethod(function, new Class<?>[]{EntityView.class, Map.class, Context.class});
                result = method.invoke(CE); // TODO more flexible for other param types
            } catch (Exception ex) {
                throw new EntityException("Internal server error", "Cannot call method " + function, 500);
            }

           
            return result;
        } else {
            throw new EntityException("Bad request", "Method not supported " + segments[3], 400);
        }
    }

}
