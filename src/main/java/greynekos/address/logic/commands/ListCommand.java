package greynekos.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static greynekos.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import greynekos.address.logic.commands.exceptions.CommandException;
import greynekos.address.logic.parser.ArgumentParseResult;
import greynekos.address.logic.parser.GreyBookParser;
import greynekos.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, "Lists all members", this);
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
