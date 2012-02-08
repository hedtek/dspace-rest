package org.dspace.rest.params;

import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.search.QueryArgs;
import org.sakaiproject.entitybus.entityprovider.extension.RequestStorage;

public class PaginationParameters {
    
    private static final String LIMIT_PARAMETER_KEY = "_limit";
    private static final String PERPAGE_PARAMETER_KEY = "_perpage";
    private static final String PAGE_PARAMETER_KEY = "_page";
    private static final String START_PARAMETER_KEY = "_start";
    private static final int DEFAULT_VALUE = 0;

    private static int valueInStore(final RequestStorage requestStorage,
            final String key) {
        int value;
        final Object storedValue = requestStorage.getStoredValue(key);
        try {
            if (storedValue == null) {
                value = DEFAULT_VALUE;
            } else {
                value = Integer.parseInt(storedValue.toString());
            }
        } catch (NumberFormatException ex) {
            log.debug("Ignoring malformed '" + key + "' parameter value - expected a number but was: " + storedValue);
            value = DEFAULT_VALUE;
        }
        if (value < DEFAULT_VALUE) {
            log.debug("Negative '" + key + "' parameter value. Using " + DEFAULT_VALUE + ".");
            value = DEFAULT_VALUE;
        }
        return value;
    }

    private static Logger log = Logger.getLogger(PaginationParameters.class);
    
    private final int start;
    private final int page;
    private final int perpage;
    private final int limit;
    
    public PaginationParameters(final RequestStorage requestStorage) {

        this.start = valueInStore(requestStorage, START_PARAMETER_KEY);
        this.page = valueInStore(requestStorage,  PAGE_PARAMETER_KEY);
        this.perpage = valueInStore(requestStorage,  PERPAGE_PARAMETER_KEY);
        this.limit =  valueInStore(requestStorage,  LIMIT_PARAMETER_KEY);
    }
    
    public int getStart() {
        return start;
    }

    public int getLimit() {
        return limit;
    }

    public  void configure(final QueryArgs arg) {
        if (perpage > 0) {
            arg.setPageSize(perpage);
        }
        arg.setStart(getStart());
    }

    public void removeTrailing(final List<?> entities) {
        if ((getStart() > 0) && (getStart() < entities.size())) {
            for (int x = 0; x
                    < getStart(); x++) {
                entities.remove(x);
            }
        }
        if (perpage > 0) {
            entities.subList(0, page * perpage).clear();
        }
        if ((getLimit() > 0) && entities.size() > getLimit()) {
            entities.subList(getLimit(), entities.size()).clear();
        }
    }   
}
