package greynekos.address.logic.commands;

import greynekos.address.logic.commands.exceptions.CommandException;
import greynekos.address.logic.parser.ArgumentParseResult;
import greynekos.address.logic.parser.GreyBookParser;
import greynekos.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, "Exits this app", this);
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }
}
