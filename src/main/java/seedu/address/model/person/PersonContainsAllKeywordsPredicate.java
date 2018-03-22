package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name, Phone, Address or Email} matches any of the keywords given.
 */
public class PersonContainsAllKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsAllKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        // Check if all keyword can be found in all of a person's details (e.g name, contact number, address)
        return keywords.stream().allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsAllKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsAllKeywordsPredicate) other).keywords)); // state check
    }
}
