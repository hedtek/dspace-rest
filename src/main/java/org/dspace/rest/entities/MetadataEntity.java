/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.*;
import org.dspace.content.Collection;
import org.dspace.content.crosswalk.DisseminationCrosswalk;
import org.dspace.core.Context;
import org.dspace.rest.util.UserRequestParams;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityFieldRequired;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;
import org.sakaiproject.entitybus.exception.EntityException;

import java.sql.SQLException;
import java.util.*;

/**
 * Entity describing DCValue
 * @see org.dspace.rest.entities.ItemEntityId
 * @see org.dspace.content.DCValue
 * @author Bojan Suzic, bojan.suzic@gmail.com
 * @author Robin Taylor
 */
public class MetadataEntity extends MetadataEntityId {

    @EntityId
    private int id;
    @EntityFieldRequired
    private String name;

    private String element;
    private String qualifier;
    private String schema;
    private String value;

    /** log4j category */
    private static final Logger log = Logger.getLogger(MetadataEntity.class);


    public MetadataEntity(String uid, Context context, int level, UserRequestParams uparams) throws SQLException
    {
        // DSpace doesn't currently allow for the instantiation of DCValue outwith an Item,
        // so we should never get here.

        log.error("Error - illegal instantiation of MetadataEntity");
        throw new EntityException("Error - illegal instantiation of MetadataEntity", null);

    }

    public MetadataEntity(DCValue dcValue, int level, UserRequestParams uparams) throws SQLException
    {
        // check calling package/class in order to prevent chaining

        this.element = dcValue.element;
        this.qualifier = dcValue.qualifier;
        this.schema = dcValue.schema;
        this.value = dcValue.value;

    }

    public MetadataEntity()
    {
        // check calling package/class in order to prevent chaining

        this.element = "example element";
        this.qualifier = "example qualifier";
        this.schema = "example schema";
        this.value = "example value";

    }


    public String getElement()
    {
        return this.element;
    }

    public String getQualifier()
    {
        return qualifier;
    }

    public String getSchema()
    {
        return schema;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public String toString() {
        return "id:" + this.id + ", stuff.....";
    }

}
