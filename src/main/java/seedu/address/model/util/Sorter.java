package seedu.address.model.util;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.internship.Internship;

//@@author niloc94
/**
 * Takes in keywords for attributes and creates comparator object for sorting
 */
public class Sorter {

    private static String attribute1;
    private static String attribute2;
    private static String attribute3;

    /**
     * Assigns keyword value to attribute
     *
     * @param keywords
     */
    public static void assignValuesToAttributes(List<String> keywords) {
        attribute1 = keywords.get(0);
        if (keywords.size() == 2) {
            attribute2 = keywords.get(1);
            attribute3 = keywords.get(1);
        } else if (keywords.size() >= 3) {
            attribute2 = keywords.get(1);
            attribute3 = keywords.get(2);
        } else {
            attribute2 = keywords.get(0);
            attribute3 = keywords.get(0);
        }
    }

    /**
     * Creates a comparator which sort according objects according to three attributes entered by the user
     *
     * @param keywords
     * @return
     */
    public static Comparator<Internship> makeComparator(List<String> keywords) {
        assignValuesToAttributes(keywords);
        if (attribute1.equalsIgnoreCase("salary")) {
            return new Comparator<Internship>() {
                @Override
                public int compare(Internship o1, Internship o2) {
                    if (new Integer(Integer.parseInt(o2.getValue(attribute1)))
                            .compareTo(new Integer(Integer.parseInt(o1.getValue(attribute1)))) == 0
                            && o1.getValue(attribute2).compareTo(o2.getValue(attribute2)) == 0) {
                        return (o1.getValue(attribute3).compareTo(o2.getValue(attribute3)));
                    } else if (new Integer(Integer.parseInt(o1.getValue(attribute1)))
                            .compareTo(new Integer(Integer.parseInt(o2.getValue(attribute1)))) == 0) {
                        return (o1.getValue(attribute2).compareTo(o2.getValue(attribute2)));
                    } else {
                        return new Integer(Integer.parseInt(o1.getValue(attribute1)))
                                .compareTo(new Integer(Integer.parseInt(o2.getValue(attribute1))));
                    }
                }
            };
        } else if (attribute2.equalsIgnoreCase("salary")) {
            return new Comparator<Internship>() {
                @Override
                public int compare(Internship o1, Internship o2) {
                    if (o1.getValue(attribute1).compareTo(o2.getValue(attribute1)) == 0
                            && new Integer(Integer.parseInt(o2.getValue(attribute2)))
                            .compareTo(new Integer(Integer.parseInt(o1.getValue(attribute2)))) == 0) {
                        return (o1.getValue(attribute3).compareTo(o2.getValue(attribute3)));
                    } else if (o1.getValue(attribute1).compareTo(o2.getValue(attribute1)) == 0) {
                        return (o1.getValue(attribute2).compareTo(o2.getValue(attribute2)));
                    } else {
                        return o1.getValue(attribute1).compareTo(o2.getValue(attribute1));
                    }
                }
            };
        } else if (attribute3.equalsIgnoreCase("salary")) {
            return new Comparator<Internship>() {
                @Override
                public int compare(Internship o1, Internship o2) {
                    if (o1.getValue(attribute1).compareTo(o2.getValue(attribute1)) == 0
                            && o1.getValue(attribute2).compareTo(o2.getValue(attribute2)) == 0) {
                        return new Integer(Integer.parseInt(o2.getValue(attribute2)))
                                .compareTo(new Integer(Integer.parseInt(o1.getValue(attribute2))));
                    } else if (o1.getValue(attribute1).compareTo(o2.getValue(attribute1)) == 0) {
                        return (o1.getValue(attribute2).compareTo(o2.getValue(attribute2)));
                    } else {
                        return o1.getValue(attribute1).compareTo(o2.getValue(attribute1));
                    }
                }
            };
        } else {
            return new Comparator<Internship>() {
                @Override
                public int compare(Internship o1, Internship o2) {
                    if (o1.getValue(attribute1).compareTo(o2.getValue(attribute1)) == 0
                            && o1.getValue(attribute2).compareTo(o2.getValue(attribute2)) == 0) {
                        return (o1.getValue(attribute3).compareTo(o2.getValue(attribute3)));
                    } else if (o1.getValue(attribute1).compareTo(o2.getValue(attribute1)) == 0) {
                        return (o1.getValue(attribute2).compareTo(o2.getValue(attribute2)));
                    } else {
                        return o1.getValue(attribute1).compareTo(o2.getValue(attribute1));
                    }
                }
            };
        }
    }
}


