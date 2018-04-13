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
            new Internship(new Name("Yunomori Onsen"), new Salary("800"),
                new Email("phoebe@yunomorionsen.com.sg"), new Address("1 Stadium Place, #02-17/18 Kallang "
                    + "Wave Mall, 397628"), new Industry("Hospitality"), new Region("Central Region"),
                new Role("Design Advertising Interns"), getTagSet()),
            new Internship(new Name("Affinixy Pte Ltd"), new Salary("400"), new Email("charlotte@affinixy.com"),
                new Address("61 Ubi Road 1, Oxley Bizhub 1 #03-40"), new Industry("Media"),
                new Region("Bedok"), new Role("2D Artist Animator Cute Stuff"), getTagSet()),
            new Internship(new Name("Simple Clouds Films"), new Salary("800"), new Email("jamie@simpleclouds.com"),
                new Address("53 Dafne St"), new Industry("Media"),
                new Region("Kembangan"), new Role("Videographer Editor"), getTagSet()),
            new Internship(new Name("Shevron Urgent"), new Salary("800"), new Email("irfan@su.com"),
                new Address("Blk 5 Ang Mo Kio Industrial Park 2A AMK Tech II #06-18"), new Industry("Manufacturing"),
                new Region("Ang Mo Kio"), new Role("Graphic Designer Intern"), getTagSet()),
            new Internship(new Name("Buds Theatre Company"), new Salary("400"), new Email("berniceyu@budstheatre.com"),
                new Address("The Playtent #02-01 180 Joo Chiat Road"), new Industry("ArtsDesign"),
                new Region("Siglap"), new Role("Arts Administrator Intern"), getTagSet()),
            new Internship(new Name("Mount Studio Pte Ltd"), new Salary("750"), new Email("royb@example.com"),
                new Address("45 Jalan Pemimpin #07-02"), new Industry("ArtsDesign"), new Region("Marymount"),
                new Role("Photo Studio Assistant Junior Photographer"), getTagSet("saved")),
            new Internship(new Name("Scooterson Inc"), new Salary("850"), new Email("contact@scootersoninc.com"),
                new Address("Blk 3 Chijmes Street 85, #17-31"), new Industry("Automotive"), new Region("Central"),
                new Role("Mechanical Engineer RD Automotive in Singapore at Scooterson"), getTagSet()),
            new Internship(new Name("AV Intelligence"), new Salary("800"), new Email("royb@avintelligence.com"),
                new Address("9 Raffles Boulevard"), new Industry("Finance"), new Region("Raffles"),
                new Role("Accountant"), getTagSet()),
            new Internship(new Name("Momentum Works"), new Salary("500"), new Email("momentumhr@gmail.com"),
                new Address("Raffles Street 85, #21-31"), new Industry("Finance"), new Region("Central Business "
                    + "District"), new Role("Freelance Accountant Finance Intern"), getTagSet()),
            new Internship(new Name("Kao Singapore Private Limited"), new Salary("900"), new Email("ronaldjohn@outlook"
                    + ".com"), new Address("83 Clemenceau Ave, Singapore 239920"), new Industry("Finance"),
                    new Region("Central Business District"), new Role("Audit Associate Internship"), getTagSet()),
            new Internship(new Name("Daimler South East Asia Pte Ltd"), new Salary("1000"),
                    new Email("yu_yan.ho@daimler.com"), new Address("1 Gateway Drive, #15-01 Westgate Tower, 608531"),
                    new Industry("Technology"), new Region("Jurong"), new Role("Corporate Security Intern"),
                    getTagSet("saved")),
            new Internship(new Name("Pivot FinTech"), new Salary("400"), new Email("jennie@pft.com"),
                new Address("143 Cecil Street #08-00 GB Building Singapore 069452"), new Industry("Finance"),
                    new Region("Raffles Place"), new Role("UI Designer"), getTagSet("saved")),
            new Internship(new Name("Allianz SE Insurance Management Asia Pacific"), new Salary("900"),
                    new Email("jennie@example.com"), new Address("Blk 45 Tampines Street 85, #11-31"),
                    new Industry("Finance"), new Region("Marina"), new Role("Intern Risk Management"), getTagSet()),
            new Internship(new Name("Silver Straits Capital Pte Ltd"), new Salary("600"),
                    new Email("admin@silverstraits.co"), new Address("167 Cecil Street #09-107 Singapore 509452"),
                    new Industry("Finance"), new Region("Raffles Place"), new Role("Private Equity Intern"),
                    getTagSet()),
            new Internship(new Name("Hawksburn Capital"), new Salary("800"), new Email("jennie@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Finance"), new Region("Outram"),
                new Role("Research Analyst Intern"), getTagSet()),
            new Internship(new Name("TalentGuru"), new Salary("1000"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Siglap"),
                new Role("Business Consultant Intern"), getTagSet()),
            new Internship(new Name("Wealth Ridge Solutions"), new Salary("2500"), new Email("jennie@example.com"),
                new Address("600 North Bridge Road Parkview Square Tower 2"), new Industry("Consultancy"),
                new Region("Bugis"), new Role("Trainee Sales Consultant"), getTagSet()),
            new Internship(new Name("Prime Solutions Provider"), new Salary("950"), new Email("email@prsolutions.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Bugis"),
                new Role("Management Trainee Consultant"), getTagSet()),
            new Internship(new Name("Prime Solutions Provider Pte Ltd"), new Salary("850"), new Email("jennie@exa.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Bugis"),
                new Role("Sales Marketing Consultant"), getTagSet()),
            new Internship(new Name("JPSO Consultancy"), new Salary("2800"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Bugis"),
                new Role("Customer Relationship Consultant"), getTagSet()),
            new Internship(new Name("Ispring Group"), new Salary("2500"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Bugis"),
                new Role("Sales Consultant"), getTagSet()),
            new Internship(new Name("B Consultancy"), new Salary("2350"), new Email("jennie@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"),
                    new Region("Tanjong Pagar"), new Role("Financial Consultant Internship"), getTagSet()),
            new Internship(new Name("PurpleClick Media Pte Ltd"), new Salary("400"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Tampines"),
                new Role("Digital Consultant"), getTagSet()),
            new Internship(new Name("Advisors Clique"), new Salary("1500"), new Email("jennie@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Telok Ayer"),
                new Role("Wealth Management Consultant"), getTagSet()),
            new Internship(new Name("Kind of Blue Pte Ltd"), new Salary("1500"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Orchard"),
                new Role("Beauty Sales Consultant"), getTagSet()),
            new Internship(new Name("Rimus Idea"), new Salary("800"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Engineering"), new Region("Ubi"),
                new Role("Design Consultant"), getTagSet()),
            new Internship(new Name("Horizon Education Group of Companies"), new Salary("800"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Education"), new Region("Kallang"),
                new Role("Accounts Intern"), getTagSet()),
            new Internship(new Name("Macs Music School Pte Ltd"), new Salary("800"), new Email("jennie@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Education"), new Region("Orchard"),
                new Role("Intern Violin Teacher"), getTagSet()),
            new Internship(new Name("Pan Asia Logistics Investments Pte Ltd"), new Salary("800"), new Email("roybcd@example.com"),
                new Address("21 Changi North Way, Singapore 498774"), new Industry("Transportation"), new Region("Changi"),
                new Role("Finance Intern"), getTagSet()),
            new Internship(new Name("PACE OD Consulting Pte Ltd"), new Salary("400"), new Email("jennie@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Arts"), new Region("Oueenstown"),
                new Role("Content Curation Intern"), getTagSet())
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
