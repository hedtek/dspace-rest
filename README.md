A Read Only RESTful API for DSpace
==================================

This forks, refactors and [backports](#dspace "DSpace Version Support") the 
[original](scm.dspace.org/svn/repo/modules/dspace-rest/trunk/ "the source") 
[DSpace REST API](https://wiki.duraspace.org/display/DSPACE/REST+API "the spec")
 
 * __adding__ [integration tests](#integration-tests "integration and regression tests"), 
 [hard limit](#hardlimit "limits maximum number of items that can be rendered") 
 and [fetch groups](#fetch-groups "Support For Fetch Groups");
 * __removing__ write support; and 
 * __improving__ [pagination](#pagination "more support for pagination").
 
For the most part, features have been fixed or cleanly removed. 

<strong>Please note</strong>
that allowing use of some of the end points retained from the original design may be unwise.

End Points
----------

<table border="1">
  <caption>Covered Well By Integration Tests</caption>
  <thead>
    <tr><th></th><th>GET</th><th>PUT</th><th>POST</th><th>DELETE</th>    
  </thead>
  <tbody>
  <tr><td>items</td><td>&#10004;</td><td>&#10008;</td><td>&#10008;</td><td>&#10008;</td></tr>
  <tr><td>bitstream</td><td>&#10004;</td><td>&#10008;</td><td>&#10008;</td><td>&#10008;</td></tr>
  <tr><td>collections</td><td>&#10004;</td><td>&#10008;</td><td>&#10008;</td><td>&#10008;</td></tr>
  <tr><td>communities</td><td>&#10004;</td><td>&#10008;</td><td>&#10008;</td><td>&#10008;</td></tr>
  <tr><td>search</td><td>&#10004;</td><td>&#10008;</td><td>&#10008;</td><td>&#10008;</td></tr>
  <tr><td>harvest</td><td>&#10004;</td><td>&#10008;</td><td>&#10008;</td><td>&#10008;</td></tr>
  <tbody>
</table> 

<table border="1">
  <caption>Light Or No Integration Tests</caption>
  <thead>
    <tr><th></th><th>GET</th><th>PUT</th><th>POST</th><th>DELETE</th>    
  </thead>
  <tbody>
  <tr><td>users</td><td>&#10003;</td><td>&#10008;</td><td>&#10008;</td><td>&#10008;</td></tr>
  <tr><td>groups</td><td>&#10003;</td><td>&#10008;</td><td>&#10008;</td><td>&#10008;</td></tr>
  <tr><td>stats</td><td>&#10003;</td><td>&#10008;</td><td>&#10008;</td><td>&#10008;</td></tr>
  <tbody>
</table> 


<a name='integration-tests'>Running Integration And Regression Tests</a>
-----------------

PostgreSQL only.

First time, run `create_integration_test_db.sh`.
 
Then `mvn -DskipTests=false`
 
<a name='dspace'>Which DSpace Version?</a>
------------------
A fork of DSpace `1.5.2`

In particular, to `ItemIterator` add 

    public void skip() throws SQLException {
        if (itemRows.hasNext())
        {
            itemRows.next();
        }
    }
    
(to support pagination).


<a name='fetch-groups'>Fetch Groups</a>
-----------------

 * __Problem__ &mdash; rich data slow to produce and consume
 * __Cause__ &mdash; no fine control over richness of data  
 * __Solution__ &mdash; fetch groups
 * __Implementation__ &mdash; optional `fetch` parameter 


<table border="1">
  <caption>Supported Fetch Groups</caption>
  <thead>
    <tr><th></th><th>light</th><th>display</th><th>example</th>
  </thead>
  <tbody>
  <tr><td>items</td><td>&#10004;</td><td>&#10004;</td><td><code>/items/5.json?fetch=display</code></td></tr>
  <tr><td>search [items]</td><td>&#10004;</td><td>&#10004;</td><td><code>/search.json?query=search.resourcetype:2&fetch=light</code></td></tr>
  <tr><td>communities</td><td>&#10004;</td><td>&#10008;</td><td><code>/communities/25.json?fetch=light</code></td></tr>
  <tr><td>collections/<em>x</em>/items</td><td>&#10004;</td><td>&#10008;</td><td><code>/collections/1/items.json?fetch=light</code></td></tr>
  <tbody>
</table> 


<a name='pagination'>Pagination</a>
-----------------

 * __Problem__ &mdash; too much data using too many resources to produce and consume
 * __Cause__ &mdash; inefficient and absent pagination code 
 * __Solution__ &mdash; push pagination into data access and pagination more end points 


<table border="1">
  <caption>Supported Pagination</caption>
  <thead>
    <tr><th></th><th>page</th><th>perpage</th><th>sort</th><th>example</th>
  </thead>
  <tbody>
  <tr><td>search [items]</td><td>&#10004;</td><td>&#10004;</td><td>&#10008;</td><td><code>/search.json?query=search.resourcetype:2&_page=2&_perpage=20</code></td></tr>
  <tr><td>collections/<em>x</em>/items</td><td>&#10004;</td><td>&#10003;</td><td>&#10008;</td><td><code>/collections/1/items.json?_page=2</code></td></tr>
  <tbody>
</table> 


<a name='hardlimit'>Hard Limit For Items</a>
-----------------

Hard coded limit (10000) for the maximum number of items that can be rendered to JSON.

Known Limitations
=================

 * Binder does not stream. Given adequate memory for the required concurrent volume, 
   this shouldn't be an issue. Switching to a streaming binder probably requires replacing Sakai.
 * Entity bus obscures design. Dropping Sakai would allow simplification.
 * Refactoring incomplete. Still difficult to work with the code base.
 * Each new fetch group requires at least one new class. Moving away from Sakai would allow
   more flexible binding.
 * Sorting needs to be supported in DSpace. This would mean either substantial changes
   to core DSpace or switching to a more flexible data access standard (for example JPA).
 * Fetch group and pagination added on an _ad hoc_ basis. Code will need to be added for 
   unsupported areas. 
 * No support for writing. Those needing write support might route `GET` here and
   `PUT`, `DELETE` and `POST` to the original code. __Note__ when reading and writing
   are mixed in the same security domain, care __MUST__ be taken to limit vulnerability
   to scripts uploaded as repository content.
 * Allowing access to some end points may not be sensible in production. Given a reasonable volume
   of repository data, allowing (unpaginated) access may effective deny service. Those willing to 
   break compatibility should consider preventing unsafe calls.
 * Areas of the original specification are not particularly RESTful. Those willing to 
   break compatibility should consider adding RESTful linking and pagination.
 * Exception handling has been improved but more work remains.
 * Some regression tests rely on database order, and may be fragile.
 * Repackaging incomplete. This is a logical fork, and packaging should reflect this.
 * Build script not yet updated. This is a logical fork, and maven packaging should reflect this.
 * This module builds against a fork of DSpace `1.5.2`. 
   * A multi-module project would allow support for multiple versions.
   * Some changes need to be fed back into core or a public fork created.
 * The [hard limit](#hardlimit "limits maximum number of items that can be rendered"): 
   * is applied only to items, and
   * is not configurable.
 * Cache header support poor. Ideally DSpace would expose a feed allowing upstream caches to be 
   invalidated when data changes.