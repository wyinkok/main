package seedu.address.model.internship;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

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
        return keywords.stream().allMatch(keyword -> StringUtil.containsWordIgnoreCase(internship.toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InternshipContainsAllKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((InternshipContainsAllKeywordsPredicate) other).keywords)); // state check
    }

}
