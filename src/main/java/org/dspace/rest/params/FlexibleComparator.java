/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */

package org.dspace.rest.params;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.dspace.rest.providers.AbstractBaseProvider;

/**
 * Provides basis for sorting entities in the list.
 * The values for sorting are definet in UtilHelper, the parameter extraction
 * and processing is done in AbstractBaseProvider
 * @see AbstractBaseProvider
 * @author Bojan Suzic, bojan.suzic@gmail.com
 */
class FlexibleComparator implements Comparator {

    private int method;
    private List<Integer> methodList = new ArrayList<Integer>();

    /**
     * Constructs and defines local values
     * @param methodList list of sorting methods by priority
     */
    public FlexibleComparator(List<Integer> methodList) {
        this.methodList = methodList;
        try {
            this.method = methodList.get(0);
        } catch (Exception ex) {
            // by default it sorts by id which should be everywhere present
            this.method = SortParameters.ID;
        }
    }

    public int compare(Object o1, Object o2) {
        if (methodList.size() > 0) {
            method = methodList.get(0);
            methodList.remove(0);
        }
        int result = 0;

        // normally should not happen
        if (!(o1.getClass().getName().equals(o2.getClass().getName()))) {
            // have mercy for objects like in harvest case, where the first object is just
            // info entity about search results; other objects are the same
            //throw new EntityException("Not acceptable", "Comparing different entities", 406);
        }

        switch (method) {
            // sort by full name, e.g. in UsersEntity
            case SortParameters.FULL_NAME:
            case SortParameters.FULL_NAME_REV:
                 {
                    try {
                        String obj1Lang = o1.getClass().getDeclaredMethod("getFullName").invoke(o1).toString();
                        String obj2Lang = o2.getClass().getDeclaredMethod("getFullName").invoke(o2).toString();
                        int i = (obj1Lang.compareToIgnoreCase(obj2Lang));
                        if ((i != 0) || (methodList.size() == 0)) {
                            result = i;
                        } else {
                            result = compare(o1, o2);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        result = 0;
                    }
                }
                break;

            // sort by last name, e.g. in UsersEntity
            case SortParameters.LASTNAME:
            case SortParameters.LASTNAME_REV:
                 {
                    try {
                        String obj1Lang = o1.getClass().getDeclaredMethod("getLastName").invoke(o1).toString();
                        String obj2Lang = o2.getClass().getDeclaredMethod("getLastName").invoke(o2).toString();
                        int i = (obj1Lang.compareToIgnoreCase(obj2Lang));
                        if ((i != 0) || (methodList.size() == 0)) {
                            result = i;
                        } else {
                            result = compare(o1, o2);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        result = 0;
                    }
                }
                break;

            // sort by count of items, e.g. in CollectionEntity
            case SortParameters.COUNT_ITEMS:
            case SortParameters.COUNT_ITEMS_REV:
                 {
                    try {
                        Integer obj1Cnt = Integer.parseInt(o1.getClass().getDeclaredMethod("getCountItems").invoke(o1).toString());
                        Integer obj2Cnt = Integer.parseInt(o2.getClass().getDeclaredMethod("getCountItems").invoke(o2).toString());
                        int i = (obj1Cnt.compareTo(obj2Cnt));
                        if ((i != 0) || (methodList.size() == 0)) {
                            result = i;
                        } else {
                            result = compare(o1, o2);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        result = 0;
                    }
                }
                break;

            // sort by language, e.g. by UserEntity
            case SortParameters.LANGUAGE:
            case SortParameters.LANGUAGE_REV:
                 {
                    try {
                        String obj1Lang = o1.getClass().getDeclaredMethod("getLanguage").invoke(o1).toString();
                        String obj2Lang = o2.getClass().getDeclaredMethod("getLanguage").invoke(o2).toString();
                        int i = (obj1Lang.compareToIgnoreCase(obj2Lang));
                        if ((i != 0) || (methodList.size() == 0)) {
                            result = i;
                        } else {
                            result = compare(o1, o2);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        result = 0;
                    }
                }
                break;

            // sort by last modified date, e.g. in ItemsEntity
            case SortParameters.LASTMODIFIED:
            case SortParameters.LASTMODIFIED_REV:
                 {
                    try {
                        Date obj1Date = (Date) o1.getClass().getDeclaredMethod("getLastModified").invoke(o1);
                        Date obj2Date = (Date) o2.getClass().getDeclaredMethod("getLastModified").invoke(o2);

                        int i = (obj1Date.compareTo(obj2Date));
                        if ((i != 0) || (methodList.size() == 0)) {
                            result = i;
                        } else {
                            result = compare(o1, o2);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        result = 0;
                    }
                }
                break;

            // sort by name, e.g. in CommunityEntity
            case SortParameters.NAME:
            case SortParameters.NAME_REV:
                 {
                    try {
                        String obj1Name = o1.getClass().getDeclaredMethod("getName").invoke(o1).toString();
                        String obj2Name = o2.getClass().getDeclaredMethod("getName").invoke(o2).toString();
                        int i = (obj1Name.compareToIgnoreCase(obj2Name));
                        if ((i != 0) || (methodList.size() == 0)) {
                            result = i;
                        } else {
                            result = compare(o1, o2);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        result = 0;
                    }

                }
                break;

            // in default case it sorts by item id
            default: {
                try {
                    Integer obj1Id = Integer.parseInt(o1.getClass().getDeclaredMethod("getId").invoke(o1).toString());
                    Integer obj2Id = Integer.parseInt(o2.getClass().getDeclaredMethod("getId").invoke(o2).toString());
                    if (obj1Id > obj2Id) {
                        result = 1;
                    } else if (obj1Id < obj2Id) {
                        result = -1;
                    } else {
                        result = 0;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    result = 0;
                }
            }
        }

        // for _REV methods just invert the results
        if (method > 300) {
            result = result * -1;
        }
        return result;
    }
}
