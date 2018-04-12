package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.JobbiBot;
import seedu.address.testutil.TypicalInternships;

public class XmlSerializableJobbiBotTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableJobbiBotTest/");
    private static final File TYPICAL_INTERNSHIPS_FILE =
            new File(TEST_DATA_FOLDER + "typicalInternshipJobbiBot.xml");
    private static final File INVALID_INTERNSHIP_FILE = new File(TEST_DATA_FOLDER + "invalidInternshipJobbiBot.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagJobbiBot.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalInternshipsFile_success() throws Exception {
        XmlSerializableJobbiBot dataFromFile = XmlUtil.getDataFromFile(TYPICAL_INTERNSHIPS_FILE,
                XmlSerializableJobbiBot.class);
        JobbiBot jobbiBotFromFile = dataFromFile.toModelType();
        JobbiBot typicalInternshipsJobbiBot = TypicalInternships.getTypicalInternshipBook();
        System.out.println(jobbiBotFromFile.getTagList().toString());
        System.out.println(typicalInternshipsJobbiBot.getTagList().toString());
        assertEquals(jobbiBotFromFile, typicalInternshipsJobbiBot);
    }

    @Test
    public void toModelType_invalidInternshipFile_throwsIllegalValueException() throws Exception {
        XmlSerializableJobbiBot dataFromFile = XmlUtil.getDataFromFile(INVALID_INTERNSHIP_FILE,
                XmlSerializableJobbiBot.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidTagFile_throwsIllegalValueException() throws Exception {
        XmlSerializableJobbiBot dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializableJobbiBot.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
