package greynekos.greybook.logic.commands;

import static greynekos.greybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static greynekos.greybook.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import greynekos.greybook.logic.commands.stubs.ArgumentParseResultStub;
import greynekos.greybook.model.Model;
import greynekos.greybook.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, new ArgumentParseResultStub(), expectedCommandResult,
                expectedModel);
    }
}
