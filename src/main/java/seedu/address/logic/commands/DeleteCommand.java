package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.CommandUtil;
import seedu.address.logic.parser.ArgumentParseResult;
import seedu.address.logic.parser.GreyBookParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.commandoption.SinglePreambleOption;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonIdentifier;

/**
 * Deletes a person identified using either the displayed index or student ID
 * from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Deletes the person identified by the index number or student ID.\n"
                    + "Parameters: INDEX (must be a positive integer) or STUDENTID (format: A0000000L)\n" + "Example: "
                    + COMMAND_WORD + " 1 OR " + COMMAND_WORD + " A0123456X";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Member %1$s has been successfully deleted!";

    public static final String MESSAGE_PERSON_NOT_FOUND = "Error, user does not exist.";

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
        Person personToDelete = CommandUtil.resolvePerson(model, identifier);

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName()));
    }

    @Override
    public PersonIdentifier getParseResult(ArgumentParseResult argResult) {
        return argResult.getValue(identifierOption);
    }
}
