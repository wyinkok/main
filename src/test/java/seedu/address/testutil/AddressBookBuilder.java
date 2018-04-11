package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.JobbiBot;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code JobbiBot ab = new AddressBookBuilder().withInternship("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private JobbiBot jobbiBot;

    public AddressBookBuilder() {
        jobbiBot = new JobbiBot();
    }

    public AddressBookBuilder(JobbiBot jobbiBot) {
        this.jobbiBot = jobbiBot;
    }

    /**
     * Adds a new {@code Internship} to the {@code JobbiBot} that we are building.
     */
    public AddressBookBuilder withInternship(Internship internship) {
        try {
            jobbiBot.addInternship(internship);
        } catch (DuplicateInternshipException dpe) {
            throw new IllegalArgumentException("internship is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code JobbiBot} that we are building.
     */
    public AddressBookBuilder withTag(String tagName) {
        try {
            jobbiBot.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public JobbiBot build() {
        return jobbiBot;
    }
}
