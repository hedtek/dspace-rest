/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.rest.providers.StatsProvider;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;

/**
 * Entity describing basic system statistics
 * @see StatsProvider
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
public class StatsEntity {

    @EntityId
    private int id;
    HashMap generalStats = new HashMap<String, String>();
    private String description;
    private String type = "monthly";

    // TODO inspect and add additional fields
    public StatsEntity(Context context) throws SQLException {

        try {
            showStatistics(context);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // TODO inspect and add additional fields
    public StatsEntity(Context context, File file) throws SQLException {

        try {
            generateStatistics(context, file);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public StatsEntity() {
        generalStats.put("browse_mini", "1");
        generalStats.put("Community Updates", "2");
        generalStats.put("Workflow Starts", "3");
        generalStats.put("Warnings", "4");
        generalStats.put("Sub Community Added", "5");
        generalStats.put("OAI Requests", "6");
        generalStats.put("browse", "7");
        generalStats.put("Bitstream Views", "8");
        generalStats.put("Bitstream Updates", "9");
        generalStats.put("Searches Performed", "10");
        generalStats.put("Workspace Item Views", "11");
        generalStats.put("Bundles Created", "12");
        generalStats.put("User Logins", "13");
        generalStats.put("Collection Views", "14");
        generalStats.put("Bundle Updates", "15");
        generalStats.put("Bitstreams Added", "16");
        generalStats.put("Item Views", "17");
        generalStats.put("Items Archived", "18");
        generalStats.put("All Items", "19");
        generalStats.put("Community Views", "20");
        generalStats.put("User Home Page Views", "21");
    }

    @Override
    public String toString() {
        return "id:" + this.id;
    }

    private void generateStatistics(Context context, File file) {
        if (file.getAbsolutePath().contains("general")) {
            this.type = "general";
        }

        description = file.getAbsolutePath().replaceAll("(.*report-)|(.ht.*)|(general-)", "");
        try {
            this.id = Integer.parseInt(description.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException ex) {
            this.id = 0;
        }

        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(System.getProperty(
                        "line.separator"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//        System.out.println(contents);
        String filteredReport = contents.toString();
        filteredReport = filteredReport.replaceAll("<div.*?>.*?</div>", "");
        filteredReport = filteredReport.replaceAll("<style.*?>.*?</style>", "");
        filteredReport = filteredReport.replaceAll("<th.*?>.*?</th>", "");
        filteredReport = filteredReport.replaceAll("\t", "");
        filteredReport = filteredReport.replaceAll("(<td.*?>)(.*?)(</td.*?>)(<td.*?>)(.*?)(</td.*?>)", "$2::$5::");
        filteredReport = filteredReport.replaceAll("<.*?>", "");
        String[] splittedReport = filteredReport.split("::");


        for (int x = 0; x
                < splittedReport.length; x++) {
            generalStats.put(splittedReport[x], splittedReport[x + 1]);
        }

        try {
            context.complete();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public String getDescription() {
        return this.description;


    }

    public String getType() {
        return this.type;


    }

    private void showStatistics(Context context)
            throws IOException, SQLException {
        StringBuffer report = new StringBuffer();
        String date = null;

        File reportDir = new File(ConfigurationManager.getProperty("report.dir"));

        File[] reports = reportDir.listFiles();
        File reportFile = null;

        FileInputStream fir = null;
        InputStreamReader ir = null;
        BufferedReader br = null;



        try {
            List monthsList = new ArrayList();

            Pattern monthly = Pattern.compile("report-([0-9][0-9][0-9][0-9]-[0-9]+)\\.html");
            Pattern general = Pattern.compile("report-general-([0-9]+-[0-9]+-[0-9]+)\\.html");

            // FIXME: this whole thing is horribly inflexible and needs serious
            // work; but as a basic proof of concept will suffice

            // if no date is passed then we want to get the most recent general
            // report


            if (date == null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'M'-'dd");
                Date mostRecentDate = null;



                for (int i = 0; i
                        < reports.length; i++) {
                    Matcher matchGeneral = general.matcher(reports[i].getName());


                    if (matchGeneral.matches()) {
                        Date parsedDate = null;



                        try {
                            parsedDate = sdf.parse(matchGeneral.group(1).trim());


                        } catch (ParseException e) {
                            // FIXME: currently no error handling
                        }

                        if (mostRecentDate == null) {
                            mostRecentDate = parsedDate;
                            reportFile = reports[i];


                        }

                        if (parsedDate != null && parsedDate.compareTo(mostRecentDate) > 0) {
                            mostRecentDate = parsedDate;
                            reportFile = reports[i];


                        }
                    }
                }
            }

            // if a date is passed then we want to get the file for that month
            if (date != null) {
                String desiredReport = "report-" + date + ".html";



                for (int i = 0; i
                        < reports.length; i++) {
                    if (reports[i].getName().equals(desiredReport)) {
                        reportFile = reports[i];


                    }
                }
            }

            if (reportFile == null) {
                System.out.println(" blank stats ");


            } // finally, build the list of report dates
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'M");


            for (int i = 0; i
                    < reports.length; i++) {
                Matcher matchReport = monthly.matcher(reports[i].getName());


                if (matchReport.matches()) {
                    Date parsedDate = null;



                    try {
                        parsedDate = sdf.parse(matchReport.group(1).trim());


                    } catch (ParseException e) {
                        // FIXME: currently no error handling
                    }

                    monthsList.add(parsedDate);


                }
            }

            Date[] months = new Date[monthsList.size()];
            months = (Date[]) monthsList.toArray(months);

            Arrays.sort(months);



            try {
                fir = new FileInputStream(reportFile.getPath());
                ir = new InputStreamReader(fir, "UTF-8");
                br = new BufferedReader(ir);


            } catch (IOException e) {
                // FIXME: no error handing yet
                throw new RuntimeException(e.getMessage(), e);


            } // FIXME: there's got to be a better way of doing this
            String line = null;


            while ((line = br.readLine()) != null) {
                report.append(line);


            }
        } finally {
            if (br != null) {
                try {
                    br.close();


                } catch (IOException ioe) {
                }
            }

            if (ir != null) {
                try {
                    ir.close();


                } catch (IOException ioe) {
                }
            }

            if (fir != null) {
                try {
                    fir.close();


                } catch (IOException ioe) {
                }
            }
        }

        String filteredReport = report.toString();
        filteredReport = filteredReport.replaceAll("<div.*?>.*?</div>", "");
        filteredReport = filteredReport.replaceAll("<style.*?>.*?</style>", "");
        filteredReport = filteredReport.replaceAll("<th.*?>.*?</th>", "");
        filteredReport = filteredReport.replaceAll("\t", "");
        filteredReport = filteredReport.replaceAll("(<td.*?>)(.*?)(</td.*?>)(<td.*?>)(.*?)(</td.*?>)", "$2::$5::");
        filteredReport = filteredReport.replaceAll("<.*?>", "");
        String[] splittedReport = filteredReport.split("::");


        for (int x = 0; x
                < splittedReport.length; x++) {
            generalStats.put(splittedReport[x], splittedReport[x + 1]);


        }
        context.complete();


    }

    // FIXME: the methods here are written this way as the xml support is not
    // working for hashmaps in sakai completely
    public String getbrowse_mini() {
        return this.generalStats.get("browse_mini").toString();


    }

    public String getCommunityUpdates() {
        return this.generalStats.get("Community Updates").toString();


    }

    public String getWorkflowStarts() {
        return this.generalStats.get("Workflow Starts").toString();


    }

    public String getWarnings() {
        return this.generalStats.get("Warnings").toString();


    }

    public String getSubcommunitiesAdded() {
        return this.generalStats.get("Sub Community Added").toString();


    }

    public String getOAIRequests() {
        return this.generalStats.get("OAI Requests").toString();


    }

    public String getbrowse() {
        return this.generalStats.get("browse").toString();


    }

    public String getBitstreamViews() {
        return this.generalStats.get("Bitstream Views").toString();


    }

    public String getBitstreamupdates() {
        return this.generalStats.get("Bitstream Updates").toString();


    }

    public String getSearchesPerformed() {
        return this.generalStats.get("Searches Performed").toString();


    }

    public String getWorkSpaceItemViews() {
        return this.generalStats.get("Workspace Item Views").toString();


    }

    public String getBundlesCreated() {
        return this.generalStats.get("Bundles Created").toString();


    }

    public String getUserLogins() {
        return this.generalStats.get("User Logins").toString();


    }

    public String getCollectionViews() {
        return this.generalStats.get("Collection Views").toString();


    }

    public String getBundleUpdates() {
        return this.generalStats.get("Bundle Updates").toString();


    }

    public String getBitstreamsAdded() {
        return this.generalStats.get("Bitstreams Added").toString();


    }

    public String getItemViews() {
        return this.generalStats.get("Item Views").toString();


    }

    public String getItemsArchived() {
        return this.generalStats.get("Items Archived").toString();


    }

    public String getAllItems() {
        return this.generalStats.get("All Items").toString();


    }

    public String getCommunityviews() {
        return this.generalStats.get("Community Views").toString();


    }

    public String getUserHomePageViews() {
        return this.generalStats.get("User Home Page Views").toString();

    }
//    FIXME: this method will work later
//    public HashMap<String, String> getStats() {
//        return this.generalStats;
//    }
}
