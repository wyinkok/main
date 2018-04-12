package seedu.address.model.internship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Internship's region in the internship book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRegion(String)}
 */
public class Region {

    public static final String MESSAGE_REGION_CONSTRAINTS =
            "Internship region can take any values and it should not be blank";

    /*
     * The first character of the region must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String REGION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Region}.
     *
     * @param region A valid region.
     */
    public Region(String region) {
        requireNonNull(region);
        checkArgument(isValidRegion(region), MESSAGE_REGION_CONSTRAINTS);
        this.value = region;
    }

    /**
     * Returns true if a given string is a valid region.
     */
    public static boolean isValidRegion(String test) {
        return test.matches(REGION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Region // instanceof handles nulls
                && this.value.equals(((Region) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
