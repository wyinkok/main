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
    Optional<ReadOnlyJobbiBot> readInternshipBook() throws DataConversionException, IOException;

    /**
     * @see #getJobbiBotFilePath()
     */
    Optional<ReadOnlyJobbiBot> readInternshipBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyJobbiBot} to the storage.
     * @param InternshipBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveInternshipBook(ReadOnlyJobbiBot InternshipBook) throws IOException;

    /**
     * @see #saveInternshipBook(ReadOnlyJobbiBot)
     */
    void saveInternshipBook(ReadOnlyJobbiBot InternshipBook, String filePath) throws IOException;

}
