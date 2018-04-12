package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDUSTRY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDUSTRY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REGION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REGION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.JobbiBot;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;

/**
 * A utility class containing a list of {@code Internship} objects to be used in tests.
 */
public class TypicalInternships {

    public static final Internship ALICE = new InternshipBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withSalary("85355255").withIndustry("Engineering").withRegion("Jurong").withRole("Safety Officer")
            .withTags("friends").build();
    public static final Internship BENSON = new InternshipBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withSalary("98765432").withIndustry("Business").withRegion("Clementi")
            .withRole("Business Analyst").withTags("owesMoney", "friends").build();
    public static final Internship CARL = new InternshipBuilder().withName("Carl Kurz").withSalary("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withIndustry("Finance").withRegion("Geylang")
            .withRole("Project Manager").build();
    public static final Internship DANIEL = new InternshipBuilder().withName("Daniel Meier").withSalary("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withIndustry("Consulting")
            .withRegion("Serangoon").withRole("Consultant Assistant").build();
    public static final Internship ELLE = new InternshipBuilder().withName("Elle Meyer").withSalary("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withIndustry("Manufacturing")
            .withRegion("Ang Mo Kio").withRole("Quality Inspector").build();
    public static final Internship FIONA = new InternshipBuilder().withName("Fiona Kunz").withSalary("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withIndustry("Services")
            .withRegion("Serangoon").withRole("Customer Service").build();
    public static final Internship GEORGE = new InternshipBuilder().withName("George Best").withSalary("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withIndustry("Engineering")
            .withRegion("Tampines").withRole("Safety Officer").build();
    public static final Internship CARL2 = new InternshipBuilder().withName("Carl").withSalary("1234567")
            .withEmail("heinz2@example.com").withAddress("wall street 2").withIndustry("Business")
            .withRegion("Aljunied").withRole("Business Analyst").build();

    // Manually added
    public static final Internship HOON = new InternshipBuilder().withName("Hoon Meier").withSalary("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withIndustry("Engineering")
            .withRegion("Geylang").withRole("Safety Officer").build();
    public static final Internship IDA = new InternshipBuilder().withName("Ida Mueller").withSalary("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withIndustry("Business")
            .withRegion("Serangoon").withRole("Business Analyst").build();

    // Manually added - Internship's details found in {@code CommandTestUtil}
    public static final Internship AMY = new InternshipBuilder().withName(VALID_NAME_AMY).withSalary(VALID_SALARY_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withIndustry(VALID_INDUSTRY_AMY)
            .withRegion(VALID_REGION_AMY).withRole(VALID_ROLE_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Internship BOB = new InternshipBuilder().withName(VALID_NAME_BOB).withSalary(VALID_SALARY_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withIndustry(VALID_INDUSTRY_BOB)
            .withRegion(VALID_REGION_BOB).withRole(VALID_ROLE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalInternships() {} // prevents instantiation

    /**
     * Returns an {@code JobbiBot} with all the typical internships.
     */
    public static JobbiBot getTypicalInternshipBook() {
        JobbiBot ab = new JobbiBot();
        for (Internship internship : getTypicalInternships()) {
            try {
                ab.addInternship(internship);
            } catch (DuplicateInternshipException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Internship> getTypicalInternships() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, CARL2));
    }
}
