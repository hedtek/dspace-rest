package org.dspace.rest.params;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dspace.search.QueryArgs;
import org.dspace.sort.SortOption;
import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

public class SortParameters {
    
    private final boolean SORT = false;

    private String sort = "";
    private String query = "";
    private String order = "";
    private List<Integer> sortOptions = new ArrayList<Integer>();
    static final int FULL_NAME_REV = 317;
    static final int LASTNAME_REV = 316;
    static final int LANGUAGE_REV = 315;
    static final int COUNT_ITEMS_REV = 314;
    static final int LASTMODIFIED_REV = 312;
    static final int NAME_REV = 311;
    static final int ID_REV = 310;
    static final int FULL_NAME = 217;
    static final int LASTNAME = 216;
    static final int LANGUAGE = 215;
    static final int COUNT_ITEMS = 214;
    static final int LASTMODIFIED = 212;
    static final int NAME = 211;
    static final int ID = 210;


    public SortParameters(RequestStorage reqStor) {
        setQuery(reqStor);
        setOrder(reqStor);
        setSort(reqStor);    
    }

    private static String valueInStore(final String key,
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

    private void setQuery(final RequestStorage requestStore) {
        String query = valueInStore("query", "",
                requestStore);
        this.query = query;
    }

    private void setSortOptions() {
        final String _sort = this.sort.toLowerCase();
        final List<Integer> sortOptions = new ArrayList<Integer>();
        // defining sort fields and values
        String[] sort_arr = _sort.split(",");
    
        for (String option : sort_arr) {
            if (option.startsWith("lastname")) {
                sortOptions.add(SortParameters.LASTNAME);
            } else if (option.startsWith("fullname")) {
                sortOptions.add(SortParameters.FULL_NAME);
            } else if (option.startsWith("language")) {
                sortOptions.add(SortParameters.LANGUAGE);
            } else if (option.startsWith("lastmodified")) {
                sortOptions.add(SortParameters.LASTMODIFIED);
            } else if (option.startsWith("countitems")) {
                sortOptions.add(SortParameters.COUNT_ITEMS);
            } else if (option.startsWith("name")) {
                sortOptions.add(SortParameters.NAME);
            } else {
                sortOptions.add(SortParameters.ID);
            }
            if ((option.endsWith("_desc") || option.endsWith("_reverse"))) {
                int i = sortOptions.get(sortOptions.size() - 1);
                sortOptions.remove(sortOptions.size() - 1);
                i += 100;
                sortOptions.add(i);
            }
        }
        
        this.sortOptions = sortOptions;
    }

    private void setSort(final RequestStorage requestStore) {
        String sort = valueInStore("_sort", "",
                requestStore);
        // both parameters are used according to requirements
        if (order.length() > 0 && sort.equals("")) {
            sort = order;
        }
        
        this.sort = sort;
        setSortOptions();
    }

    private void setOrder(final RequestStorage requestStore) {
        String order = valueInStore("_order", "", requestStore);
        this.order = order;
    }

    
    public void configure(final QueryArgs arg) {
        arg.setQuery(this.query);
        if ((this.order.equalsIgnoreCase("descending")) || (this.order.equalsIgnoreCase("desc"))) {
            arg.setSortOrder(SortOption.DESCENDING);
        } else {
            arg.setSortOrder(SortOption.ASCENDING);
        }
    }

    @SuppressWarnings({ "unchecked", "unused" })
    public void sort(final List<?> entities) {
        if (SORT && sortOptions.size() > 0) {
            Collections.sort(entities, new FlexibleComparator(sortOptions));
        }
    }
}
