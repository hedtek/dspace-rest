/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.params;


/**
 *
 * @author Bojan Suzic
 */
public class RequestParameters {

    private String user = "";
    private String password = "";
    public void setUser(String uname) {
        this.user = uname;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }
}
