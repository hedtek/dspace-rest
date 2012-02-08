/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.util;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.core.Context;
import org.dspace.rest.entities.CollectionEntity;
import org.dspace.rest.entities.CollectionEntityId;
import org.dspace.rest.entities.CommunityEntity;
import org.dspace.rest.entities.CommunityEntityId;
import org.dspace.rest.entities.ItemEntity;
import org.dspace.rest.entities.ItemEntityId;
import org.dspace.rest.params.RequestParameters;
import org.dspace.rest.providers.AbstractBaseProvider;
import org.sakaiproject.entitybus.exception.EntityException;

/**
 * Here are implemented and coupled helper methods used by several providers
 * especially in the custom actions
 * @see GenComparator
 * @see AbstractBaseProvider
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class UtilHelper {

    protected UtilHelper() {
    }

    public static final boolean DEBUG_ACTIVE = true;
    // methods for getObjects
    public static final int PARENTS = 1;
    public static final int CHILDREN = 2;
    public static final int COLLECTIONS = 3;
    public static final int RECENT_SUBMISSIONS = 4;
    public static final int COMMUNITIES_INVOLVED = 5;
    public static final int ITEMS_INVOLVED = 6;
    public static final int ITEM_PERMISSION = 7;
    public static final int ITEM_IN_COMMUNITIES = 8;
    public static final int ITEM_IN_COLLECTIONS = 9;
    // sort methods for GenComparator
    public static final int SORT_ID = 210;
    public static final int SORT_NAME = 211;
    public static final int SORT_LASTMODIFIED = 212;
    public static final int SORT_SUBMITTER = 213;
    public static final int SORT_COUNT_ITEMS = 214;
    public static final int SORT_LANGUAGE = 215;
    public static final int SORT_LASTNAME = 216;
    public static final int SORT_FULL_NAME = 217;
    public static final int SORT_ID_REV = 310;
    public static final int SORT_NAME_REV = 311;
    public static final int SORT_LASTMODIFIED_REV = 312;
    public static final int SORT_SUBMITTER_REV = 313;
    public static final int SORT_COUNT_ITEMS_REV = 314;
    public static final int SORT_LANGUAGE_REV = 315;
    public static final int SORT_LASTNAME_REV = 316;
    public static final int SORT_FULL_NAME_REV = 317;
    public static final int DEPTH_LEVEL = 2;
    public static final int DEPTH_MINIMAL = 1;
    public static final int DEPTH_STANDARD = 2;
    public static final int DEPTH_EXTENDED = 3;    
    public static final Map<String, String> mappings = new HashMap<String, String>() {

        {
            put("getId", "id");
            put("getName", "name");
            put("getCountItems", "countItems");
            put("getHandle", "handle");
            put("getType", "type");
            put("getCollections", "collections");
            put("getCanEdit", "canedit");
            put("getParentCommunity", "parents");
            put("getSubCommunities", "children");


        }
    };
    private static Map<String, Class<?>[]> mappings_parameters = new HashMap<String, Class<?>[]>();
    private static Map<String, String> mappings_rev = new HashMap<String, String>();

    /**
     * Returns objects according to method choosen, used by communities
     * @param uid community/collection/item id
     * @param context 
     * @param method method choosen
     * @param idOnly should only ids (basic entities) be returned
     * @param immediateOnly returns only immediate subcommunities
     * @return list containing entities (e.g. CommunityEntity, ItemEntity etc)
     * @throws java.sql.SQLException
     * @throws org.dspace.rest.util.RecentSubmissionsException
     */
    public static List<Object> getObjects(String uid, Context context, int method, boolean idOnly, boolean immediateOnly, RequestParameters uparams) throws SQLException, RecentSubmissionsException {
        List<Object> entities = new ArrayList<Object>();

        // Reflect method resolution could be used here but this causes perfomance penalty

        switch (method) {

            // returns parent entities of community
            case PARENTS: {
                if (uid.equals(":ID:")) {
                    entities.add(new CommunityEntity());
                } else {
                    Community res = Community.find(context, Integer.parseInt(uid));
                    if (immediateOnly) {
                        for (Community o : res.getAllParents()) {
                            entities.add(idOnly ? new CommunityEntityId(o) : new CommunityEntity(o,1, uparams));
                        }
                    } else {
                        entities.add(idOnly ? new CommunityEntityId(res.getParentCommunity()) : new CommunityEntity(res.getParentCommunity(),1, uparams));
                    }
                }
            }
            break;

            // returns children entities of community
            case CHILDREN: {
                if (uid.equals(":ID:")) {
                    entities.add(new CommunityEntity());
                } else {
                    Community res = Community.find(context, Integer.parseInt(uid));
                    for (Community o : res.getSubcommunities()) {
                        entities.add(idOnly ? new CommunityEntityId(o) : new CommunityEntity(o,1, uparams));
                    }

                    try {
                        CommunityEntity ce = (CommunityEntity) entities.get(0);
                        //Field mile = ce.getClass().getField("name");
                        Field mile[] = ce.getClass().getDeclaredFields();
                        //ce.getClass().
                        System.out.println("num fields " + mile.length + " id " + ce.getHandle());
                        for (Field f : mile) {
                            f.setAccessible(true);
                            System.out.println("Field " + f.getName());
                        }

                        //Method mika = ce.getClass().getMethod("getHandle", null);
                        //Method mika2=ce.getClass().getDeclaredMethods()[0];
                        //mika.setAccessible(true);
                        //mika2.setAccessible(true);
                        entities.add(ce);


                        //System.out.println(" metoda " + mika.getName() + " - " + mika2.getName());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    for (Collection o : res.getCollections()) { // added later for test purposes
                        entities.add(idOnly ? new CollectionEntityId(o) : new CollectionEntity(o,1, uparams));
                    }


                }
            }
            break;

            // returns collections in community
            case COLLECTIONS: {
                if (uid.equals(":ID:")) {
                    entities.add(new CollectionEntity());
                } else {
                    Community res = Community.find(context, Integer.parseInt(uid));
                    for (Collection o : res.getCollections()) {
                        entities.add(idOnly ? new CollectionEntityId(o) : new CollectionEntity(o,1, uparams));
                    }
                }
            }
            break;

            // returns recent submissions in community
            case RECENT_SUBMISSIONS: {
                if (uid.equals(":ID:")) {
                    entities.add(new ItemEntity());
                } else {
                    Community res = Community.find(context, Integer.parseInt(uid));
                    RecentSubmissionsManager rsm = new RecentSubmissionsManager(context);
                    RecentSubmissions recent = rsm.getRecentSubmissions(res);
                    for (Item i : recent.getRecentSubmissions()) {
                        entities.add(idOnly ? new ItemEntityId(i) : new ItemEntity(i,1, uparams));
                    }
                }
            }
            break;

            // returns communities involved in collection
            case COMMUNITIES_INVOLVED: {
                if (uid.equals(":ID:")) {
                    entities.add(new CommunityEntity());
                } else {
                    Collection col = Collection.find(context, Integer.parseInt(uid));
                    for (Community o : col.getCommunities()) {
                        entities.add(idOnly ? new CommunityEntityId(o) : new CommunityEntity(o,1, uparams));
                    }
                }
            }
            break;

            // returns items in relation with collectin
            case ITEMS_INVOLVED: {
                if (uid.equals(":ID:")) {
                    entities.add(new ItemEntity());
                } else {
                    Collection col = Collection.find(context, Integer.parseInt(uid));
                    ItemIterator i = immediateOnly ? col.getItems() : col.getAllItems();
                    while (i.hasNext()) {
                        entities.add(idOnly ? new ItemEntityId(i.next()) : new ItemEntity(i.next(),1, uparams));
                    }
                }
            }
            break;

            // returns current user permissions on item
            case ITEM_PERMISSION: {
                if (uid.equals(":ID:")) {
                    entities.add(true);
                } else {
                    Item i = Item.find(context, Integer.parseInt(uid));
                    entities.add(i.canEdit());
                }
            }
            break;

            // returns communities in which item is member
            case ITEM_IN_COMMUNITIES: {
                if (uid.equals(":ID:")) {
                    entities.add(new CommunityEntity());
                } else {
                    Item i = Item.find(context, Integer.parseInt(uid));
                    for (Community o : i.getCommunities()) {
                        entities.add(idOnly ? new CommunityEntityId(o) : new CommunityEntity(o,1, uparams));
                    }
                }
            }
            break;

            // returns collections in which item is contained
            case ITEM_IN_COLLECTIONS: {
                if (uid.equals(":ID:")) {
                    entities.add(new CollectionEntity());
                } else {
                    Item i = Item.find(context, Integer.parseInt(uid));
                    for (Collection o : i.getCollections()) {
                        entities.add(idOnly ? new CollectionEntityId(o) : new CollectionEntity(o,1, uparams));
                    }
                }
            }


        }

        if (entities.size() == 0) {
            throw new EntityException("No members", uid, 204);
        }
        return entities;

    }

    /**
     * This method forwards to basic getObjects method, used by some other providers
     */
    public static List<Object> getObjects(String uid, Context context, int method, boolean idOnly, RequestParameters uparams) throws SQLException, RecentSubmissionsException {
        return getObjects(uid, context, method, idOnly, false, uparams);
    }

    /**
     * This method forwards to basic getObjects method, used by some other providers
     */
    public static List<Object> getObjects(String uid, Context context, int method, RequestParameters uparams) throws SQLException, RecentSubmissionsException {
        return getObjects(uid, context, method, false, false, uparams);
    }

    public static void addParameters(String function, Class<?>[] parameters) {
        mappings_parameters.put(function, parameters);
    }

    public static Class<?>[] getParameters(String function) {
        return mappings_parameters.get(function);
    }

    public static void addMethod(String field, String function) {
        mappings_rev.put(field, function);
    }

    public static String getMethod(String field) {
        return mappings_rev.get(field);
    }

    public static void debug(String output) {
        if (DEBUG_ACTIVE) {
            System.out.println(output);
        }
    }

    // check if we are deep into loop
    // in this case return false to start issuing entity ids only
//    public static boolean checkLevel(int level) {
//        //level = 2;
//        //level++;
//        boolean includeFull = false;
//        try {
//            StackTraceElement[] ste = new Throwable().getStackTrace();
//            System.out.println(ste[2].getClassName() + "\n    " + ste[1].getClassName() + "\n   -" + ste[0].getClassName() );
//            if ((ste.length > 1) && ((ste[level].getClassName().contains("org.dspace.rest.providers")) || (ste[level + 1].getClassName().contains("org.dspace.rest.providers")))) {
////            if ((ste.length > 1) && ((ste[level + 1].getClassName().contains("org.dspace.rest.providers")))) {
//                includeFull = true;
//            }
//
//        } catch (NullPointerException ex) {
//            System.out.println(ex.getMessage());
//        }
//        includeFull = false;
//        return includeFull;
//    }
}
