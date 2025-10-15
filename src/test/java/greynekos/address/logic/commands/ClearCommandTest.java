package greynekos.address.logic.commands;

import static greynekos.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static greynekos.address.testutil.TypicalPersons.getTypicalGreyBook;

import org.junit.jupiter.api.Test;

import greynekos.address.logic.commands.stubs.ArgumentParseResultStub;
import greynekos.address.model.GreyBook;
import greynekos.address.model.Model;
import greynekos.address.model.ModelManager;
import greynekos.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyGreyBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, new ArgumentParseResultStub(), ClearCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyGreyBook_success() {
        Model model = new ModelManager(getTypicalGreyBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalGreyBook(), new UserPrefs());
        expectedModel.setGreyBook(new GreyBook());

        assertCommandSuccess(new ClearCommand(), model, new ArgumentParseResultStub(), ClearCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

}
