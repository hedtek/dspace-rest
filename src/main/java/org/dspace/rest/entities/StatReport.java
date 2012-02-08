/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.entities;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dspace.app.statistics.Report;
import org.dspace.app.statistics.Stat;
import org.dspace.app.statistics.Statistics;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;

/**
 *
 * @author Bojan Suzic
 */
public class StatReport implements Report {

    private List<Statistics> blocks = new ArrayList<Statistics>();
    private Date start = null;
    private Date end = null;
    private HashMap<String, HashMap> completeStats = new HashMap<String, HashMap>();
    private HashMap<String, String> basicInfo = new HashMap<String, String>();
    private HashMap<String, String> dateInfo = new HashMap<String, String>();
    @EntityId
    private int id;
    private String type = "monthly";

    // unnecessary methods, interface requires implementation    
    @Override
    public String statBlock(Statistics ststcs) {
        return "";
    }

    @Override
    public String header() {
        return "";
    }

    @Override
    public String header(String string) {
        return "";
    }

    @Override
    public String sectionHeader(String string) {
        return string;
    }

    @Override
    public String floorInfo(int i) {
        return Integer.toString(i);
    }

    @Override
    public String blockExplanation(String string) {
        return string;
    }

    @Override
    public String footer() {
        return "";
    }

    @Override
    public String mainTitle() {
        return "";
    }

    // methods used for processing
    @Override
    public String dateRange() {
        DateFormat df = DateFormat.getDateInstance();

        if (start != null) {
            dateInfo.put("start", df.format(start));
        } else {
            dateInfo.put("start", "from start of records ");
        }

        if (end != null) {
            dateInfo.put("end", df.format(end));
        } else {
            dateInfo.put("end", "end of records");
        }

        return "";
    }

    public void processStat(Statistics stat, HashMap<String, HashMap> sections) {

        HashMap<String, HashMap> subsections = sections.containsKey(stat.getSectionHeader()) ? sections.get(stat.getSectionHeader()) : new HashMap<String, String>();
        Stat[] stats = stat.getStats();


        for (int i = 0; i < stats.length; i++) {
            HashMap<String, Object> description = new HashMap<String, Object>();
            if (stats[i].getReference() != null) {
                description.put("reference", stats[i].getReference());
            }

            if (stats[i].getUnits() != null) {
                description.put("units", stats[i].getUnits());
            }

            description.put("value", stats[i].getValue());

            if (stats[i].getKey() != null) {
                subsections.put(stats[i].getKey(), description);
            }

        }

        sections.put(stat.getSectionHeader(), subsections);
    }

    @Override
    public void setStartDate(Date date) {
        this.start = (date == null ? null : new Date(date.getTime()));
        if (start != null) {
            DateFormat df = DateFormat.getDateInstance();
            dateInfo.put("start", df.format(start));
        }

    }

    @Override
    public void setEndDate(Date date) {
        this.end = (date == null ? null : new Date(date.getTime()));
        if (end != null) {
            DateFormat df = DateFormat.getDateInstance();
            dateInfo.put("end", df.format(end));
        }
    }

    @Override
    public void setMainTitle(String string, String string1) {
        this.basicInfo.put("name", string);
        this.basicInfo.put("server", string1);
    }

    public HashMap<String, String> getBasicInfo() {
        return this.basicInfo;
    }

    @Override
    public void addBlock(Statistics stat) {
        blocks.add(stat);
    }

    
    @Override
    public String render() {
        Iterator<Statistics> statSets = blocks.iterator();
        while (statSets.hasNext()) {
            Statistics stats = statSets.next();
            processStat(stats, this.completeStats);
        }
        return "";
    }

    public HashMap<String, HashMap> getStatistics() {
        return this.completeStats;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String sType) {
        this.type = sType;
    }

    public HashMap<String, String> getDateInfo() {
        return this.dateInfo;
    }
}
