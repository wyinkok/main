package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalInternships.ALICE;
import static seedu.address.testutil.TypicalInternships.HOON;
import static seedu.address.testutil.TypicalInternships.IDA;
import static seedu.address.testutil.TypicalInternships.getTypicalAddressBook;

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
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlJobbiBotStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyJobbiBot> readAddressBook(String filePath) throws Exception {
        return new XmlJobbiBotStorage(filePath).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatJobbiBot.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAddressBook_invalidInternshipAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidInternshipJobbiBot.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidInternshipAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidInternshipJobbiBot.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        JobbiBot original = getTypicalAddressBook();
        XmlJobbiBotStorage xmlJobbiBotStorage = new XmlJobbiBotStorage(filePath);

        //Save in new file and read back
        xmlJobbiBotStorage.saveAddressBook(original, filePath);
        ReadOnlyJobbiBot readBack = xmlJobbiBotStorage.readAddressBook(filePath).get();
        assertEquals(original, new JobbiBot(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addInternship(HOON);
        original.removeInternship(ALICE);
        xmlJobbiBotStorage.saveAddressBook(original, filePath);
        readBack = xmlJobbiBotStorage.readAddressBook(filePath).get();
        assertEquals(original, new JobbiBot(readBack));

        //Save and read without specifying file path
        original.addInternship(IDA);
        xmlJobbiBotStorage.saveAddressBook(original); //file path not specified
        readBack = xmlJobbiBotStorage.readAddressBook().get(); //file path not specified
        assertEquals(original, new JobbiBot(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyJobbiBot addressBook, String filePath) {
        try {
            new XmlJobbiBotStorage(filePath).saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new JobbiBot(), null);
    }


}
