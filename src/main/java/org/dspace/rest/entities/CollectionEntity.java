/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.entities;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.core.Context;
import org.dspace.eperson.Group;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityFieldRequired;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;
import org.sakaiproject.entitybus.exception.EntityException;

/**
 * Entity describing collection
 * @see CollectionEntityId
 * @see Collection
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CollectionEntity {

    private static Logger log = Logger.getLogger(CollectionEntity.class);
    
    @EntityId
    private int id;
    @EntityFieldRequired
    private String name;
    @EntityFieldRequired
    private Boolean canEdit;
    private String handle, licence;
    private int type;
    private int countItems;
    List<Object> items = new ArrayList<Object>();
    List<Object> communities = new ArrayList<Object>();
    private String short_description;
    private String intro_text;
    private String copyright_text;
    private String sidebar_text;
    private String provenance;
    private Object logo;

    public CollectionEntity(final String uid, final Context context, final int level, final DetailDepth depth) throws SQLException {
        this(Collection.find(context, Integer.parseInt(uid)), level, depth);
    }

    public CollectionEntity(final Collection collection, int level, final DetailDepth depth) throws SQLException {
        log.debug("Creating collection entity.");
        // Only include full when above maximum depth
        final boolean includeFullNextLevel = depth.includeFullDetails(++level);
        if (log.isDebugEnabled()) log.debug("DepthDetail is " + depth + "; include full? " + includeFullNextLevel + "; next level " + level);
        loadCollectionData(collection);
        
        final ItemIterator i = collection.getAllItems();
        this.items = ItemBuilder.builder(!includeFullNextLevel, depth).build(i, level);
        this.countItems = items.size();

        for (Community c : collection.getCommunities()) {
            communities.add(includeFullNextLevel ? new CommunityEntity(c, level, depth) : new CommunityEntityId(c));
        }
    }
    
    private void loadCollectionData(Collection collection) throws SQLException {
    	this.id = collection.getID();
        this.canEdit = collection.canEditBoolean();
        this.handle = collection.getHandle();
        this.name = collection.getName();
        this.type = collection.getType();
        this.licence = collection.getLicense();
        this.short_description = collection.getMetadata("short_description");
        this.intro_text = collection.getMetadata("introductory_text");
        this.copyright_text = collection.getMetadata("copyright_text");
        this.sidebar_text = collection.getMetadata("side_bar_text");
        this.provenance = collection.getMetadata("provenance_description");
        if (collection.getLogo() != null) {
            this.logo = new BitstreamEntityId(collection.getLogo());
        }
        
    }

    public CollectionEntity() {
        // check calling package/class in order to prevent chaining
        boolean includeFull = false;

        this.canEdit = false;
        this.handle = "123456789/0";
        this.name = "Sample collection";
        this.type = 4;
        this.id = 92;
        this.countItems = 10921;
        this.licence = "Example licence";
        this.items.add(new ItemEntity());
        this.communities.add(includeFull ? new CommunityEntity() : new CommunityEntityId());
    }

    public String getLicence() {
        return this.licence;
    }

    public List<?> getItems() {
        return this.items;
    }

    public List<?> getCommunities() {
        return this.communities;
    }

    public String getName() {
        return this.name;
    }

    public String getHandle() {
        return this.handle;
    }

    public boolean getCanEdit() {
        return this.canEdit;
    }

    public int getId() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }

    public int getCountItems() {
        return this.countItems;
    }

    public String getShortDescription() {
        return this.short_description;
    }

    public String getIntroText() {
        return this.intro_text;
    }

    public String getCopyrightText() {
        return this.copyright_text;
    }

    public String getSidebarText() {
        return this.sidebar_text;
    }

    public String getProvenance() {
        return this.provenance;
    }

    public Object getLogo() {
        return this.logo;
    }

    @Override
    public String toString() {
        return "collection id:" + this.id + ", stuff.....";
    }

    public Object setName(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("value")) {
            setMetadata(Integer.parseInt(ref.getId()), context, "name", inputVar.get("value").toString());
        } else {
            throw new EntityException("Bad request", "Value not included", 400);
        }
        return null;
    }

    public Object setProvenance(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("value")) {
            setMetadata(Integer.parseInt(ref.getId()), context, "provenance_description", inputVar.get("value").toString());
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

    public Object setIntroText(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("value")) {
            setMetadata(Integer.parseInt(ref.getId()), context, "intro_text", inputVar.get("value").toString());
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

    public Object setLicence(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("value")) {
            setMetadata(Integer.parseInt(ref.getId()), context, "licence", inputVar.get("value").toString());
        } else {
            throw new EntityException("Bad request", "Value not included", 400);
        }
        return null;
    }

    protected void setMetadata(int id, Context context, String name, String value) {
        try {
            Collection col = Collection.find(context, id);
            if (col != null) {

                col.setMetadata(name, value);
                try {
                    col.update();
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

    public String createAdministrators(EntityReference ref, Map<String, Object> inputVar, Context context) {
        String id = "";
        try {
            id = (String) inputVar.get("id");
        } catch (NullPointerException ex) {
            throw new EntityException("Bad request", "Value not included", 400);
        }

        try {
            Collection col = Collection.find(context, Integer.parseInt(id));
            if (col != null) {
                col.createAdministrators();
                Group group = col.getAdministrators();
                return Integer.toString(group.getID());
            } else {
                throw new EntityException("Not found", "Entity not found", 404);
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ex) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        }
    }

    public String createSubmitters(EntityReference ref, Map<String, Object> inputVar, Context context) {
        String id = "";
        try {
            id = (String) inputVar.get("id");
        } catch (NullPointerException ex) {
            throw new EntityException("Bad request", "Value not included", 400);
        }

        try {
            Collection col = Collection.find(context, Integer.parseInt(id));
            if (col != null) {
                col.createSubmitters();
                Group group = col.getSubmitters();
                return Integer.toString(group.getID());
            } else {
                throw new EntityException("Not found", "Entity not found", 404);
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ex) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        }
    }

    public String createTemplateItem(EntityReference ref, Map<String, Object> inputVar, Context context) {
        String id = "";
        try {
            id = (String) inputVar.get("id");
        } catch (NullPointerException ex) {
            throw new EntityException("Bad request", "Value not included", 400);
        }

        try {
            Collection col = Collection.find(context, Integer.parseInt(id));
            if (col != null) {
                col.createTemplateItem();
                Item item = col.getTemplateItem();
                return Integer.toString(item.getID());
            } else {
                throw new EntityException("Not found", "Entity not found", 404);
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ex) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        }
    }

    public String createWorkflowGroup(EntityReference ref, Map<String, Object> inputVar, Context context) {
        String id = "";
        String step = "";
        try {
            id = (String) inputVar.get("id");
            step = (String) inputVar.get("step");
        } catch (NullPointerException ex) {
            throw new EntityException("Bad request", "Value not included", 400);
        }

        try {
            Collection col = Collection.find(context, Integer.parseInt(id));
            if (col != null) {
                col.createWorkflowGroup(Integer.parseInt(step));
                Group group = col.getWorkflowGroup(Integer.parseInt(step));
                return Integer.toString(group.getID());
            } else {
                throw new EntityException("Not found", "Entity not found", 404);
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ex) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        }
    }

    public void removeAdministrators(EntityReference ref, Map<String, Object> inputVar, Context context) {
        try {
            Integer elid = Integer.parseInt((String) inputVar.get("id"));
            Collection col = Collection.find(context, elid);
            if ((col != null)) {
                col.removeAdministrators();
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ae) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        } catch (NumberFormatException ex) {
            throw new EntityException("Bad request", "Could not parse input", 400);
        }
    }

    public void removeSubmitters(EntityReference ref, Map<String, Object> inputVar, Context context) {
        try {
            Integer elid = Integer.parseInt((String) inputVar.get("id"));
            Collection col = Collection.find(context, elid);
            if ((col != null)) {
                col.removeSubmitters();
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ae) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        } catch (NumberFormatException ex) {
            throw new EntityException("Bad request", "Could not parse input", 400);
        }
    }

    public void removeTemplateItem(EntityReference ref, Map<String, Object> inputVar, Context context) {
        try {
            Integer elid = Integer.parseInt((String) inputVar.get("id"));
            Collection col = Collection.find(context, elid);
            if ((col != null)) {
                col.removeTemplateItem();
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
        } catch (AuthorizeException ae) {
            throw new EntityException("Forbidden", "Forbidden", 403);
        } catch (NumberFormatException ex) {
            throw new EntityException("Bad request", "Could not parse input", 400);
        } catch (IOException ie) {
            throw new EntityException("Internal server error", "SQL error, cannot remove template item", 500);
        }
    }
}
