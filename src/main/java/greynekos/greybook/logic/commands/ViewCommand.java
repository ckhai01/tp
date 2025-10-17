package greynekos.greybook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import greynekos.greybook.logic.commands.exceptions.CommandException;
import greynekos.greybook.logic.commands.util.CommandUtil;
import greynekos.greybook.logic.parser.ArgumentParseResult;
import greynekos.greybook.logic.parser.GreyBookParser;
import greynekos.greybook.logic.parser.ParserUtil;
import greynekos.greybook.logic.parser.commandoption.SinglePreambleOption;
import greynekos.greybook.model.Model;
import greynekos.greybook.model.person.Person;
import greynekos.greybook.model.person.PersonIdentifier;

/**
 * Displays a person identified using either the displayed index or student ID
 * from GreyBook.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Listed person";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Displays the person identified by the index number or student ID.\n"
                    + "Parameters: INDEX (must be a positive integer) or STUDENTID (format: A0000000Y)\n" + "Example: "
                    + COMMAND_WORD + " 1 OR " + COMMAND_WORD + " A0123456J";

    private final SinglePreambleOption<PersonIdentifier> identifierOption =
            SinglePreambleOption.of("INDEX or STUDENTID", ParserUtil::parsePersonIdentifier);

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, MESSAGE_USAGE, this).addOptions(identifierOption);
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException {
        requireNonNull(model);

        PersonIdentifier identifier = getParseResult(arg);
        Person personToView = CommandUtil.resolvePerson(model, identifier);
        Predicate<Person> personPredicate = p -> (p.equals(personToView));
        model.updateFilteredPersonList(personPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public PersonIdentifier getParseResult(ArgumentParseResult argResult) {
        return argResult.getValue(identifierOption);
    }
}
