/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import org.dspace.content.*;
import org.dspace.content.Collection;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityFieldRequired;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;
import org.sakaiproject.entitybus.exception.EntityException;
import org.dspace.authorize.AuthorizeException;

import java.util.*;

import org.dspace.content.crosswalk.*;
import org.sakaiproject.entitybus.EntityView;
import org.sakaiproject.entitybus.EntityReference;
import org.dspace.core.Context;

import java.sql.SQLException;
import java.io.StringWriter;
import org.jdom.output.XMLOutputter;
import org.jdom.Element;
import org.dspace.rest.util.UtilHelper;
import org.dspace.rest.util.UserRequestParams;

/**
 * Entity describing item
 * @see ItemEntityId
 * @see Item
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class ItemEntity extends ItemEntityId {

    @EntityId
    private int id;
    @EntityFieldRequired
    private String name;
    @EntityFieldRequired
    private Boolean canEdit;
    private String handle;
    private int type;
    List<Object> bundles = new ArrayList<Object>();
    List<Object> bitstreams = new ArrayList<Object>();
    List<Object> collections = new ArrayList<Object>();
    List<Object> communities = new ArrayList<Object>();
    List<Object> metadata = new ArrayList<Object>();
    //String metadata;
    Date lastModified;
    Object owningCollection;
    boolean isArchived, isWithdrawn;
    UserEntity submitter;
    private DisseminationCrosswalk xHTMLHeadCrosswalk;

    // TODO inspect and add additional fields
    public ItemEntity(String uid, Context context, int level, UserRequestParams uparams) throws SQLException {
        Item res = Item.find(context, Integer.parseInt(uid));
        this.id = res.getID();
        this.canEdit = res.canEdit();
        this.handle = res.getHandle();
        this.name = res.getName();
        this.type = res.getType();
        this.lastModified = res.getLastModified();
        this.isArchived = res.isArchived();
        this.isArchived = res.isWithdrawn();
        this.submitter = new UserEntity(res.getSubmitter());

        Bundle[] bun = res.getBundles();
        Bitstream[] bst = res.getNonInternalBitstreams();
        Collection[] col = res.getCollections();
        Community[] com = res.getCommunities();
        boolean includeFull = false;
        level++;
        if (level <= uparams.getDetail()) {
            includeFull = true;
        }

        Collection ownCol = res.getOwningCollection();
        if (ownCol != null) {
            this.owningCollection = includeFull ? new CollectionEntity(ownCol, level, uparams) : new CollectionEntityId(ownCol);
        }
        for (Bundle b : bun) {
            this.bundles.add(includeFull ? new BundleEntity(b, level, uparams) : new BundleEntityId(b));
        }
        for (Bitstream b : bst) {
            this.bitstreams.add(includeFull ? new BitstreamEntity(b, level, uparams) : new BitstreamEntityId(b));
        }
        for (Collection c : col) {
            this.collections.add(includeFull ? new CollectionEntity(c, level, uparams) : new CollectionEntityId(c));
        }
        for (Community c : com) {
            this.communities.add(includeFull ? new CommunityEntity(c, level, uparams) : new CommunityEntityId(c));
        }

        DCValue[] dcValues = res.getMetadata(Item.ANY, Item.ANY, Item.ANY, Item.ANY);
        for (DCValue dcValue : dcValues)
        {
            this.metadata.add(includeFull ? new MetadataEntity(dcValue, level, uparams) : new MetadataEntityId(dcValue));
        }
    }

    public ItemEntity(Item item, int level, UserRequestParams uparams) throws SQLException {
        // check calling package/class in order to prevent chaining
        boolean includeFull = false;
        level++;
        if (level <= uparams.getDetail()) {
            includeFull = true;
        }

        this.canEdit = item.canEdit();
        this.handle = item.getHandle();
        this.name = item.getName();
        this.type = item.getType();
        this.id = item.getID();
        this.lastModified = item.getLastModified();
        Collection ownCol = item.getOwningCollection();
        if (ownCol != null) {
            this.owningCollection = includeFull ? new CollectionEntity(ownCol, level, uparams) : new CollectionEntityId(ownCol);
        }
        this.isArchived = item.isArchived();
        this.isWithdrawn = item.isWithdrawn();
        this.submitter = new UserEntity(item.getSubmitter());

        Bundle[] bun = item.getBundles();
        Bitstream[] bst = item.getNonInternalBitstreams();
        Collection[] col = item.getCollections();
        Community[] com = item.getCommunities();
        for (Bundle b : bun) {
            this.bundles.add(includeFull ? new BundleEntity(b, level, uparams) : new BundleEntityId(b));
        }
        for (Bitstream b : bst) {
            this.bitstreams.add(includeFull ? new BitstreamEntity(b, level, uparams) : new BitstreamEntityId(b));
        }
        for (Collection c : col) {
            this.collections.add(includeFull ? new CollectionEntity(c, level, uparams) : new CollectionEntityId(c));
        }
        for (Community c : com) {
            this.communities.add(includeFull ? new CommunityEntity(c, level, uparams) : new CommunityEntityId(c));
        }

        DCValue[] dcValues = item.getMetadata(Item.ANY, Item.ANY, Item.ANY, Item.ANY);
        for (DCValue dcValue : dcValues)
        {
            this.metadata.add(includeFull ? new MetadataEntity(dcValue, level, uparams) : new MetadataEntityId(dcValue));
        }

    }

    public ItemEntity() {
        // check calling package/class in order to prevent chaining
        boolean includeFull = false;
        this.canEdit = false;
        this.handle = "123456789/0";
        this.name = "Item";
        this.type = 3;
        this.id = 22;
        this.bundles.add(includeFull ? new BundleEntity() : new BundleEntityId());
        this.bitstreams.add(includeFull ? new BitstreamEntity() : new BundleEntityId());
        this.collections.add(includeFull ? new CollectionEntity() : new BundleEntityId());
        this.communities.add(includeFull ? new CommunityEntity() : new BundleEntityId());
        this.metadata.add(includeFull ? new MetadataEntity() : new MetadataEntityId());
    }

    /*
    // taken from jspui handle implementation
    // it should be probably properly formated, as HashMap
    // for example but currently HashMap is not supported
    public String prepareMetadata(Item res) {
        String headMetadata = "";

        try {
            xHTMLHeadCrosswalk = new XHTMLHeadDisseminationCrosswalk();
            List l = xHTMLHeadCrosswalk.disseminateList(res);
            StringWriter sw = new StringWriter();

            XMLOutputter xmlo = new XMLOutputter();
            for (int i = 0; i < l.size(); i++) {
                Element e = (Element) l.get(i);
                // FIXME: we unset the Namespace so it's not printed.
                // This is fairly yucky, but means the same crosswalk should
                // work for Manakin as well as the JSP-based UI.
                e.setNamespace(null);
                xmlo.output(e, sw);

            }
            headMetadata = sw.toString();
        } catch (Exception ce) {
            ce.printStackTrace();
        }

        return headMetadata;
    }



    public String getMetadata() {
        return this.metadata;
    }
    */


    public List getMetadata()
    {
        return this.metadata;
    }

    public UserEntity getSubmitter() {
        return this.submitter;
    }

    public boolean getIsArchived() {
        return this.isArchived;
    }

    public boolean getIsWithdrawn() {
        return this.isWithdrawn;
    }

    public Object getOwningCollection() {
        return this.owningCollection;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public List getCollections() {
        return this.collections;
    }

    public List getCommunities() {
        return this.communities;
    }

    public String getName() {
        return this.name;
    }

    public List getBitstreams() {
        return this.bitstreams;
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

    public List getBundles() {
        return this.bundles;
    }

    @Override
    public String toString() {
        return "id:" + this.id + ", stuff.....";
    }

    public Object addBundle(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("id")) {
            try {
                Item item = Item.find(context, Integer.parseInt(ref.getId().toString()));
                Bundle bundle = Bundle.find(context, Integer.parseInt(inputVar.get("id").toString()));
                if ((item != null) && (item != null)) {
                    item.addBundle(bundle);
                    return bundle.getID();
                } else {
                    throw new EntityException("Not found", "Entity not found", 404);
                }
            } catch (SQLException ex) {
                throw new EntityException("Internal server error", "SQL error", 500);
            } catch (AuthorizeException ex) {
                throw new EntityException("Forbidden", "Forbidden", 403);
            }
        } else {
            throw new EntityException("Bad request", "Value not included", 400);
        }
    }

    public String createBundle(EntityReference ref, Map<String, Object> inputVar, Context context) {
        String result = "";
        String id = "";
        String name = "";
        try {
            id = (String) inputVar.get("id");
            name = (String) inputVar.get("name");
        } catch (NullPointerException ex) {
            throw new EntityException("Bad request", "Value not included", 400);
        }

        try {
            Item item = Item.find(context, Integer.parseInt(id));
            if (item != null) {
                //Bundle bundle = item.createBundle(name);
                //bundle.setName(name);
                //item.update();
                //result = Integer.toString(bundle.getID());
            } else {
                throw new EntityException("Internal server error", "Could not create subcommunity", 500);
            }
        } catch (SQLException ex) {
            throw new EntityException("Internal server error", "SQL error", 500);
            //}        catch (AuthorizeException ex) {
            //throw new EntityException("Forbidden", "Forbidden", 403);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
