package greynekos.greybook.logic.commands;

import static greynekos.greybook.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static java.util.Objects.requireNonNull;

import greynekos.greybook.logic.commands.exceptions.CommandException;
import greynekos.greybook.logic.parser.ArgumentParseResult;
import greynekos.greybook.logic.parser.GreyBookParser;
import greynekos.greybook.model.Model;

/**
 * Lists all persons in the greybook book to the user.
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
