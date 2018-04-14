package seedu.address.model.internship;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Internship}'s {@code Name, Salary, Address, Email or Industry} matches any of the keywords given.
 */
public class InternshipContainsKeywordsPredicate implements Predicate<Internship> {
    private final List<String> keywords;

    public InternshipContainsKeywordsPredicate(List<String> uniqueKeywords) {
        this.keywords = uniqueKeywords;
    }

    //@@author niloc94
    @Override
    public boolean test(Internship internship) {
        // Check if any of the keyword can be found in all of an internship's details (e.g name, industry, location)
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                (internshipAttributeString(internship), keyword));
    }

    //@@author niloc94
    /**
     * Helper method to collate all attributes of internship formats it for searching
     */
    public String internshipAttributeString(Internship internship) {
        // tags currently toString as [tagName], replace [] with whitespace for searching.
        // Also replaces comma with whitespace
        return internship.toString().replaceAll("[\\[+\\]+\\,]", " ");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InternshipContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((InternshipContainsKeywordsPredicate) other).keywords)); // state check
    }
}
