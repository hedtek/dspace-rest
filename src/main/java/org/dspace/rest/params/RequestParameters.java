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
    private boolean idOnly = false;
    private boolean isAuthorized = false;
    private boolean immediateOnly = true;
    private boolean inArchive = false;
    private boolean topLevelOnly = true;
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

    public void setIdOnly(boolean param) {
        this.idOnly = param;
    }

    public void setIsAuthorized(boolean param) {
        this.isAuthorized = param;
    }

    public void setImmediateOnly(boolean param) {
        this.immediateOnly = param;
    }

    public void setInArchive(boolean param) {
        this.inArchive = param;
    }

    public void setTopLevelOnly(boolean param) {
        this.topLevelOnly = param;
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

    public boolean getIdOnly() {
        return this.idOnly;
    }

    public boolean getIsAuthorized() {
        return this.isAuthorized;
    }

    public boolean getImmediateOnly() {
        return this.immediateOnly;
    }

    public boolean getInArchive() {
        return this.inArchive;
    }

    public boolean getTopLevelOnly() {
        return this.topLevelOnly;
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
