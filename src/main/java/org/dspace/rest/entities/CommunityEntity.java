/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import org.sakaiproject.entitybus.entityprovider.annotations.EntityFieldRequired;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityTitle;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityParameters;
import org.sakaiproject.entitybus.EntityView;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.exception.EntityException;
import org.dspace.authorize.AuthorizeException;
import org.dspace.rest.params.RequestParameters;
import org.dspace.rest.util.RecentSubmissionsManager;
import org.dspace.rest.util.RecentSubmissions;
import org.dspace.rest.util.RecentSubmissionsException;

//import javassist.*;
import org.dspace.content.Community;
import org.dspace.content.Collection;
import org.dspace.content.Item;
import org.dspace.core.Context;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import org.dspace.eperson.Group;
import java.io.IOException;
import org.dspace.rest.util.UtilHelper;
import org.dspace.rest.entities.BitstreamEntity;
import org.dspace.rest.entities.BitstreamEntityId;

/**
 * Entity describing community, basic version
 * @see CommunityEntityId
 * @see Community
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CommunityEntity extends CommunityEntityId {

    @EntityId
    private int id;
    @EntityFieldRequired
    public String name;
    @EntityFieldRequired
    private Boolean canEdit;
    private String handle;
    private int type;
    private int countItems;
    List<Object> collections = new ArrayList<Object>();
    List<Object> subCommunities = new ArrayList<Object>();
    List<Object> recentSubmissions = new ArrayList<Object>();
    Object administrators;
    Object parent;
    Context context;
    private String short_description, introductory_text, copyright_text, side_bar_text;
    private Object logo;

    public CommunityEntity(String uid, Context context, int level, RequestParameters uparams) throws SQLException {
        System.out.println("creating community main");
        this.context = context;
        try {
            Community res = Community.find(context, Integer.parseInt(uid));
            this.id = res.getID();
            this.canEdit = res.canEditBoolean();
            this.handle = res.getHandle();
            this.name = res.getName();
            this.type = res.getType();
            boolean includeFull = false;
            level++;
            if (level <= uparams.getDetail()) {
                includeFull = true;
            }

            if (res.getLogo() != null)
            //this.logo = includeFull ? new BitstreamEntity(Integer.toString(res.getLogo().getID()), context, level, uparams) : new BitstreamEntityId(Integer.toString(res.getLogo().getID()), context);
            this.logo = new BitstreamEntityId(Integer.toString(res.getLogo().getID()), context);
            this.short_description = res.getMetadata("short_description");
            this.introductory_text = res.getMetadata("introductory_text");
            this.copyright_text = res.getMetadata("copyright_text");
            this.side_bar_text = res.getMetadata("side_bar_text");

                Collection[] cols = res.getCollections();
            for (Collection c : cols) {
                this.collections.add(includeFull ? new CollectionEntity(c, level, uparams) : new CollectionEntityId(c));
            }
            
            Community[] coms = res.getSubcommunities();
            for (Community c : coms) {
                this.subCommunities.add(includeFull ? new CommunityEntity(c, level, uparams) : new CommunityEntityId(c));
            }
            try {
                Community parentCommunity = res.getParentCommunity();
                if(parentCommunity == null) {
                    this.parent = null;
                } else {
                    if(includeFull) {
                        this.parent = new CommunityEntity(parentCommunity, level, uparams);
                    } else {
                        this.parent = new CommunityEntityId(parentCommunity);
                    }
                }

            } catch (NullPointerException ex) {
                this.parent = null;
            }
            RecentSubmissionsManager rsm = new RecentSubmissionsManager(context);
            try {
                RecentSubmissions recent = rsm.getRecentSubmissions(res);
                for (Item i : recent.getRecentSubmissions()) {
                    this.recentSubmissions.add(includeFull ? new ItemEntity(i, level, uparams) : new ItemEntityId(i));
                }
            } catch (RecentSubmissionsException ex) {
            }
        } catch (NumberFormatException ex) {
        }
        //context.complete(); //<-important
    }

    public CommunityEntity(Community community, int level, RequestParameters uparams) throws SQLException {
        System.out.println("creating community 2");
        // check calling package/class in order to prevent chaining
        boolean includeFull = false;
        level++;
        if (level <= uparams.getDetail()) {
            includeFull = true;
        }

        this.canEdit = community.canEditBoolean();
        this.handle = community.getHandle();
        this.name = community.getName();
        this.type = community.getType();
        this.id = community.getID();
        this.countItems = community.countItems();
        this.short_description = community.getMetadata("short_description");
        this.introductory_text = community.getMetadata("introductory_text");
        this.copyright_text = community.getMetadata("copyright_text");
        this.side_bar_text = community.getMetadata("side_bar_text");

        if (community.getLogo() != null)
        //this.logo = includeFull ? new BitstreamEntity(community.getLogo(), level, uparams) : new BitstreamEntityId(community.getLogo());
        this.logo = new BitstreamEntityId(community.getLogo());
        
        Collection[] cols = community.getCollections();
        for (Collection c : cols) {
            this.collections.add(includeFull ? new CollectionEntity(c, level, uparams) : new CollectionEntityId(c));
        }
        Community[] coms = community.getSubcommunities();
        for (Community c : coms) {
            this.subCommunities.add(includeFull ? new CommunityEntity(c, level, uparams) : new CommunityEntityId(c));
        }
        try {
            this.parent = includeFull ? new CommunityEntity(community.getParentCommunity(), level, uparams) : new CommunityEntityId(community.getParentCommunity());
        } catch (NullPointerException ne) {
            this.parent = null;
        }
    }

    public CommunityEntity() {
        // check calling package/class in order to prevent chaining
        boolean includeFull = false;
        this.canEdit = true;
        this.handle = "123456789/0";
        this.name = "Community Name";
        this.type = 5;
        this.id = 6;
        this.countItems = 1001;
        this.collections.add(includeFull ? new CollectionEntity() : new CollectionEntityId());
        this.subCommunities.add(includeFull ? new CommunityEntity() : new CommunityEntityId());
        this.parent = includeFull ? new CommunityEntity() : new CommunityEntityId();
        this.short_description = "short description";
        this.introductory_text = "introductory_text";
        this.copyright_text = "copyright_text";
        this.side_bar_text = "side_bar_text";
    }

    public List<?> getCollections() {
        return this.collections;
    }

    public List<?> getSubCommunities() {
        return this.subCommunities;
    }

    public Object getParentCommunity() {
        return this.parent;
    }

    public String getName() {
        return this.name;
    }

    @EntityTitle
    public String getHandle() {
        return this.handle;
    }

    public boolean getCanEdit() {
        return this.canEdit;
    }

    public Object getLogo() {
        return this.logo;
        //return null;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }

    public int getCountItems() {
        return this.countItems;
    }

    public List<?> getRecentSubmissions() {
        return this.recentSubmissions;
    }

    public Object getAdministrators() {
        return this.administrators;
    }

    public String getShortDescription() {
        return this.short_description;
    }

    public String getCopyrightText() {
        return this.copyright_text;
    }

    public String getSidebarText() {
        return this.side_bar_text;
    }

    public String getIntroductoryText() {
        return this.introductory_text;
    }

    public Object setName(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("value")) {
            setMetadata(Integer.parseInt(ref.getId()), context, "name", inputVar.get("value").toString());
        } else {
            throw new EntityException("Bad request", "Value not included", 400);
        }
        return null;
    }

    public Object setShortDescription(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("value")) {
            setMetadata(Integer.parseInt(ref.getId()), context, "short_description", inputVar.get("value").toString());
        } else {
            throw new EntityException("Bad request", "Value not included", 400);
        }
        return null;
    }

    public Object setCopyrightText(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("value")) {
            setMetadata(Integer.parseInt(ref.getId()), context, "copyright_text", inputVar.get("value").toString());
        } else {
            throw new EntityException("Bad request", "Value not included", 400);
        }
        return null;
    }

    public Object setSidebarText(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("value")) {
            setMetadata(Integer.parseInt(ref.getId()), context, "side_bar_text", inputVar.get("value").toString());
        } else {
            throw new EntityException("Bad request", "Value not included", 400);
        }
        return null;
    }

    public Object setIntroductoryText(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("value")) {
            setMetadata(Integer.parseInt(ref.getId()), context, "introductory_text", inputVar.get("value").toString());
        } else {
            throw new EntityException("Bad request", "Value not included", 400);
        }
        return null;
    }

    protected void setMetadata(int id, Context context, String name, String value) {
        try {
            Community com = Community.find(context, id);
            if (com != null) {
                com.setMetadata(name, value);
                try {
                    com.update();
                } catch (IOException ex) {
                    throw new EntityException("Internal server error", "SQL error, cannot update collection", 500);
                } catch (AuthorizeException ex) {
                    throw new EntityException("Forbidden", "Forbidden", 403);
                }
            } else {
                throw new EntityException("Not found", "Entity not found", 404);
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);

        }
    }

    public Object addCollection(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("cid")) {
            try {
                Integer cid = 0;
                if (inputVar.get("cid").getClass().equals(Integer.class)) {
                    cid = (Integer) inputVar.get("cid");
                } else {
                    cid = Integer.parseInt(inputVar.get("cid").toString());
                }
                Collection col = Collection.find(context, cid);
                Community com = Community.find(context, Integer.parseInt(ref.getId()));
                if ((com != null) && (col != null)) {
                    com.addCollection(col);
//                    System.out.println("com:col" + com.getName() + col.getName());
                    return col.getID();
                } else {
                    throw new EntityException("Not found", "Entity not found", 404);
                }
            } catch (SQLException ex) {
                throw new EntityException("Internal server error", "SQL error", 500);
            } catch (AuthorizeException ex) {
                throw new EntityException("Forbidden", "Forbidden", 403);
            }
        }
        return null;
    }

    public Object addSubcommunity(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("cid")) {
            try {
                if (ref.getId().equals(inputVar.get("cid").toString())) {
                    throw new EntityException("Bad request", "Community cannot be subcommunity to itself", 400);
                }
                Integer cid = 0;
                if (inputVar.get("cid").getClass().equals(Integer.class)) {
                    cid = (Integer) inputVar.get("cid");
                } else {
                    cid = Integer.parseInt(inputVar.get("cid").toString());
                }
                Community com = Community.find(context, Integer.parseInt(ref.getId()));
                Community sub = Community.find(context, cid);


                if ((com != null) && (sub != null)) {
                    com.addSubcommunity(sub);
                    return com.getID();
                } else {
                    throw new EntityException("Not found", "Entity not found", 404);
                }
            } catch (SQLException ex) {
                throw new EntityException("Internal server error", "SQL error", 500);
            } catch (AuthorizeException ex) {
                throw new EntityException("Forbidden", "Forbidden", 403);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "id:" + this.id + ", stuff.....";
    }

    // TODO: check integer/string fields for id
    public String createCollection(EntityReference ref, Map<String, Object> inputVar, Context context) {
        String result = "";
        String id = "";
        String name = "";
        try {
            if (inputVar.get("id").getClass().equals(Integer.class)) {
                id = Integer.toString((Integer) inputVar.get("id"));
            } else {
                id = (String) inputVar.get("id");
            }
            name = (String) inputVar.get("name");
        } catch (NullPointerException ex) {
            throw new EntityException("Bad request", "Value not included", 400);
        }

        try {
            Community com = Community.find(context, Integer.parseInt(id));
            Collection col = com.createCollection();
            if (col != null) {
                result = Integer.toString(col.getID());
                col.setMetadata("name", name);
                //System.out.println("create, community " + com.getID() + " collection " + col.getID());
                col.update();
            } else {
                throw new EntityException("Internal server error", "Could not create collection", 501);
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ex) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        } catch (IOException ex) {
            throw new EntityException("Internal server error", "SQL error, cannot update collection", 500);
        }
        return result;
    }

    public String createSubcommunity(EntityReference ref, Map<String, Object> inputVar, Context context) {
        String result = "";
        String id = "";
        String name = "";
        try {
            if (inputVar.get("id").getClass().equals(Integer.class)) {
                id = Integer.toString((Integer) inputVar.get("id"));
            } else {
                id = (String) inputVar.get("id");
            }
            name = (String) inputVar.get("name");
        } catch (NullPointerException ex) {
            throw new EntityException("Bad request", "Value not included", 400);
        }

        try {
            Community com = Community.find(context, Integer.parseInt(id));
            Community sub = com.createSubcommunity();
            if (sub != null) {
                result = Integer.toString(sub.getID());
                sub.setMetadata("name", name);
                sub.update();
            } else {
                throw new EntityException("Internal server error", "Could not create subcommunity", 500);
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ex) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        } catch (IOException ex) {
            throw new EntityException("Internal server error", "SQL error, cannot update collection", 500);
        }
        return result;
    }

    public void removeChildren(EntityReference ref, Map<String, Object> inputVar, Context context) {
        try {
            Integer comid = Integer.parseInt((String) inputVar.get("id"));
            Integer chid = Integer.parseInt((String) inputVar.get("eid"));
            Community com = Community.find(context, comid);
            Community subcom = Community.find(context, chid);
            if ((com != null) && (subcom != null)) {
                com.removeSubcommunity(subcom);
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ae) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        } catch (IOException ie) {
            throw new EntityException("Internal server error", "SQL error, cannot remove subcommunity", 500);
        } catch (NumberFormatException ex) {
            throw new EntityException("Bad request", "Could not parse input", 400);
        }

    }

    public void removeSubcollections(EntityReference ref, Map<String, Object> inputVar, Context context) {
        try {
            Integer comid = Integer.parseInt((String) inputVar.get("id"));
            Integer colid = Integer.parseInt((String) inputVar.get("eid"));
            Community com = Community.find(context, comid);
            Collection subcol = Collection.find(context, colid);
            if ((com != null) && (subcol != null)) {
                com.removeCollection(subcol);
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ae) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        } catch (IOException ie) {
            throw new EntityException("Internal server error", "SQL error, cannot remove subcommunity", 500);
        } catch (NumberFormatException ex) {
            throw new EntityException("Bad request", "Could not parse input", 400);
        }

    }

    public void delete(EntityReference ref, Map<String, Object> inputVar, Context context) {
        try {
            Integer elid = Integer.parseInt((String) inputVar.get("id"));
            Community com = Community.find(context, elid);
            if ((com != null)) {
                com.delete();
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ae) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        } catch (IOException ie) {
            throw new EntityException("Internal server error", "SQL error, cannot remove subcommunity", 500);
        } catch (NumberFormatException ex) {
            throw new EntityException("Bad request", "Could not parse input", 400);
        }
    }
}
