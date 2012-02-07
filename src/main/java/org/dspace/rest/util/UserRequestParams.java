/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.util;

import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

/**
 *
 * @author Bojan Suzic
 */
public class UserRequestParams {

    private String user = "";
    private String password = "";
    private boolean idOnly = false;
    private boolean isAuthorized = false;
    private boolean immediateOnly = true;
    private boolean inArchive = false;
    private boolean topLevelOnly = true;
    private String query = "";
    private String _order = "";
    private String _sort = "";
    private int _start = 0;
    private int _page = 0;
    private int _perpage = 0;
    private int _limit = 0;
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

    private void setQuery(String param) {
        this.query = param;
    }

    public void setOrder(String param) {
        this._order = param;
    }

    public void setSort(String param) {
        this._sort = param;
    }

    public void setStart(int param) {
        this._start = param;
    }

    public void setPage(int param) {
        this._page = param;
    }

    public void setPerPage(int param) {
        this._perpage = param;
    }

    public void setLimit(int param) {
        this._limit = param;
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

    public String getQuery() {
        return this.query;
    }

    public String getOrder() {
        return this._order;
    }

    public String getSort() {
        return this._sort;
    }

    public int getStart() {
        return this._start;
    }

    public int getPage() {
        return this._page;
    }

    public int getPerPage() {
        return this._perpage;
    }

    public int getLimit() {
        return this._limit;
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

    public void setQuery(final RequestStorage requestStore) {
        String query = valueInStore("query", "",
                requestStore);
        setQuery(query);
    }

    public static String valueInStore(final String key,
            final String defaultWhenValueNotStored,
            final RequestStorage requestStore) {
        String query;
        final Object storedValue = requestStore.getStoredValue(key);
        if (storedValue == null) {
            
            query = defaultWhenValueNotStored;
        } else {
            query = storedValue.toString();
        }
        return query;
    }
}
