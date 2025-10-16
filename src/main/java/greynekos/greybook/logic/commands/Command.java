package greynekos.greybook.logic.commands;

import greynekos.greybook.logic.commands.exceptions.CommandException;
import greynekos.greybook.logic.parser.ArgumentParseResult;
import greynekos.greybook.logic.parser.GreyBookParser;
import greynekos.greybook.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be
 * executed.
 */
public abstract class Command {
    public Object getParseResult(ArgumentParseResult argResult) {
        return null;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model
     *            {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException
     *             If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException;

    public abstract void addToParser(GreyBookParser parser);
}
