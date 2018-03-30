package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INDUSTRY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INDUSTRY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDUSTRY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SALARY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SALARY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SALARY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDUSTRY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDUSTRY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.Industry;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.Location;
import seedu.address.model.internship.Name;
import seedu.address.model.internship.Salary;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.InternshipBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Internship expectedInternship = new InternshipBuilder().withName(VALID_NAME_BOB).withSalary(VALID_SALARY_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withIndustry(VALID_INDUSTRY_BOB)
                .withLocation(VALID_LOCATION_BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedInternship));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedInternship));

        // multiple salaries - last salary accepted
        assertParseSuccess(parser, NAME_DESC_BOB + SALARY_DESC_AMY + SALARY_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedInternship));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedInternship));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedInternship));

        // multiple industries - last industry accepted
        assertParseSuccess(parser, NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INDUSTRY_DESC_AMY + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedInternship));

        // multiple locations - last location accepted
        assertParseSuccess(parser, NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + INDUSTRY_DESC_BOB + LOCATION_DESC_AMY + LOCATION_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedInternship));

        // multiple tags - all accepted
        Internship expectedInternshipMultipleTags =
                new InternshipBuilder().withName(VALID_NAME_BOB).withSalary(VALID_SALARY_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withIndustry(VALID_INDUSTRY_BOB)
                        .withLocation(VALID_LOCATION_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedInternshipMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Internship expectedInternship = new InternshipBuilder().withName(VALID_NAME_AMY).withSalary(VALID_SALARY_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withIndustry(VALID_INDUSTRY_AMY)
                .withLocation(VALID_LOCATION_AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + SALARY_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + INDUSTRY_DESC_AMY + LOCATION_DESC_AMY, new AddCommand(expectedInternship));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB, expectedMessage);

        // missing salary prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_SALARY_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + SALARY_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                        + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                        + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB, expectedMessage);

        // missing industry prefix
        assertParseFailure(parser, NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + VALID_INDUSTRY_BOB + LOCATION_DESC_BOB, expectedMessage);

        // missing location prefix
        assertParseFailure(parser, NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INDUSTRY_DESC_BOB + VALID_LOCATION_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_SALARY_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                        + VALID_INDUSTRY_BOB + VALID_LOCATION_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + SALARY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid salary
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_SALARY_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Salary.MESSAGE_SALARY_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + SALARY_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid industry
        assertParseFailure(parser, NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_INDUSTRY_DESC + LOCATION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Industry.MESSAGE_INDUSTRY_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + INDUSTRY_DESC_BOB + INVALID_LOCATION_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Location.MESSAGE_LOCATION_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + SALARY_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + SALARY_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INDUSTRY_DESC_BOB + LOCATION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
