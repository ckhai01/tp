package greynekos.greybook.logic.commands;

import static greynekos.greybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static greynekos.greybook.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import greynekos.greybook.logic.commands.stubs.ArgumentParseResultStub;
import greynekos.greybook.model.Model;
import greynekos.greybook.model.ModelManager;

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
