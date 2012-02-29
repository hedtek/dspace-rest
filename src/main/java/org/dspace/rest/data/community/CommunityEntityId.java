/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */


package org.dspace.rest.data.community;

import java.sql.SQLException;

import org.dspace.content.Community;
import org.dspace.rest.data.base.Entity;

/**
 * Entity describing community, basic version
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class CommunityEntityId extends Entity {

    public CommunityEntityId(int id) {
        super(id, Type.COMMUNITY);
    }

    public CommunityEntityId(Community community) throws SQLException {
        this(community.getID());
    }
}
