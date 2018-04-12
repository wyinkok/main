package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalInternships.ALICE;
import static seedu.address.testutil.TypicalInternships.HOON;
import static seedu.address.testutil.TypicalInternships.IDA;
import static seedu.address.testutil.TypicalInternships.getTypicalInternshipBook;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.JobbiBot;
import seedu.address.model.ReadOnlyJobbiBot;

public class XmlJobbiBotStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/"
            + "data/XmlJobbiBotStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readInternshipBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readInternshipBook(null);
    }

    private java.util.Optional<ReadOnlyJobbiBot> readInternshipBook(String filePath) throws Exception {
        return new XmlJobbiBotStorage(filePath).readInternshipBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readInternshipBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readInternshipBook("NotXmlFormatJobbiBot.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readInternshipBook_invalidInternshipInternshipBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readInternshipBook("invalidInternshipJobbiBot.xml");
    }

    @Test
    public void readInternshipBook_invalidAndValidInternshipInternshipBook_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readInternshipBook("invalidAndValidInternshipJobbiBot.xml");
    }

    @Test
    public void readAndsaveInternshipBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempInternshipBook.xml";
        JobbiBot original = getTypicalInternshipBook();
        XmlJobbiBotStorage xmlJobbiBotStorage = new XmlJobbiBotStorage(filePath);

        //Save in new file and read back
        xmlJobbiBotStorage.saveInternshipBook(original, filePath);
        ReadOnlyJobbiBot readBack = xmlJobbiBotStorage.readInternshipBook(filePath).get();
        assertEquals(original, new JobbiBot(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addInternship(HOON);
        original.removeInternship(ALICE);
        xmlJobbiBotStorage.saveInternshipBook(original, filePath);
        readBack = xmlJobbiBotStorage.readInternshipBook(filePath).get();
        assertEquals(original, new JobbiBot(readBack));

        //Save and read without specifying file path
        original.addInternship(IDA);
        xmlJobbiBotStorage.saveInternshipBook(original); //file path not specified
        readBack = xmlJobbiBotStorage.readInternshipBook().get(); //file path not specified
        assertEquals(original, new JobbiBot(readBack));

    }

    @Test
    public void saveInternshipBook_nullInternshipBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveInternshipBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code InternshipBook} at the specified {@code filePath}.
     */
    private void saveInternshipBook(ReadOnlyJobbiBot internshipBook, String filePath) {
        try {
            new XmlJobbiBotStorage(filePath).saveInternshipBook(internshipBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveInternshipBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveInternshipBook(new JobbiBot(), null);
    }


}
