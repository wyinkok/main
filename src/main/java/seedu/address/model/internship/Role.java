package seedu.address.model.internship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
//@@author TanCiKang
/**
 * Represents a Internship's role in the internship book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    public static final String MESSAGE_ROLE_CONSTRAINTS =
            "Internship role can take any values and it should not be blank";

    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ROLE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Role}.
     *
     * @param role A valid role.
     */
    public Role (String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_ROLE_CONSTRAINTS);
        this.value = role;
    }

    /**
     * Returns true if a given string is a valid internship role.
     */
    public static boolean isValidRole(String test) {
        return test.matches(ROLE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Role // instanceof handles nulls
                && this.value.equals(((Role) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
