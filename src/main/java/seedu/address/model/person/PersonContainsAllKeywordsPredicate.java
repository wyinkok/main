package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.ModelManager;
import seedu.address.model.internship.Internship;

/**
 * Tests that a {@code Person}'s {@code Name, Salary, Address or Email} matches any of the keywords given.
 */
public class PersonContainsAllKeywordsPredicate implements Predicate<Internship> {
    private final List<String> keywords;

    public PersonContainsAllKeywordsPredicate(List<String> keywords) {
        List<String> allKeywords = new ArrayList<String>(keywords);
        allKeywords.addAll(ModelManager.getKeywords());
        this.keywords = allKeywords;
    }

    @Override
    public boolean test(Internship internship) {
        // Check if all keyword can be found in all of a person's details (e.g name, contact number, address)
        return keywords.stream().allMatch(keyword ->
                StringUtil.containsWordIgnoreCase(internshipAttributeString(internship), keyword));
    }

    /**
     * Helper method to collate all attributes of internship formats it for searching
     */
    private String internshipAttributeString(Internship internship) {
        // tags currently toString as [tagName], replace [] with whitespace for searching.
        // Also replaces commas with whitespace
        return new String(internship.toString().replaceAll("[\\[+\\]+\\,]", " "));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsAllKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsAllKeywordsPredicate) other).keywords)); // state check
    }
}
