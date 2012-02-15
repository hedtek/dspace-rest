/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.providers;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dspace.app.statistics.ReportGenerator;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.rest.data.DSpace;
import org.dspace.rest.entities.StatReport;
import org.dspace.rest.entities.StatsEntity;
import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.search.Search;
import org.sakaiproject.entitybus.exception.EntityException;

/**
 * Provides interface for access to basic statistic data
 * @see StatsEntity
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class StatsProvider extends AbstractBaseProvider implements CoreEntityProvider {

    private static Logger log = Logger.getLogger(StatsProvider.class);
    private Pattern analysisMonthlyPattern = Pattern.compile("dspace-log-monthly-([0-9][0-9][0-9][0-9]-[0-9]+)\\.dat");
    private Pattern analysisGeneralPattern = Pattern.compile("dspace-log-general-([0-9]+-[0-9]+-[0-9]+)\\.dat");

    public StatsProvider(EntityProviderManager entityProviderManager) throws SQLException {
        super(entityProviderManager);
    }

    public String getEntityPrefix() {
        return "stats";
    }

    /**
     * By default in this provider there is no particular entity
     * @param id
     * @return
     */
    public boolean entityExists(String id) {
        // sample entity
        if (id.equals(":ID:")) {
            return true;
        }

        return false;
    }

    public Object getEntity(EntityReference reference) {
        // sample entity
        if (reference.getId().equals(":ID:")) {
            return new StatsEntity();
        }

        throw new IllegalArgumentException("Invalid id:" + reference.getId());
    }

    /**
     * Here are statistical data extracted and returned as the list
     * StatsEntity is used here to format and present data
     * It could be done using HashMap for now it is not functioning, updates
     * of related software needed
     * @see StatsEntity
     * @param ref
     * @param search
     * @return
     */
    public List<?> getEntities(EntityReference ref, Search search) {
        final Context context = DSpace.context();

        try {
            List<Object> stat = new ArrayList<Object>();
            File reportDir = new File(ConfigurationManager.getProperty("log.dir"));

            ReportGenerator rg = new ReportGenerator();
            // iterate through files in report directory and load each
            File[] reports = reportDir.listFiles();
            try {
                for (File report : reports) {
                    Matcher genMatcher = analysisGeneralPattern.matcher(report.getName());
                    Matcher monMatcher = analysisMonthlyPattern.matcher(report.getName());
                    StatReport statReport = new StatReport();
                    if (genMatcher.matches()) {
                        statReport.setType("general");
                        rg.processReport(context, statReport, report.getAbsolutePath());
                        stat.add(statReport);
                    } else if (monMatcher.matches()) {
                        statReport.setType("monthly");
                        rg.processReport(context, statReport, report.getAbsolutePath());
                        stat.add(statReport);
                    }
                }

                return stat;
            } catch (SQLException ex) {
                throw new EntityException("Internal Server Error", "SQL Problem", 500);
            } catch (Exception ex) {
                throw new EntityException("Internal Server Error", "Log file Problem", 500);
            }
        } finally {
            DSpace.complete(context);
        }
    }
}
