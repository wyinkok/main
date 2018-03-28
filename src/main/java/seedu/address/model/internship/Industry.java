package seedu.address.model.internship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Internship's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIndustry(String)}
 */
public class Industry {

    public static final String MESSAGE_INDUSTRY_CONSTRAINTS =
            "Internship industry can take any values, and it should not be blank";

    /*
     * The first character of the industry must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String INDUSTRY_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Industry}.
     *
     * @param industry A valid industry.
     */
    public Industry (String industry) {
        requireNonNull(industry);
        checkArgument(isValidIndustry(industry), MESSAGE_INDUSTRY_CONSTRAINTS);
        this.value = industry;
    }

    /**
     * Returns true if a given string is a valid internship industry.
     */
    public static boolean isValidIndustry(String test) {
        return test.matches(INDUSTRY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Industry // instanceof handles nulls
                && this.value.equals(((Industry) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
