package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.JobbiBot;
import seedu.address.model.ReadOnlyJobbiBot;

/**
 * An Immutable JobbiBot that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableJobbiBot {

    @XmlElement
    private List<XmlAdaptedInternship> internships;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableJobbiBot.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableJobbiBot() {
        internships = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableJobbiBot(ReadOnlyJobbiBot src) {
        this();
        internships.addAll(src.getInternshipList().stream().map(XmlAdaptedInternship::new)
                .collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code JobbiBot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedInternship} or {@code XmlAdaptedTag}.
     */
    public JobbiBot toModelType() throws IllegalValueException {
        JobbiBot jobbiBot = new JobbiBot();
        for (XmlAdaptedTag t : tags) {
            jobbiBot.addTag(t.toModelType());
        }
        for (XmlAdaptedInternship p : internships) {
            jobbiBot.addInternship(p.toModelType());
        }
        return jobbiBot;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableJobbiBot)) {
            return false;
        }

        XmlSerializableJobbiBot otherAb = (XmlSerializableJobbiBot) other;
        return internships.equals(otherAb.internships) && tags.equals(otherAb.tags);
    }
}
