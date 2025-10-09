package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.CommandUtil;
import seedu.address.logic.parser.ArgumentParseResult;
import seedu.address.logic.parser.GreyBookParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.commandoption.SinglePreambleOption;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Displays a person identified using either the displayed index or student ID
 * from the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Listed person";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Displays the person identified by the index number or student ID.\n"
                    + "Parameters: INDEX (must be a positive integer) or STUDENTID (format: A0000000L)\n" + "Example: "
                    + COMMAND_WORD + " 1 OR " + COMMAND_WORD + " A0123456X";

    private final SinglePreambleOption<String> identifierOption =
            SinglePreambleOption.of("INDEX or STUDENTID", ParserUtil::parseDeleteIdentifier);

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, MESSAGE_USAGE, this).addOptions(identifierOption);
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException {
        requireNonNull(model);

        String identifier = getParseResult(arg);
        if (!CommandUtil.isIndex(identifier)) {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        }
        Person personToView = CommandUtil.resolvePerson(model, identifier);
        Predicate<Person> personPredicate = p -> (p.equals(personToView));
        model.updateFilteredPersonList(personPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public String getParseResult(ArgumentParseResult argResult) {
        return argResult.getValue(identifierOption);
    }
}
