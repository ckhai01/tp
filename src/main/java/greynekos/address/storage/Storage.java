package greynekos.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import greynekos.address.commons.exceptions.DataLoadingException;
import greynekos.address.model.ReadOnlyGreyBook;
import greynekos.address.model.ReadOnlyUserPrefs;
import greynekos.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends GreyBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    Path getGreyBookFilePath();

    @Override
    Optional<ReadOnlyGreyBook> readGreyBook() throws DataLoadingException;

    @Override
    void saveGreyBook(ReadOnlyGreyBook greyBook) throws IOException;

}
