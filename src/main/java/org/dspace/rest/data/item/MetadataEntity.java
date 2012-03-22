/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.data.item;

import org.dspace.content.DCValue;

/**
 * Entity describing DCValue
 * @see org.dspace.rest.data.item.ItemEntityId
 * @see org.dspace.content.DCValue
 * @author Bojan Suzic, bojan.suzic@gmail.com
 * @author Robin Taylor
 */
public class MetadataEntity {

    private final String element;
    private final String qualifier;
    private final String schema;
    private final String value;

    public MetadataEntity(DCValue dcValue)
    {
    	this(dcValue.element, dcValue.qualifier,  dcValue.schema, dcValue.value);
    }

    public MetadataEntity(String element, String qualifier, String schema,
			String value) {
		super();
		this.element = element;
		this.qualifier = qualifier;
		this.schema = schema;
		this.value = value;
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
		return "MetadataEntity [element=" + element + ", qualifier="
				+ qualifier + ", schema=" + schema + ", value=" + value + "]";
	}
}
