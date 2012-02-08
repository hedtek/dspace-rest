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
import org.dspace.rest.params.RequestParameters;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityFieldRequired;

/**
 * Entity describing users registered on the system
 * @see UserEntityId
 * @see EPerson
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class UserEntity extends UserEntityId implements Comparable<UserEntity> {

    private static EPerson findEPerson(String uid, Context context)
            throws SQLException {
        EPerson eperson = EPerson.find(context, Integer.parseInt(uid));
        if(eperson == null) {
            throw new SQLException("Cannot build User entity. EPerson (UID:" + uid + ") does not exist");
        }
        return eperson;
    }
	
   @EntityFieldRequired private String name;
   private Boolean requireCertificate, selfRegistered;
   private String handle, email, firstName, lastName, fullName,
           language, netId;
   private int type;
   
   public UserEntity(String uid, Context context, int level, RequestParameters uparams) throws SQLException {
       this(findEPerson(uid, context));
   }

   /**
    * Constructs a user entity representing this ePerson.
    * 
    * @param eperson not null
    */
   public UserEntity(EPerson eperson) {
        super(eperson.getID());
        this.handle = eperson.getHandle();
        this.name = eperson.getName();
        this.type = eperson.getType();
        this.email = eperson.getEmail();
        this.firstName = eperson.getFirstName();
        this.fullName = eperson.getFullName();
        this.requireCertificate = eperson.getRequireCertificate();
        this.selfRegistered = eperson.getSelfRegistered();
        this.language = eperson.getLanguage();
        this.lastName = eperson.getLastName();
        this.netId = eperson.getNetid();
   }

   public UserEntity() {
       super(111);
       this.handle = "123456789/0";
       this.name = "John";
       this.type = 7;
       this.email = "john.smith@johnsemail.com";
       this.firstName = "John";
       this.fullName = "John Smith";
       this.requireCertificate = false;
       this.selfRegistered = true;
       this.language = "en";
       this.lastName = "Smith";
       this.netId = "1";
   }

   public String getName() {
       return this.name;
   }

   public String getHandle() {
       return this.handle;
   }

   public String getEmail() {
       return this.email;
   }

   public String getFirstName() {
       return this.firstName;
   }

   public String getFullName() {
       return this.fullName;
   }

   @Override
   public int getId() {
       return this.id;
   }
   public int getType() {
      return this.type;
   }
   
   public String getLastName() {
       return this.lastName;
   }

   public String getLanguage() {
       return this.language;
   }

   public String getNetId() {
       return this.netId;
   }

   public boolean getRequireCertificate() {
       return this.requireCertificate;
   }

   public boolean getSelfRegistered() {
       return this.selfRegistered;
   }

    @Override
    public String toString() {
        return "id:" + this.id + ", full_name:" + this.fullName;
    }

    public int compareTo(UserEntity o1) {
        return o1.getName().compareTo(this.getName()) * -1;
    }
}
