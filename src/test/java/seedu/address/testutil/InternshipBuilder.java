package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.internship.Address;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.Industry;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.Location;
import seedu.address.model.internship.Name;
import seedu.address.model.internship.Salary;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Internship objects.
 */
public class InternshipBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_SALARY = "1000";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_INDUSTRY = "Engineering";
    public static final String DEFAULT_LOCATION = "Geylang";
    public static final String DEFAULT_TAGS = "friends";

    private Name name;
    private Salary salary;
    private Email email;
    private Address address;
    private Industry industry;
    private Location location;
    private Set<Tag> tags;

    public InternshipBuilder() {
        name = new Name(DEFAULT_NAME);
        salary = new Salary(DEFAULT_SALARY);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        industry = new Industry(DEFAULT_INDUSTRY);
        location = new Location(DEFAULT_LOCATION);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the InternshipBuilder with the data of {@code internshipToCopy}.
     */
    public InternshipBuilder(Internship internshipToCopy) {
        name = internshipToCopy.getName();
        salary = internshipToCopy.getSalary();
        email = internshipToCopy.getEmail();
        address = internshipToCopy.getAddress();
        industry = internshipToCopy.getIndustry();
        location = internshipToCopy.getLocation();
        tags = new HashSet<>(internshipToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Internship} that we are building.
     */
    public InternshipBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Internship} that we are building.
     */
    public InternshipBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Internship} that we are building.
     */
    public InternshipBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Salary} of the {@code Internship} that we are building.
     */
    public InternshipBuilder withSalary(String salary) {
        this.salary = new Salary(salary);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Internship} that we are building.
     */
    public InternshipBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Industry} of the {@code Internship} that we are building.
     */
    public InternshipBuilder withIndustry(String industry) {
        this.industry = new Industry(industry);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Internship} that we are building.
     */
    public InternshipBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    public Internship build() {
        return new Internship(name, salary, email, address, industry, location, tags);
    }

}
