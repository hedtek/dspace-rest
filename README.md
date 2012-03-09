A Read Only RESTful API for DSpace
==================================

This forks, refactors and [backports](#dspace "DSpace Version Support") the 
[original](scm.dspace.org/svn/repo/modules/dspace-rest/trunk/ "the source") 
[DSpace REST API](https://wiki.duraspace.org/display/DSPACE/REST+API "the spec")
 
 * __adding__ integration tests and [fetch groups](#fetch-groups "Support For Fetch Groups");
 * __removing__ write support; and 
 * __improving__ pagination.
 
For the most part, features have been fixed or cleanly removed. 
 
<a name='dspace'>Which DSpace Version?</a>
------------------
A fork of DSpace `1.5.2`

<a name='fetch-groups'>Fetch Groups</a>
-----------------

 * __Problem__ &mdash; rich data slow to produce and consume
 * __Cause__ &mdash; no fine control over richness of data  
 * __Solution__ &mdash; fetch groups
 * __Implementation__ &mdash; optional `fetch` parameter 

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