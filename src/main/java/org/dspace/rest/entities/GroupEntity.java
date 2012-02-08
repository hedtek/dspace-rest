/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.dspace.eperson.Group;
import org.dspace.rest.params.RequestParameters;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityFieldRequired;
import org.sakaiproject.entitybus.exception.EntityException;

/**
 * Entity describing users registered on the system
 * @see GroupEntityId
 * @see EGroup
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class GroupEntity extends GroupEntityId {

    @EntityFieldRequired
    private String name;
    private String handle;
    private int type;
    private boolean isEmpty;
    private List<Object> memberGroups = new ArrayList<Object>();
    private List<Object> members = new ArrayList<Object>();

    public GroupEntity(String uid, Context context, int level, RequestParameters uparams) throws SQLException {
        super(uid, context);
        this.handle = res.getHandle();
        this.name = res.getName();
        this.type = res.getType();
        this.isEmpty = res.isEmpty();

        // check calling package/class in order to prevent chaining
        boolean includeFull = false;
        level++;
        if (level <= uparams.getDetail()) {
            includeFull = true;
        }

        for (EPerson member : res.getMembers()) {
            members.add(includeFull ? new UserEntity(member) : new UserEntityId(member.getID()));
        }
        for (Group group : res.getMemberGroups()) {
            memberGroups.add(includeFull ? new GroupEntity(group, level, uparams) : new GroupEntityId(group));
        }
    }

    public GroupEntity(Group egroup, int level, RequestParameters uparams) throws SQLException {
        super(egroup);
        // check calling package/class in order to prevent chaining
        boolean includeFull = false;
        level++;
        if (level <= uparams.getDetail()) {
            includeFull = true;
        }

        this.handle = egroup.getHandle();
        this.name = egroup.getName();
        this.type = egroup.getType();
        this.isEmpty = egroup.isEmpty();
        for (EPerson member : egroup.getMembers()) {
            members.add(includeFull ? new UserEntity(member) : new UserEntityId(member.getID()));
        }
        for (Group group : egroup.getMemberGroups()) {
            memberGroups.add(includeFull ? new GroupEntity(group, level, uparams) : new GroupEntityId(group));
        }
    }

    public GroupEntity() {
        this.id = 111;
        this.handle = "123456789/0";
        this.name = "John";
        this.type = 7;
        this.isEmpty = true;
    }

    public String getName() {
        return this.name;
    }

    public String getHandle() {
        return this.handle;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }

    public boolean getIsEmpty() {
        return this.isEmpty;
    }

    public List<?> getMembers() {
        return this.members;
    }

    public List<?> getMemberGroups() {
        return this.memberGroups;
    }

    public Object setName(EntityReference ref, Map<String, Object> inputVar, Context context) {
        if (inputVar.containsKey("value")) {
            try {
                Group group = Group.find(context, Integer.parseInt(ref.getId()));
                if ((group != null)) {
                    group.setName(inputVar.get("value").toString());
                    return group.getID();
                } else {
                    throw new EntityException("Not found", "Entity not found", 404);
                }
            } catch (SQLException ex) {
                throw new EntityException("Internal server error", "SQL error", 500);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "id:" + this.id;
    }

    @Override
    public int compareTo(Object o1) {
        return ((GroupEntity) (o1)).getName().compareTo(this.getName()) * -1;
    }
}
