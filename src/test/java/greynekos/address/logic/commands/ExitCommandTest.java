package greynekos.address.logic.commands;

import static greynekos.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static greynekos.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import greynekos.address.logic.commands.stubs.ArgumentParseResultStub;
import greynekos.address.model.Model;
import greynekos.address.model.ModelManager;

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
