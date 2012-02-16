/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import java.sql.SQLException;

import org.dspace.core.Context;
import org.dspace.eperson.EPerson;

/**
 * Entity describing users registered on the system
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class UserEntity extends UserEntityId {

    private static EPerson findEPerson(String uid, Context context)
            throws SQLException {
        EPerson eperson = EPerson.find(context, Integer.parseInt(uid));
        if(eperson == null) {
            throw new SQLException("Cannot build User entity. EPerson (UID:" + uid + ") does not exist");
        }
        return eperson;
    }
	
   private final String firstName; 
   private final String lastName;
   private final String fullName;
   private final int type;
      
   public UserEntity(String uid, Context context) throws SQLException {
       this(findEPerson(uid, context));
   }

   /**
    * Constructs a user entity representing this ePerson.
    * 
    * @param eperson not null
    */
   public UserEntity(EPerson eperson) {
        super(eperson.getID());
        this.type = eperson.getType();
        this.firstName = eperson.getFirstName();
        this.fullName = eperson.getFullName();
        this.lastName = eperson.getLastName();
   }

   public String getFirstName() {
       return this.firstName;
   }

   public String getFullName() {
       return this.fullName;
   }
   public int getType() {
      return this.type;
   }
   
   public String getLastName() {
       return this.lastName;
   }
}
