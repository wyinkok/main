package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyJobbiBot;

/**
 * A class to access JobbiBot data stored as an xml file on the hard disk.
 */
public class XmlJobbiBotStorage implements JobbiBotStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlJobbiBotStorage.class);

    private String filePath;

    public XmlJobbiBotStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getJobbiBotFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyJobbiBot> readInternshipBook() throws DataConversionException, IOException {
        return readInternshipBook(filePath);
    }

    /**
     * Similar to {@link #readInternshipBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyJobbiBot> readInternshipBook(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("JobbiBot file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        XmlSerializableJobbiBot xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + addressBookFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveInternshipBook(ReadOnlyJobbiBot addressBook) throws IOException {
        saveInternshipBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveInternshipBook(ReadOnlyJobbiBot)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveInternshipBook(ReadOnlyJobbiBot addressBook, String filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableJobbiBot(addressBook));
    }

}
