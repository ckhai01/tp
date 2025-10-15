package greynekos.address.storage;

import static greynekos.address.testutil.Assert.assertThrows;
import static greynekos.address.testutil.TypicalPersons.ALICE;
import static greynekos.address.testutil.TypicalPersons.HOON;
import static greynekos.address.testutil.TypicalPersons.IDA;
import static greynekos.address.testutil.TypicalPersons.getTypicalGreyBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import greynekos.address.commons.exceptions.DataLoadingException;
import greynekos.address.model.GreyBook;
import greynekos.address.model.ReadOnlyGreyBook;

public class JsonGreyBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonGreyBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readGreyBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readGreyBook(null));
    }

    private java.util.Optional<ReadOnlyGreyBook> readGreyBook(String filePath) throws Exception {
        return new JsonGreyBookStorage(Paths.get(filePath)).readGreyBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder) : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readGreyBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readGreyBook("notJsonFormatGreyBook.json"));
    }

    @Test
    public void readGreyBook_invalidPersonGreyBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readGreyBook("invalidPersonGreyBook.json"));
    }

    @Test
    public void readGreyBook_invalidAndValidPersonGreyBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readGreyBook("invalidAndValidPersonGreyBook.json"));
    }

    @Test
    public void readAndSaveGreyBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempGreyBook.json");
        GreyBook original = getTypicalGreyBook();
        JsonGreyBookStorage jsonGreyBookStorage = new JsonGreyBookStorage(filePath);

        // Save in new file and read back
        jsonGreyBookStorage.saveGreyBook(original, filePath);
        ReadOnlyGreyBook readBack = jsonGreyBookStorage.readGreyBook(filePath).get();
        assertEquals(original, new GreyBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonGreyBookStorage.saveGreyBook(original, filePath);
        readBack = jsonGreyBookStorage.readGreyBook(filePath).get();
        assertEquals(original, new GreyBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonGreyBookStorage.saveGreyBook(original); // file path not specified
        readBack = jsonGreyBookStorage.readGreyBook().get(); // file path not specified
        assertEquals(original, new GreyBook(readBack));

    }

    @Test
    public void saveGreyBook_nullGreyBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveGreyBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code greyBook} at the specified {@code filePath}.
     */
    private void saveGreyBook(ReadOnlyGreyBook greyBook, String filePath) {
        try {
            new JsonGreyBookStorage(Paths.get(filePath)).saveGreyBook(greyBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveGreyBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveGreyBook(new GreyBook(), null));
    }
}
