package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ABSENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXCUSED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRESENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentParseResult;
import seedu.address.logic.parser.GreyBookParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.commandoption.OptionalPrefixOption;
import seedu.address.logic.parser.commandoption.SinglePreambleOption;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentID;
import seedu.address.model.tag.Tag;

/**
 * The MarkCommand marks a club member's attendance (e.g. as Present, Absent,
 * Late, Excused).
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a club member's attendance.\n"
            + "Parameters: INDEX (must be a positive integer) " + "or " + PREFIX_STUDENTID + "STUDENT_ID\n" + "Flags: "
            + PREFIX_PRESENT + " for Present, " + PREFIX_ABSENT + " for Absent, " + PREFIX_LATE + " for Late, "
            + PREFIX_EXCUSED + " for Excused\n" + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PRESENT + "\n"
            + "         " + COMMAND_WORD + " " + PREFIX_STUDENTID + "A0123456X " + PREFIX_ABSENT;

    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Marked %1$s's Attendance: %2$s\n%3$s";
    public static final String MESSAGE_MISSING_ATTENDANCE_FLAG = "You must provide one of -p, -a, -l, -e.";

    private final SinglePreambleOption<Index> indexOption = SinglePreambleOption.of("INDEX", ParserUtil::parseIndex);
    private final OptionalPrefixOption<StudentID> studentIdOption =
            OptionalPrefixOption.of(PREFIX_STUDENTID, "STUDENT_ID", ParserUtil::parseStudentID);

    private final OptionalPrefixOption<String> presentOption =
            OptionalPrefixOption.of(PREFIX_PRESENT, "ATTENDANCE_PRESENT");
    private final OptionalPrefixOption<String> absentOption =
            OptionalPrefixOption.of(PREFIX_ABSENT, "ATTENDANCE_ABSENT");
    private final OptionalPrefixOption<String> lateOption = OptionalPrefixOption.of(PREFIX_LATE, "ATTENDANCE_LATE");
    private final OptionalPrefixOption<String> excusedOption =
            OptionalPrefixOption.of(PREFIX_EXCUSED, "ATTENDANCE_EXCUSED");

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, MESSAGE_USAGE, this).addOptions(indexOption, studentIdOption, presentOption,
                absentOption, lateOption, excusedOption);
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException {
        requireNonNull(model);

        String attendanceStatus = getAttendanceStatus(arg);
        if (attendanceStatus == null) {
            throw new CommandException(MESSAGE_MISSING_ATTENDANCE_FLAG);
        }

        Person personToMark;
        if (arg.getOptionalValue(studentIdOption).isPresent()) {
            // mark by StudentID
            StudentID studentId = arg.getOptionalValue(studentIdOption).get();
            personToMark = getPersonByStudentId(model, studentId);
        } else {
            // mark by index
            Index index = arg.getValue(indexOption);
            personToMark = getPersonByIndex(model, index);
        }

        // Create new tag with attendance + current date
        Tag attendanceTag = createAttendanceTag(attendanceStatus);

        // Merge with existing tags
        Set<Tag> updatedTags = new HashSet<>(personToMark.getTags());
        updatedTags.add(attendanceTag);

        // Create a new person with updated tags
        Person markedPerson = createEditedPersonWithTags(personToMark, updatedTags);
        model.setPerson(personToMark, markedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_MARK_PERSON_SUCCESS, markedPerson.getName(), attendanceStatus,
                Messages.format(markedPerson)));
    }

    private String getAttendanceStatus(ArgumentParseResult arg) {
        if (arg.getOptionalValue(presentOption).isPresent()) {
            return "Present";
        }
        if (arg.getOptionalValue(absentOption).isPresent()) {
            return "Absent";
        }
        if (arg.getOptionalValue(lateOption).isPresent()) {
            return "Late";
        }
        if (arg.getOptionalValue(excusedOption).isPresent()) {
            return "Excused";
        }
        return null;
    }

    private Person getPersonByIndex(Model model, Index index) throws CommandException {
        List<Person> list = model.getFilteredPersonList();
        if (index.getZeroBased() >= list.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return list.get(index.getZeroBased());
    }

    private Person getPersonByStudentId(Model model, StudentID sid) throws CommandException {
        return model.getPersonByStudentId(sid)
                .orElseThrow(() -> new CommandException("No student found with ID: " + sid));
    }

    private Tag createAttendanceTag(String status) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        return new Tag(formattedDate + "-" + status);
    }

    private static Person createEditedPersonWithTags(Person personToEdit, Set<Tag> updatedTags) {
        assert personToEdit != null;
        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), updatedTags, personToEdit.getStudentID());
    }
}
