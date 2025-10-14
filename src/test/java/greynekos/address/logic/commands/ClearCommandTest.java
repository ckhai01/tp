package greynekos.address.logic.commands;

import static greynekos.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static greynekos.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import greynekos.address.logic.commands.stubs.ArgumentParseResultStub;
import greynekos.address.model.AddressBook;
import greynekos.address.model.Model;
import greynekos.address.model.ModelManager;
import greynekos.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, new ArgumentParseResultStub(), ClearCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, new ArgumentParseResultStub(), ClearCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

}
