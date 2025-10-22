package greynekos.greybook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import greynekos.greybook.commons.exceptions.DataLoadingException;
import greynekos.greybook.model.GreyBook;
import greynekos.greybook.model.ReadOnlyGreyBook;

/**
 * Represents a storage for {@link GreyBook}.
 */
public interface GreyBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getGreyBookFilePath();

    /**
     * Returns GreyBook data as a {@link ReadOnlyGreyBook}. Returns
     * {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException
     *             if loading the data from storage failed.
     */
    Optional<ReadOnlyGreyBook> readGreyBook() throws DataLoadingException;

    /**
     * @see #getGreyBookFilePath()
     */
    Optional<ReadOnlyGreyBook> readGreyBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyGreyBook} to the storage.
     *
     * @param greyBook
     *            cannot be null.
     * @throws IOException
     *             if there was any problem writing to the file.
     */
    void saveGreyBook(ReadOnlyGreyBook greyBook) throws IOException;

    /**
     * @see #saveGreyBook(ReadOnlyGreyBook)
     */
    void saveGreyBook(ReadOnlyGreyBook greyBook, Path filePath) throws IOException;

}
