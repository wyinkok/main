package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedInternship.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalInternships.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.Industry;
import seedu.address.model.internship.Name;
import seedu.address.model.internship.Salary;
import seedu.address.testutil.Assert;

public class XmlAdaptedInternshipTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_SALARY = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_INDUSTRY = "_Engineering";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_SALARY = BENSON.getSalary().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_INDUSTRY = BENSON.getIndustry().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validInternshipDetails_returnsInternship() throws Exception {
        XmlAdaptedInternship internship = new XmlAdaptedInternship(BENSON);
        assertEquals(BENSON, internship.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(INVALID_NAME, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(null, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidSalary_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, INVALID_SALARY, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_TAGS);
        String expectedMessage = Salary.MESSAGE_SALARY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullSalary_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Salary.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, INVALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, null, VALID_ADDRESS, VALID_INDUSTRY,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, INVALID_ADDRESS, VALID_INDUSTRY,
                        VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void
    toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, null, VALID_INDUSTRY,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullIndustry_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Industry.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidIndustry_throwsIllegalValueException() {
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, INVALID_INDUSTRY,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Industry.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedInternship internship =
                new XmlAdaptedInternship(VALID_NAME, VALID_SALARY, VALID_EMAIL, VALID_ADDRESS, VALID_INDUSTRY,
                        invalidTags);
        Assert.assertThrows(IllegalValueException.class, internship::toModelType);
    }

}
