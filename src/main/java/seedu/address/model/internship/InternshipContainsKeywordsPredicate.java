package seedu.address.model.internship;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Internship}'s {@code Name, Salary, Address or Email} matches any of the keywords given.
 */
public class InternshipContainsKeywordsPredicate implements Predicate<Internship> {
    private final List<String> keywords;

    public InternshipContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test(Internship internship) {
        // Check if all keyword can be found in all of an internship's details (e.g name, industry, location)
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(internshipAttributeString(internship), keyword));
    }

    /**
     * Helper method to collate all attributes of internship formats it for searching
     */
    private String internshipAttributeString(Internship internship) {
        // tags currently toString as [tagName], replace [] with whitespace for searching. Also replaces comma with whitespace
        return new String(internship.toString().replaceAll("[\\[+\\]+\\,]"," "));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InternshipContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((InternshipContainsKeywordsPredicate) other).keywords)); // state check
    }
}
