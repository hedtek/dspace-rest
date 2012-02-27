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

import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.dspace.eperson.Group;
import org.dspace.rest.data.base.DetailDepth;

/**
 * Entity describing users registered on the system
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class GroupEntity extends GroupEntityId {

    private static List<Object> buildMembers(Group egroup,
            final boolean includeFullNextLevel) {
        final List<Object> members = new ArrayList<Object>();
        for (EPerson member : egroup.getMembers()) {
            members.add(includeFullNextLevel ? new UserEntity(member) : new UserEntityId(member.getID()));
        }
        return members;
    }

    private static List<Object> buildMemberGroups(Group egroup, int level,
            final DetailDepth depth, final boolean includeFullNextLevel)
            throws SQLException {
        final List<Object> memberGroups = new ArrayList<Object>();
        for (Group group : egroup.getMemberGroups()) {
            memberGroups.add(includeFullNextLevel ? new GroupEntity(group, level, depth) : new GroupEntityId(group));
        }
        return memberGroups;
    }
    
    private final String name;
    private final String handle;
    private final int type;
    private final boolean isEmpty;
    private final List<Object> memberGroups;
    private final List<Object> members;

    public GroupEntity(String uid, Context context, final DetailDepth depth) throws SQLException {
        this(Group.find(context, Integer.parseInt(uid)), 1, depth);
    }

    public GroupEntity(Group egroup, int level, final DetailDepth depth) throws SQLException {
        super(egroup);

        // Only include full when above maximum depth
        final boolean includeFullNextLevel = depth.includeFullDetails(++level);

        this.handle = egroup.getHandle();
        this.name = egroup.getName();
        this.type = egroup.getType();
        this.isEmpty = egroup.isEmpty();
        this.members = buildMembers(egroup, includeFullNextLevel);
        this.memberGroups = buildMemberGroups(egroup, level, depth,
                includeFullNextLevel);
    }

    public String getName() {
        return this.name;
    }

    public String getHandle() {
        return this.handle;
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
}
