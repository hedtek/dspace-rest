/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import java.sql.SQLException;

import org.dspace.content.Bitstream;
import org.dspace.core.Context;



/**
 * Entity describing Bitstream, basic version
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */

public class BitstreamEntityId {
   private final int id;

   public BitstreamEntityId (String uid, Context context) throws SQLException {
       this(Bitstream.find(context, Integer.parseInt(uid)));
   }

    public BitstreamEntityId(Bitstream bitstream) throws SQLException {
        this.id = bitstream.getID();
   }

   public final int getId() {
       return this.id;
   }

   @Override
   public boolean equals(Object obj) {
      if (null == obj)
         return false;
      if (!(obj instanceof BitstreamEntityId))
         return false;
      else {
         BitstreamEntityId castObj = (BitstreamEntityId) obj;
            return (this.id == castObj.id);
      }
   }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "id:" + this.id;
    }
}
