package seedu.address.model.internship;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.ModelManager;

/**
 * Tests that a {@code Internship}'s {@code Name, Salary, Address, Email or Industry} matches all of the keywords given.
 */
public class InternshipContainsAllKeywordsPredicate implements Predicate<Internship> {
    private final List<String> keywords;

    public InternshipContainsAllKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
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
                || (other instanceof InternshipContainsAllKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((InternshipContainsAllKeywordsPredicate) other).keywords)); // state check
    }

}
