package seedu.address.model.internship;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Internship in the internship book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Internship {

    public static final String ATTRIBUTES_LIST = "Name Salary Email Address Industry Region Role";
    private final Name name;
    private final Salary salary;
    private final Email email;
    private final Address address;
    private final Industry industry;
    private final Region region;
    private final Role role;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Internship(Name name, Salary salary, Email email, Address address, Industry industry, Region region,
                      Role role, Set<Tag> tags) {
        requireAllNonNull(name, salary, email, address, industry, region, tags);
        this.name = name;
        this.salary = salary;
        this.email = email;
        this.address = address;
        this.industry = industry;
        this.region = region;
        this.role = role;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    public Name getName() {
        return name;
    }

    public Salary getSalary() {
        return salary;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Industry getIndustry() {
        return industry;
    }

    public Region getRegion() {
        return region;
    }

    public Role getRole() {
        return role;
    }

    public String getUrl() {

        StringBuilder url = new StringBuilder();
        url.append(getName()).append(' ').append(getRole());
        return url.toString().replace(" ", "-");
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Internship)) {
            return false;
        }

        Internship otherInternship = (Internship) other;
        return otherInternship.getName().equals(this.getName())
                && otherInternship.getSalary().equals(this.getSalary())
                && otherInternship.getEmail().equals(this.getEmail())
                && otherInternship.getAddress().equals(this.getAddress())
                && otherInternship.getIndustry().equals(this.getIndustry())
                && otherInternship.getRegion().equals(this.getRegion())
                && otherInternship.getRole().equals(this.getRole());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, salary, email, address, industry, region, role, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Salary: ")
                .append(getSalary())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Industry: ")
                .append(getIndustry())
                .append(" Region: ")
                .append(getRegion())
                .append(" Role: ")
                .append(getRole())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    //@@author niloc94
    public String getValue(String keyword) {
        switch (keyword.toLowerCase()) {
        case "name":
            return getName().toString();

        case "salary":
            return getSalary().toString();

        case "address":
            return getAddress().toString();

        case "industry":
            return getIndustry().toString();

        case "region":
            return getRegion().toString();

        case "role":
            return getRole().toString();

        case "email":
            return getEmail().toString();

        default:
            assert false; // Keyword already parsed to attribute type. Program should never reach here
            throw new AssertionError();
        }
    }
}
