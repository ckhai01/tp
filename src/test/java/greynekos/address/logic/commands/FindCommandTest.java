package greynekos.address.logic.commands;

import static greynekos.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static greynekos.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static greynekos.address.testutil.TypicalPersons.CARL;
import static greynekos.address.testutil.TypicalPersons.ELLE;
import static greynekos.address.testutil.TypicalPersons.FIONA;
import static greynekos.address.testutil.TypicalPersons.getTypicalGreyBook;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import greynekos.address.logic.commands.stubs.FindPersonArgumentParseResultStub;
import greynekos.address.model.Model;
import greynekos.address.model.ModelManager;
import greynekos.address.model.UserPrefs;
import greynekos.address.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalGreyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalGreyBook(), new UserPrefs());

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPersonArgumentParseResultStub argStub = new FindPersonArgumentParseResultStub(" ");
        FindCommand command = new FindCommand();
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, argStub, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindPersonArgumentParseResultStub argStub = new FindPersonArgumentParseResultStub("Kurz Elle Kunz");
        FindCommand command = new FindCommand();
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, argStub, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
