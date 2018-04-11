package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.JobbiBot;
import seedu.address.model.ReadOnlyJobbiBot;

/**
 * Represents a storage for {@link JobbiBot}.
 */
public interface JobbiBotStorage {

    /**
     * Returns the file path of the data file.
     */
    String getJobbiBotFilePath();

    /**
     * Returns JobbiBot data as a {@link ReadOnlyJobbiBot}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyJobbiBot> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getJobbiBotFilePath()
     */
    Optional<ReadOnlyJobbiBot> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyJobbiBot} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyJobbiBot addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyJobbiBot)
     */
    void saveAddressBook(ReadOnlyJobbiBot addressBook, String filePath) throws IOException;

}
