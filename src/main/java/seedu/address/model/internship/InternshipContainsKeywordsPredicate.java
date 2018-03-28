package seedu.address.model.internship;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Internship}'s {@code Name, Salary, Address, Email or Industry} matches any of the keywords given.
 */
public class InternshipContainsKeywordsPredicate implements Predicate<Internship> {
    private final List<String> keywords;

    public InternshipContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

@Override
    public boolean test(Internship internship) {
    return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(internship.getName().fullName, keyword))
            || keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(internship.getSalary().value, keyword))
            || keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(internship.getAddress().value, keyword))
            || keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(internship.getEmail().value, keyword));
}

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InternshipContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((InternshipContainsKeywordsPredicate) other).keywords)); // state check
    }

}
