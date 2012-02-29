package org.dspace.rest.data.community;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.browse.BrowseEngine;
import org.dspace.browse.BrowseException;
import org.dspace.browse.BrowseIndex;
import org.dspace.browse.BrowseInfo;
import org.dspace.browse.BrowserScope;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.rest.data.base.DetailDepth;
import org.dspace.rest.data.base.Entity;
import org.dspace.rest.data.base.FetchGroup;
import org.dspace.rest.data.bitstream.BitstreamEntityId;
import org.dspace.rest.data.item.ItemBuilder;
import org.dspace.sort.SortException;
import org.dspace.sort.SortOption;

public class Communities {

    private static Logger log = Logger.getLogger(Communities.class);
    
    static Object logo(Community community) throws SQLException {
        final Object logo;
        if (community.getLogo() == null) {
            logo = null;
        } else {
            logo = new BitstreamEntityId(community.getLogo());
        }
        return logo;
    }

    public static List<Entity> toEntities(int level, final DetailDepth depth, final Community[] communities)
            throws SQLException {
        return new BulkCommunityBuilder(communities).withFull(depth.includeFullDetails(level)).all(level, depth);
    }
    
    public static Entity fetch(final String uid, final Context context,
            final DetailDepth depth, final FetchGroup fetchGroup) throws SQLException {
        return new Builder(new Communities(context).fetch(uid)).with(fetchGroup).build(depth);
    }

    public static List<Entity> subcommunities(Community community, int level, final DetailDepth depth) throws SQLException {
        return new Builder(community).subcommunities(level, depth);
    }

    public static Entity parent(final int level, final DetailDepth depth, final Community community) throws SQLException {
        return new Builder(community).parent(level, depth);
    }
    
    private final Context context;

    public Communities(Context context) {
        super();
        this.context = context;
    }
    
    public List<Entity> get(final boolean onlyTopLevel, final FetchGroup fetch) throws SQLException {
        return new BulkCommunityBuilder(get(onlyTopLevel)).with(fetch).all();
    }

    private Community[] get(final boolean onlyTopLevel) throws SQLException {
        return onlyTopLevel ? Community.findAllTop(context) : Community.findAll(context);
    }

    /**
     * Obtain the recent submissions from the given container object.  This
     * method uses the configuration to determine which field and how many
     * items to retrieve from the DSpace Object.
     * 
     * If the object you pass in is not a Community or Collection (e.g. an Item
     * is a DSpaceObject which cannot be used here), an exception will be thrown
     * 
     * @param dso   DSpaceObject: Community or Collection
     * @return      The recently submitted items
     * @throws RecentSubmissionsException
     * @throws BrowseException 
     * @throws SortException 
     */
    private Item[] recentSubmissions(final Community community) throws BrowseException, SortException
    {
        final BrowseInfo results = new BrowseEngine(context).browseMini(scope(community));
        return results.getItemResults(context);
    }

    private BrowserScope scope(final Community community) throws BrowseException, SortException {
        final BrowserScope bs = new BrowserScope(context);
        final BrowseIndex bi = BrowseIndex.getItemBrowseIndex();
    
        // get our configuration
        final String source = ConfigurationManager.getProperty("recent.submissions.sort-option");
        final String count = ConfigurationManager.getProperty("recent.submissions.count");
    
        // fill in the scope
        bs.setBrowseIndex(bi);
        bs.setOrder(SortOption.DESCENDING);
        bs.setResultsPerPage(Integer.parseInt(count));
        bs.setBrowseContainer(community);
        for (SortOption so : SortOption.getSortOptions())
        {
            if (so.getName().equals(source))
            {
                bs.setSortBy(so.getNumber());
            }
        }
        return bs;
    }

    private List<Object> recentSubmissions(final int level, final DetailDepth depth, final Community community) throws SQLException {
        List<Object> recentSubmissions = new ArrayList<Object>();
        try {
            Item[] recentItems = recentSubmissions(community);
            for (final Item item : recentItems) {
                recentSubmissions.add(new ItemBuilder(item).till(depth).withFull(depth.includeFullDetails(level)));
            }
        } catch (BrowseException e) {
            log.debug("Failed to find recent submissions. Continuing with entity retreival.", e);
        } catch (SortException e) {
            log.debug("Failed to find recent submissions. Continuing with entity retreival.", e);
        }
        return recentSubmissions;
    }
    

    public CommunityEntity community(String uid, final DetailDepth depth) throws SQLException {
        return build(uid, 1, depth);
    }

    private CommunityEntity build(String uid, int level, final DetailDepth depth) throws SQLException {
        final Community community = fetch(uid);
        return new CommunityEntity(community, level, depth, recentSubmissions(level, depth, community), 0);
    }

    private Community fetch(final String uid) throws SQLException {
        return Community.find(context, Integer.parseInt(uid));
    }

}
