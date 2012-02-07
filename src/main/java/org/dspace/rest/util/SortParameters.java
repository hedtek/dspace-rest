package org.dspace.rest.util;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

public class SortParameters {

    private String sort = "";
    private String query = "";
    private String order = "";
    private List<Integer> sortOptions = new ArrayList<Integer>();


    public SortParameters(RequestStorage reqStor) {
        setQuery(reqStor);
        setOrder(reqStor);
        setSort(reqStor);    
    }

    private void setOrder(String param) {
        this.order = param;
    }

    private void setSort(String param) {
        this.sort = param;
        setSortOptions();
    }

    private void setQuery(String param) {
        this.query = param;
    }

    public String getQuery() {
        return this.query;
    }

    public String getOrder() {
        return this.order;
    }

    public String getSort() {
        return this.sort;
    }
    
    private void setSortOptions(List<Integer> sortOptions) {
        this.sortOptions = sortOptions;
    }

    public List<Integer> getSortOptions() {
        return sortOptions;
    }

    public static String valueInStore(final String key,
            final String defaultWhenValueNotStored,
            final RequestStorage requestStore) {
        String value;
        final Object storedValue = requestStore.getStoredValue(key);
        if (storedValue == null) {
            
            value = defaultWhenValueNotStored;
        } else {
            value = storedValue.toString();
        }
        return value;
    }

    public void setQuery(final RequestStorage requestStore) {
        String query = valueInStore("query", "",
                requestStore);
        setQuery(query);
    }

    private void setSortOptions() {
        final String _sort = this.sort.toLowerCase();
        final List<Integer> sortOptions = new ArrayList<Integer>();
        // defining sort fields and values
        String[] sort_arr = _sort.split(",");
    
        for (String option : sort_arr) {
            if (option.startsWith("submitter")) {
                sortOptions.add(UtilHelper.SORT_SUBMITTER);
            } else if (option.startsWith("lastname")) {
                sortOptions.add(UtilHelper.SORT_LASTNAME);
            } else if (option.startsWith("fullname")) {
                sortOptions.add(UtilHelper.SORT_FULL_NAME);
            } else if (option.startsWith("language")) {
                sortOptions.add(UtilHelper.SORT_LANGUAGE);
            } else if (option.startsWith("lastmodified")) {
                sortOptions.add(UtilHelper.SORT_LASTMODIFIED);
            } else if (option.startsWith("countitems")) {
                sortOptions.add(UtilHelper.SORT_COUNT_ITEMS);
            } else if (option.startsWith("name")) {
                sortOptions.add(UtilHelper.SORT_NAME);
            } else {
                sortOptions.add(UtilHelper.SORT_ID);
            }
            if ((option.endsWith("_desc") || option.endsWith("_reverse"))) {
                int i = sortOptions.get(sortOptions.size() - 1);
                sortOptions.remove(sortOptions.size() - 1);
                i += 100;
                sortOptions.add(i);
            }
        }
        
        setSortOptions(sortOptions);
    }

    public void setSort(final RequestStorage requestStore) {
        String sort = valueInStore("_sort", "",
                requestStore);
        // both parameters are used according to requirements
        if (order.length() > 0 && sort.equals("")) {
            sort = order;
        }
        
        setSort(sort);
    }

    public void setOrder(final RequestStorage requestStore) {
        String order = valueInStore("_order", "", requestStore);
        setOrder(order);
    }
}
