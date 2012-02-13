package org.dspace.rest.providers;

import java.sql.SQLException;

import org.dspace.core.Context;
import org.dspace.rest.diagnose.Operation;
import org.dspace.rest.entities.CommunityEntity;
import org.dspace.rest.entities.DetailDepth;
import org.dspace.rest.params.Parameters;

class CommunitiesAttributeValuer extends DirectAttributeValuer {
    @Override
    protected Object value(String id, Parameters parameters, Context context,
            String attributeSegment) throws SQLException {
        final DetailDepth depth = parameters.getDetailDepth().getDepth();
        if ("id".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getId();
        } else if ("name".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getName();
        } else if ("countItems".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getCountItems();
        } else if ("handle".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getHandle();
        } else if ("type".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getType();
        } else if ("collections".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getCollections();
        } else if ("canedit".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getCanEdit();
        } else if ("anchestor".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getParentCommunity();
        } else if ("children".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getSubCommunities();
        } else if ("recent".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getRecentSubmissions();
        } else if ("shortDescription".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getShortDescription();
        } else if ("copyrightText".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getCopyrightText();
        } else if ("sidebarText".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getSidebarText();
        } else if ("introductoryText".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getIntroductoryText();
        } else if ("logo".equals(attributeSegment)) {
            return new CommunityEntity(id, context, 1, depth).getLogo();
        } else {
            return null;
        }
    }
 
    protected Operation operation() {
        return Operation.GET_COMMUNITIES;
    }
}