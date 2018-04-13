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
public class TypicalInternships {

    public static final Internship DATASCIENCE = new InternshipBuilder().withName("Grab").withSalary("1500")
            .withEmail("Grab@example.com").withAddress("Grab Building")
            .withIndustry("Tech").withRegion("Town").withRole("Data Scientist").build();
    public static final Internship ENGINEERING1 = new InternshipBuilder().withName("ST Engineering").withSalary("1000")
            .withEmail("STEngineering@example.com").withAddress("123, Jurong West Ave 6, #08-111")
            .withIndustry("Manufacturing").withRegion("Jurong").withRole("Safety Officer").build();
    public static final Internship ENGINEERING2 = new InternshipBuilder().withName("ABC Engineering").withSalary("999")
            .withEmail("ABCEngineering@example.com").withAddress("311, Jurong Ave 2")
            .withIndustry("Manufacturing").withRegion("Jurong").withRole("Product Engineer").build();
    public static final Internship ENGINEERING3 = new InternshipBuilder().withName("Sembcorp").withSalary("1001")
            .withEmail("Sembcorp@example.com").withAddress("111 Tuas Street")
            .withIndustry("Engineering").withRegion("Tuas").withRole("Project Manager").build();
    public static final Internship BUSINESS1 = new InternshipBuilder().withName("PwC").withSalary("1200")
            .withEmail("AdvisoryIntern@example.com").withAddress("Raffles Place").withTags("saved")
            .withIndustry("Business").withRegion("Raffles Place").withRole("Advisory Intern").build();
    public static final Internship BUSINESS2 = new InternshipBuilder().withName("Deloitte").withSalary("750")
            .withEmail("AuditIntern@Deloitte.com").withAddress("Tanjong Pagar")
            .withIndustry("Business").withRegion("Tanjong Pagar").withRole("Audit Intern").build();
    public static final Internship BUSINESS3 = new InternshipBuilder().withName("KPMG").withSalary("1000")
            .withEmail("ConsultingIntern@example.com").withAddress("Raffles Boulevard")
            .withIndustry("Business").withRegion("Raffles Place").withRole("Consulting Intern").build();
    public static final Internship BUSINESS4 = new InternshipBuilder().withName("PwC").withSalary("761")
            .withEmail("AuditIntern@PwC.com").withAddress("Raffles Place")
            .withIndustry("Business").withRegion("Raffles Place").withRole("Audit Intern").build();

    // Manually added
    public static final Internship HEALTHCARE = new InternshipBuilder().withName("Philips Healthcare")
            .withSalary("1000").withEmail("PhilipsHealthcare@example.com").withAddress("Toa Payoh Central")
            .withIndustry("Healthcare").withRegion("Toa Payoh").withRole("Marketing Intern").build();
    public static final Internship CONSULTING = new InternshipBuilder().withName("McKinsey").withSalary("2000")
            .withEmail("Consulting@example.com").withAddress("Sandcrawler Building").withIndustry("Consulting")
            .withRegion("One North").withRole("Business Analyst").build();


    public static final String KEYWORD_MATCHING_AUDIT = "Audit"; // A keyword that matches AUDIT

    private TypicalInternships() {} // prevents instantiation

    /**
     * Returns an {@code JobbiBot} with all the typical internships.
     */
    public static JobbiBot getTypicalInternshipBook() {
        JobbiBot jb = new JobbiBot();
        for (Internship internship : getTypicalInternships()) {
            try {
                jb.addInternship(internship);
            } catch (DuplicateInternshipException e) {
                throw new AssertionError("not possible");
            }
        }
        return jb;
    }

    public static List<Internship> getTypicalInternships() {
        return new ArrayList<>(Arrays.asList(DATASCIENCE, ENGINEERING1, ENGINEERING2, ENGINEERING3,
                BUSINESS1, BUSINESS2, BUSINESS3, BUSINESS4));
    }
}
