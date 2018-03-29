package seedu.address.model.internship;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Internship in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Internship {

    private final Name name;
    private final Salary salary;
    private final Email email;
    private final Address address;
    private final Industry industry;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Internship(Name name, Salary salary, Email email, Address address, Industry industry, Set<Tag> tags) {
        requireAllNonNull(name, salary, email, address, tags);
        this.name = name;
        this.salary = salary;
        this.email = email;
        this.address = address;
        this.industry = industry;
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
                && otherInternship.getAddress().equals(this.getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, salary, email, address, tags);
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
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
