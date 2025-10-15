package greynekos.address.logic.commands;

import greynekos.address.logic.commands.exceptions.CommandException;
import greynekos.address.logic.parser.ArgumentParseResult;
import greynekos.address.logic.parser.GreyBookParser;
import greynekos.address.model.Model;

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
