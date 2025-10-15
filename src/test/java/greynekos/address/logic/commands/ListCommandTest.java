package greynekos.address.logic.commands;

import static greynekos.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static greynekos.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static greynekos.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static greynekos.address.testutil.TypicalPersons.getTypicalGreyBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import greynekos.address.logic.commands.stubs.ArgumentParseResultStub;
import greynekos.address.model.Model;
import greynekos.address.model.ModelManager;
import greynekos.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGreyBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getGreyBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, new ArgumentParseResultStub(), ListCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, new ArgumentParseResultStub(), ListCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
