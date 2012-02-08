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
import org.dspace.rest.providers.BitstreamProvider;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;



/**
 * Entity describing Bitstream, basic version
 * @see BitstreamEntity
 * @see BitstreamProvider
 * @see Bitstream
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */

public class BitstreamEntityId {
   @EntityId private int id;

   protected BitstreamEntityId() {

   }

   public BitstreamEntityId (String uid, Context context) throws SQLException {
       Bitstream res = Bitstream.find(context, Integer.parseInt(uid));
       this.id = res.getID();
       //context.complete();
   }

    public BitstreamEntityId(Bitstream bitstream) throws SQLException {
        this.id = bitstream.getID();
   }

   public int getId() {
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
