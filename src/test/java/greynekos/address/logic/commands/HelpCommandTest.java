package greynekos.address.logic.commands;

import static greynekos.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static greynekos.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import greynekos.address.logic.commands.stubs.ArgumentParseResultStub;
import greynekos.address.model.Model;
import greynekos.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, new ArgumentParseResultStub(), expectedCommandResult,
                expectedModel);
    }
}
