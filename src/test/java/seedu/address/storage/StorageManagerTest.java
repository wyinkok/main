package seedu.address.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalInternships.getTypicalAddressBook;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.JobbiBotChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.JobbiBot;
import seedu.address.model.ReadOnlyJobbiBot;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlJobbiBotStorage jobbiBotStorage = new XmlJobbiBotStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(jobbiBotStorage, userPrefsStorage);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void JobbiBotReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlJobbiBotStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlJobbiBotStorageTest} class.
         */
        JobbiBot original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyJobbiBot retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new JobbiBot(retrieved));
    }

    @Test
    public void getJobbiBotFilePath() {
        assertNotNull(storageManager.getJobbiBotFilePath());
    }

    @Test
    public void handleJobbiBotChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlJobbiBotStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        storage.handleAddressBookChangedEvent(new JobbiBotChangedEvent(new JobbiBot()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlJobbiBotStorageExceptionThrowingStub extends XmlJobbiBotStorage {

        public XmlJobbiBotStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyJobbiBot addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
