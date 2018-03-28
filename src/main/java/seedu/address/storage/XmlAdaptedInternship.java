package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.Industry;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.Name;
import seedu.address.model.internship.Salary;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Internship.
 */
public class XmlAdaptedInternship {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Internship's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String salary;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String industry;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedInternship.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedInternship() {}

    /**
     * Constructs an {@code XmlAdaptedInternship} with the given internship details.
     */
    public XmlAdaptedInternship(String name, String salary, String email, String address, String industry,
                                List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.salary = salary;
        this.email = email;
        this.address = address;
        this.industry = industry;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Internship into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedInternship
     */
    public XmlAdaptedInternship(Internship source) {
        name = source.getName().fullName;
        salary = source.getSalary().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        industry = source.getIndustry().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted internship object into the model's Internship object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted internship
     */
    public Internship toModelType() throws IllegalValueException {
        final List<Tag> internshipTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            internshipTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.salary == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Salary.class.getSimpleName()));
        }
        if (!Salary.isValidSalary(this.salary)) {
            throw new IllegalValueException(Salary.MESSAGE_SALARY_CONSTRAINTS);
        }
        final Salary salary = new Salary(this.salary);

        if (this.email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(this.email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email email = new Email(this.email);

        if (this.address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(this.address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address address = new Address(this.address);

        if (!Industry.isValidIndustry(this.industry)) {
            throw new IllegalValueException(Industry.MESSAGE_INDUSTRY_CONSTRAINTS);
        }
        final Industry industry = new Industry(this.industry);

        final Set<Tag> tags = new HashSet<>(internshipTags);
        return new Internship(name, salary, email, address, industry, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedInternship)) {
            return false;
        }

        XmlAdaptedInternship otherInternship = (XmlAdaptedInternship) other;
        return Objects.equals(name, otherInternship.name)
                && Objects.equals(salary, otherInternship.salary)
                && Objects.equals(email, otherInternship.email)
                && Objects.equals(address, otherInternship.address)
                && Objects.equals(industry, otherInternship.industry)
                && tagged.equals(otherInternship.tagged);
    }
}
