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
     * Helper method to compare string and integer values in attributes
     * String values are compareTo each other without case sensitivity
     * Integer values (salary) are compareTo each other to sort from highest to lowest
     * '-' char at the start of attributeToCompare flips the order of the Internship objects being compared
     *
     * @param attributeToCompare the internship attribute to compare
     * @param o1 internship object 1
     * @param o2 internship object 2
     * @return
     */
    private static int attributeCompare(Internship o1, Internship o2, String attributeToCompare) {
        if (attributeToCompare.equalsIgnoreCase("salary")) {
            return new Integer(Integer.parseInt(o2.getValue(attributeToCompare)))
                    .compareTo(new Integer(Integer.parseInt(o1.getValue(attributeToCompare))));
        } else if (attributeToCompare.equalsIgnoreCase("-salary")) {
            return new Integer(Integer.parseInt(o1.getValue(attributeToCompare)))
                    .compareTo(new Integer(Integer.parseInt(o2.getValue(attributeToCompare))));
        } else if (attributeToCompare.charAt(0) == '-') {
            return o2.getValue(attributeToCompare).toLowerCase()
                    .compareTo(o1.getValue(attributeToCompare).toLowerCase());
        } else {
            return o1.getValue(attributeToCompare).toLowerCase()
                    .compareTo(o2.getValue(attributeToCompare).toLowerCase());
        }
    }

    /**
     * Creates a comparator which sort according objects according to three attributes entered by the user
     * Keyword attributes are sorted from A-Z by default (ignoring case sensitivity)
     * Integer values are sorted from highest to lowest by default
     * For keywords with '-' at the start of that keyword attribute,
     * the reverses sort order for that attribute is applied
     *
     * @param keywords the list of string keys entered by the user
     * @return a custom comparator which compare two Internship Object according to their the keyword attributes given
     */
    public static Comparator<Internship> makeComparator(List<String> keywords) {
        assignValuesToAttributes(keywords);
        return new Comparator<Internship>() {
            @Override
            public int compare(Internship o1, Internship o2) {
                if (attributeCompare(o1, o2, attribute1) == 0 && attributeCompare(o1, o2, attribute1) == 0) {
                    return attributeCompare(o1, o2, attribute3);
                } else if (attributeCompare(o1, o2, attribute1) == 0 && attributeCompare(o1, o2, attribute2) != 0) {
                    return attributeCompare(o1, o2, attribute2);
                } else {
                    return attributeCompare(o1, o2, attribute1);
                }
            }
        };
    }
}


