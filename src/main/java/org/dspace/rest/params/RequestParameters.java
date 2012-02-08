/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.params;

import org.dspace.rest.util.UtilHelper;

/**
 *
 * @author Bojan Suzic
 */
public class RequestParameters {

    private String user = "";
    private String password = "";
    private boolean isAuthorized = false;
    private boolean inArchive = false;
    private String sdate = "";
    private String edate = "";
    private boolean withdrawn = false;
    private int detail = UtilHelper.DEPTH_STANDARD;
    public void setUser(String uname) {
        this.user = uname;
    }

    public void setDetail(int param) {
        this.detail = param;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public void setIsAuthorized(boolean param) {
        this.isAuthorized = param;
    }

    public void setInArchive(boolean param) {
        this.inArchive = param;
    }

    public void setSDate(String param) {
        this.sdate = param;
    }

    public void setEDate(String param) {
        this.edate = param;
    }

    public void setWithdrawn(boolean param) {
        this.withdrawn = param;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean getIsAuthorized() {
        return this.isAuthorized;
    }

    public boolean getInArchive() {
        return this.inArchive;
    }

    public String getSDate() {
        return this.sdate;
    }

    public String getEDate() {
        return this.edate;
    }

    public boolean getWithdrawn() {
        return this.withdrawn;
    }

    public int getDetail() {
        return this.detail;
    }
}
