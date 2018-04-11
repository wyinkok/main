package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.JobbiBotChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyJobbiBot;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of JobbiBot data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private JobbiBotStorage jobbiBotStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(JobbiBotStorage jobbiBotStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.jobbiBotStorage = jobbiBotStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public String getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ JobbiBot methods ==============================

    @Override
    public String getJobbiBotFilePath() {
        return jobbiBotStorage.getJobbiBotFilePath();
    }

    @Override
    public Optional<ReadOnlyJobbiBot> readInternshipBook() throws DataConversionException, IOException {
        return readInternshipBook(jobbiBotStorage.getJobbiBotFilePath());
    }

    @Override
    public Optional<ReadOnlyJobbiBot> readInternshipBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return jobbiBotStorage.readInternshipBook(filePath);
    }

    @Override
    public void saveInternshipBook(ReadOnlyJobbiBot addressBook) throws IOException {
        saveInternshipBook(addressBook, jobbiBotStorage.getJobbiBotFilePath());
    }

    @Override
    public void saveInternshipBook(ReadOnlyJobbiBot addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        jobbiBotStorage.saveInternshipBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(JobbiBotChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveInternshipBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
