package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.Industry;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.Name;
import seedu.address.model.internship.Salary;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Internship[] getSampleInternships() {
        return new Internship[] {
            new Internship(new Name("Alex Yeoh"), new Salary("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Industry("Engineering"),
                getTagSet("friends")),
            new Internship(new Name("Bernice Yu"), new Salary("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Industry("Business"),
                getTagSet("colleagues", "friends")),
            new Internship(new Name("Charlotte Oliveiro"), new Salary("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Industry("Finance"),
                getTagSet("neighbours")),
            new Internship(new Name("David Li"), new Salary("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Industry("Consulting"),
                getTagSet("family")),
            new Internship(new Name("Irfan Ibrahim"), new Salary("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Industry("Manufacturing"),
                getTagSet("classmates")),
            new Internship(new Name("Roy Balakrishnan"), new Salary("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Industry("Services"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Internship sampleInternship : getSampleInternships()) {
                sampleAb.addInternship(sampleInternship);
            }
            return sampleAb;
        } catch (DuplicateInternshipException e) {
            throw new AssertionError("sample data cannot contain duplicate internships", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
