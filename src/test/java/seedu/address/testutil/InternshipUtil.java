package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDUSTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.internship.Internship;

/**
 * A utility class for Internship.
 */
public class InternshipUtil {

    /**
     * Returns an add command string for adding the {@code internship}.
     */
    public static String getAddCommand(Internship internship) {
        return AddCommand.COMMAND_WORD + " " + getInternshipDetails(internship);
    }

    /**
     * Returns the part of command string for the given {@code internship}'s details.
     */
    public static String getInternshipDetails(Internship internship) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + internship.getName().fullName + " ");
        sb.append(PREFIX_SALARY + internship.getSalary().value + " ");
        sb.append(PREFIX_EMAIL + internship.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + internship.getAddress().value + " ");
        sb.append(PREFIX_INDUSTRY + internship.getIndustry().value + " ");
        sb.append(PREFIX_REGION + internship.getRegion().value + " ");
        sb.append(PREFIX_ROLE + internship.getRole().value + " ");
        internship.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
