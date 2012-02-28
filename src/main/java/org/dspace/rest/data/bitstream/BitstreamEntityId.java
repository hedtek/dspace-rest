/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.data.bitstream;

import java.sql.SQLException;

import org.dspace.content.Bitstream;
import org.dspace.core.Context;
import org.dspace.rest.data.base.Entity;



/**
 * Entity describing Bitstream, basic version
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */

public class BitstreamEntityId extends Entity {
   public BitstreamEntityId (String uid, Context context) throws SQLException {
       this(Bitstream.find(context, Integer.parseInt(uid)));
   }

    public BitstreamEntityId(Bitstream bitstream) throws SQLException {
        super(bitstream.getID(), Type.BITSTREAM);
   }
}
