package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentParseResult;
import seedu.address.logic.parser.GreyBookParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.commandoption.SinglePreambleOption;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentID;

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
        Person personToDelete = findPersonToDelete(model, identifier);

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName()));
    }

    /**
     * Finds the person to delete based on the identifier. The identifier can be
     * either an index or a student ID.
     *
     * @param model
     *            The model containing the person list
     * @param identifier
     *            The identifier string (index or student ID)
     * @return The person to delete
     * @throws CommandException
     *             if the person cannot be found
     */
    private Person findPersonToDelete(Model model, String identifier) throws CommandException {
        if (isIndex(identifier)) {
            return findPersonByIndex(model, identifier);
        } else {
            return findPersonByStudentId(model, identifier);
        }
    }

    /**
     * Checks if the identifier is an index.
     *
     * @param identifier
     *            The identifier string
     * @return true if the identifier is a valid index
     */
    private boolean isIndex(String identifier) {
        return StringUtil.isNonZeroUnsignedInteger(identifier);
    }

    /**
     * Finds a person by their index in the displayed list.
     *
     * @param model
     *            The model containing the person list
     * @param indexString
     *            The index as a string
     * @return The person at the specified index
     * @throws CommandException
     *             if the index is out of bounds
     */
    private Person findPersonByIndex(Model model, String indexString) throws CommandException {
        Index targetIndex = Index.fromOneBased(Integer.parseInt(indexString));
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        return lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Finds a person by their student ID.
     *
     * @param model
     *            The model containing the person list
     * @param studentIdString
     *            The student ID as a string
     * @return The person with the specified student ID
     * @throws CommandException
     *             if no person with the student ID is found
     */
    private Person findPersonByStudentId(Model model, String studentIdString) throws CommandException {
        StudentID targetStudentId = new StudentID(studentIdString);
        List<Person> lastShownList = model.getFilteredPersonList();

        Optional<Person> person =
                lastShownList.stream().filter(p -> p.getStudentID().equals(targetStudentId)).findFirst();

        if (!person.isPresent()) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        return person.get();
    }

    @Override
    public String getParseResult(ArgumentParseResult argResult) {
        return argResult.getValue(identifierOption);
    }
}
