package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.JobbiBot;
import seedu.address.model.ReadOnlyJobbiBot;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.Industry;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.Name;
import seedu.address.model.internship.Region;
import seedu.address.model.internship.Role;
import seedu.address.model.internship.Salary;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code InternshipBook} with sample data.
 */
public class SampleDataUtil {
    public static Internship[] getSampleInternships() {
        return new Internship[] {
            new Internship(new Name("Yunomori Onsen Design Advertising Interns"), new Salary("800"),
                    new Email("phoebe@yunomorionsen.com.sg"),new Address("1 Stadium Place, #02-17/18 Kallang "
                    + "Wave Mall, 397628"), new Industry("ArtsandDesign"), new Region("Central Region"),
                    new Role("Design / Advertising"), getTagSet()),
            new Internship(new Name("Deloitte"), new Salary("750"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Industry("Business"),
                    new Region("Serangoon"), new Role("Business Development"), getTagSet()),
            new Internship(new Name("Deloitte"), new Salary("750"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Industry("Business"),
                    new Region("Ang Mo Kio"), new Role("Marketing"), getTagSet()),
            new Internship(new Name("Deloitte"), new Salary("750"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Industry("Business"),
                    new Region("Serangoon"), new Role("Operations"), getTagSet()),
            new Internship(new Name("Sephora Singapore"), new Salary("1200"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Industry("Technology"),
                    new Region("Tampines"), new Role("Web Developer"), getTagSet()),
            new Internship(new Name("Sephora Singapore"), new Salary("1200"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Industry("Technology"), new Region("Aljunied"),
                    new Role("iOS Developer"), getTagSet()),
            new Internship(new Name("Oliver Wyman"), new Salary("1000"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Industry("Consulting"), new Region("Aljunied"),
                new Role("Consultant Assistant"), getTagSet()),
            new Internship(new Name("Oliver Wyman"), new Salary("800"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Industry("Consulting"), new Region("Aljunied"),
                new Role("Public Relations"), getTagSet()),
            new Internship(new Name("Oliver Wyman"), new Salary("800"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Industry("Consulting"), new Region("Aljunied"),
                new Role("Human Resource"), getTagSet()),
            new Internship(new Name("Google"), new Salary("1500"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Technology"), new Region("Tampines"),
                new Role("Software Engineer"), getTagSet()),
            new Internship(new Name("Google"), new Salary("1500"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Technology"), new Region("Tampines"),
                new Role("UX Designer"), getTagSet()),
            new Internship(new Name("Google"), new Salary("1500"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Technology"), new Region("Tampines"),
                new Role("UI Designer"), getTagSet()),
            new Internship(new Name("Quantcast"), new Salary("3000"), new Email("jennie@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Technology"), new Region("Tampines"),
                new Role("Software Engineer"), getTagSet()),
        };
    }

    public static ReadOnlyJobbiBot getSampleJobbiBot() {
        try {
            JobbiBot sampleAb = new JobbiBot();
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
