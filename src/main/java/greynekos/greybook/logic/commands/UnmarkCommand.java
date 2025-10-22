package greynekos.greybook.logic.commands;

import static greynekos.greybook.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static greynekos.greybook.logic.Messages.MESSAGE_MISSING_STUDENTID;
import static greynekos.greybook.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import greynekos.greybook.commons.core.index.Index;
import greynekos.greybook.logic.Messages;
import greynekos.greybook.logic.commands.exceptions.CommandException;
import greynekos.greybook.logic.parser.ArgumentParseResult;
import greynekos.greybook.logic.parser.GreyBookParser;
import greynekos.greybook.logic.parser.ParserUtil;
import greynekos.greybook.logic.parser.commandoption.OptionalSinglePreambleOption;
import greynekos.greybook.model.Model;
import greynekos.greybook.model.person.AttendanceStatus;
import greynekos.greybook.model.person.Person;
import greynekos.greybook.model.person.StudentID;

/**
 * The UnmarkCommand clears a club member's attendance status.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";
    public static final String UNMARK_ALL_KEYWORD = "all";

    public static final String MESSAGE_UNMARK_PERSON_SUCCESS = "Cleared %1$s's attendance status";
    public static final String MESSAGE_UNMARK_ALL_SUCCESS = "All attendance status have been cleared.";

    /**
     * Unmark Command Usage and Error Messages
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears a person's attendance status.\n"
            + "Parameters: INDEX (must be a positive integer) or  STUDENT_ID (format: A0000000Y)\n" + "Example: "
            + COMMAND_WORD + " 1 OR " + COMMAND_WORD + " A0123456J OR " + COMMAND_WORD + " all";

    /**
     * Unmark Command Preamble and Prefix Options
     */

    private final OptionalSinglePreambleOption<String> allOption =
            OptionalSinglePreambleOption.of("UNMARK_ALL", s -> s.equals(UNMARK_ALL_KEYWORD) ? s : null);

    private final OptionalSinglePreambleOption<Index> indexOption =
            OptionalSinglePreambleOption.of("INDEX", ParserUtil::parseIndex);

    private final OptionalSinglePreambleOption<StudentID> studentIdOption =
            OptionalSinglePreambleOption.of("STUDENT_ID", ParserUtil::parseStudentID);

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, MESSAGE_USAGE, this).addOptions(indexOption, studentIdOption, allOption)
                .enforceOnePreamble();
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException {
        requireNonNull(model);

        // Check if this is a reset command using the resetOption
        Optional<String> allOptional = arg.getOptionalValue(allOption);
        if (allOptional.isPresent()) {
            return executeUnmarkAll(model);
        }

        // Get identifier (guaranteed to have exactly one by parser validation)
        Optional<Index> indexOptional = arg.getOptionalValue(indexOption);
        Optional<StudentID> studentIdOptional = arg.getOptionalValue(studentIdOption);

        Person personToUnmark;
        if (studentIdOptional.isPresent()) {
            personToUnmark = getPersonByStudentId(model, studentIdOptional.get());
        } else {
            personToUnmark = getPersonByIndex(model, indexOptional.get());
        }

        Person unmarkedPerson = createUnmarkedPerson(personToUnmark);
        model.setPerson(personToUnmark, unmarkedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_UNMARK_PERSON_SUCCESS, unmarkedPerson.getName(),
                Messages.format(unmarkedPerson)));
    }

    private Person getPersonByIndex(Model model, Index index) throws CommandException {
        List<Person> list = model.getFilteredPersonList();
        if (index.getZeroBased() >= list.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return list.get(index.getZeroBased());
    }

    private Person getPersonByStudentId(Model model, StudentID sid) throws CommandException {
        return model.getPersonByStudentId(sid)
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_MISSING_STUDENTID, sid)));
    }

    private CommandResult executeUnmarkAll(Model model) {
        List<Person> personList = model.getFilteredPersonList();

        for (Person person : personList) {
            Person resetPerson = createUnmarkedPerson(person);
            model.setPerson(person, resetPerson);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_UNMARK_ALL_SUCCESS);
    }

    private static Person createUnmarkedPerson(Person person) {
        assert person != null;
        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getStudentID(),
                person.getTags(), new AttendanceStatus());
    }
}
