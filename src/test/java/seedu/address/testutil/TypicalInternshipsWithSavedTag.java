//@@author wyinkok
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.JobbiBot;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;

/**
 * A utility class containing a list of {@code Internship} objects to be used in tests.
 */
public class TypicalInternshipsWithSavedTag {

    public static final Internship ALICE = new InternshipBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withSalary("85355255")
            .withTags("friends", "saved").build();
    public static final Internship BENSON = new InternshipBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withSalary("98765432")
            .withTags("owesMoney", "friends", "saved").build();
    public static final Internship CARL = new InternshipBuilder().withName("Carl Kurz").withSalary("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withTags("saved").build();
    public static final Internship DANIEL = new InternshipBuilder().withName("Daniel Meier").withSalary("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("saved").build();
    public static final Internship ELLE = new InternshipBuilder().withName("Elle Meyer").withSalary("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Internship FIONA = new InternshipBuilder().withName("Fiona Kunz").withSalary("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withTags("saved").build();
    public static final Internship GEORGE = new InternshipBuilder().withName("George Best").withSalary("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withTags("saved").build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalInternshipsWithSavedTag() {} // prevents instantiation

    /**
     * Returns an {@code JobbiBot} with all the typical persons.
     */
    public static JobbiBot getTypicalInternshipBookWithSavedTag() {
        JobbiBot ab = new JobbiBot();
        for (Internship internship : getTypicalPersonsWithSavedTag()) {
            try {
                ab.addInternship(internship);
            } catch (DuplicateInternshipException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Internship> getTypicalPersonsWithSavedTag() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
