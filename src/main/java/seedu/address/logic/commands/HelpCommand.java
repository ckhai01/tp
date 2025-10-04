package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentParseResult;
import seedu.address.logic.parser.GreyBookParser;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Shows program usage instructions.\n" + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, MESSAGE_USAGE, this);
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
