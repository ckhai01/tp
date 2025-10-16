package seedu.address.logic.commands;

import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentParseResult;
import seedu.address.logic.parser.GreyBookParser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentID;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * MarkCommand.
 */
public class MarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_markByIndex_success() throws Exception {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MarkCommand markCommand = new MarkCommand();

        // Build parser and arguments for command
        GreyBookParser parser = new GreyBookParser();
        markCommand.addToParser(parser);

        String userInput = "mark " + INDEX_FIRST_PERSON.getOneBased() + " -p";
        ArgumentParseResult arg = parser.parse(userInput);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        Tag expectedTag = new Tag(today + "-Present");
        Set<Tag> mergedTags = mergeTags(targetPerson.getTags(), expectedTag);
        String[] mergedTagStrings = mergedTags.stream().map(tag -> tag.tagName).toArray(String[]::new);
        Person markedPerson = new PersonBuilder(targetPerson).withTags(mergedTagStrings).build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, markedPerson);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_PERSON_SUCCESS,
                markedPerson.getName(), "Present", Messages.format(markedPerson));

        assertCommandSuccess(markCommand, model, arg, expectedMessage, expectedModel);
    }

    @Test
    public void execute_markByStudentId_success() throws Exception {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentID sid = targetPerson.getStudentID();
        MarkCommand markCommand = new MarkCommand();

        GreyBookParser parser = new GreyBookParser();
        markCommand.addToParser(parser);

        String userInput = "mark i/" + sid.value + " -l";
        ArgumentParseResult arg = parser.parse(userInput);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Tag expectedTag = new Tag(today + "-Late");

        Set<Tag> mergedTags = mergeTags(targetPerson.getTags(), expectedTag);
        String[] mergedTagStrings = mergedTags.stream().map(tag -> tag.tagName).toArray(String[]::new);
        
        Person markedPerson = new PersonBuilder(targetPerson).withTags(mergedTagStrings).build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, markedPerson);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_PERSON_SUCCESS,
                markedPerson.getName(), "Late", Messages.format(markedPerson));

        assertCommandSuccess(markCommand, model, arg, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        MarkCommand markCommand = new MarkCommand();
        GreyBookParser parser = new GreyBookParser();
        markCommand.addToParser(parser);

        String userInput = "mark 999 -p";
        ArgumentParseResult arg = parser.parse(userInput);

        assertCommandFailure(markCommand, model, arg,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentId_throwsCommandException() throws Exception {
        MarkCommand markCommand = new MarkCommand();
        GreyBookParser parser = new GreyBookParser();
        markCommand.addToParser(parser);

        String userInput = "mark i/A0000000X -a";
        ArgumentParseResult arg = parser.parse(userInput);

        assertCommandFailure(markCommand, model, arg,
                "No student found with ID: A0000000X");
    }

    @Test
    public void execute_missingAttendanceFlag_throwsCommandException() throws Exception {
        MarkCommand markCommand = new MarkCommand();
        GreyBookParser parser = new GreyBookParser();
        markCommand.addToParser(parser);

        String userInput = "mark " + INDEX_FIRST_PERSON.getOneBased();
        ArgumentParseResult arg = parser.parse(userInput);

        assertCommandFailure(markCommand, model, arg,
                MarkCommand.MESSAGE_MISSING_ATTENDANCE_FLAG);
    }

    // helper method to merge tag sets
    private static Set<Tag> mergeTags(Set<Tag> original, Tag newTag) {
        Set<Tag> updated = new HashSet<>(original);
        updated.add(newTag);
        return updated;
    }
}
