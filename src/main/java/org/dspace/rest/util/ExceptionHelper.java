/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.util;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.sakaiproject.entitybus.exception.EntityException;

/**
 *
 */
public class ExceptionHelper {
    private static Logger log = Logger.getLogger(ExceptionHelper.class);

    public static EntityException toEntityException(String describeOperation, SQLException e) {
        log.error(describeOperation + "[" + e.getMessage() + "]");
        return new EntityException("Internal server error", "SQL error", 500);

    }
}
