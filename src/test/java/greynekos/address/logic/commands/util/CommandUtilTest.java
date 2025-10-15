package greynekos.address.logic.commands.util;

import static greynekos.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import greynekos.address.commons.core.index.Index;
import greynekos.address.logic.commands.exceptions.CommandException;
import greynekos.address.model.Model;
import greynekos.address.model.ModelManager;
import greynekos.address.model.UserPrefs;
import greynekos.address.model.person.Person;
import greynekos.address.model.person.StudentID;
import greynekos.address.testutil.TypicalPersons;

public class CommandUtilTest {

    private static final String NOT_FOUND_MSG = "Error, user does not exist.";

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalGreyBook(), new UserPrefs());
    }

    @Test
    public void resolvePersonByIndex_success() throws Exception {
        // one-based index "1" should resolve to the first person in the currently
        // filtered list
        Person expected = model.getFilteredPersonList().get(0);
        Person resolved = CommandUtil.resolvePerson(model, INDEX_FIRST_PERSON);
        assertEquals(expected, resolved);
    }

    @Test
    public void resolvePersonByIndex_outOfBoundsThrowsCommandException() {
        int outOfBoundsOneBased = model.getFilteredPersonList().size() + 1;
        CommandException ex = assertThrows(CommandException.class,
                () -> CommandUtil.resolvePerson(model, Index.fromOneBased(outOfBoundsOneBased)));
        assertEquals(NOT_FOUND_MSG, ex.getMessage());
    }

    @Test
    public void resolvePersonByStudentId_success() throws Exception {
        Person expected = model.getFilteredPersonList().get(0);

        Person resolved = CommandUtil.resolvePerson(model, expected.getStudentID());
        assertEquals(expected, resolved);
    }

    @Test
    public void resolvePersonByStudentId_notFoundThrowsCommandException() {
        String missingId = generateMissingValidStudentId();
        CommandException ex =
                assertThrows(CommandException.class, () -> CommandUtil.resolvePerson(model, new StudentID(missingId)));
        assertEquals(NOT_FOUND_MSG, ex.getMessage());
    }

    @Test
    public void isIndex_basicCases() {
        assertTrue(CommandUtil.isIndex("1"));
        assertTrue(CommandUtil.isIndex("2"));

        assertFalse(CommandUtil.isIndex("0"));
        assertFalse(CommandUtil.isIndex("-1"));
        assertFalse(CommandUtil.isIndex("abc"));
    }

    /**
     * Produces a valid-looking student ID that is guaranteed not to appear in the
     * current model data. Assumes standard AB3-style pattern A\d{7}[A-Z].
     */
    private String generateMissingValidStudentId() {
        Set<String> existing =
                model.getFilteredPersonList().stream().map(p -> p.getStudentID().value).collect(Collectors.toSet());

        // Try a sequence of valid IDs until we find one not present.
        // Start from A0000000Z and bump the trailing letter if needed.
        String baseDigits = "0000000";
        for (char suffix = 'Z'; suffix >= 'A'; suffix--) {
            String candidate = "A" + baseDigits + suffix;
            if (!existing.contains(candidate)) {
                return candidate;
            }
        }
        // As a fallback, vary the digits slightly (still valid format).
        for (int i = 1; i < 10; i++) {
            String candidate = "A000000" + i + "X";
            if (!existing.contains(candidate)) {
                return candidate;
            }
        }
        // Extremely unlikely to happen with typical test data.
        throw new IllegalStateException("Could not generate a non-colliding StudentID for tests.");
    }
}
