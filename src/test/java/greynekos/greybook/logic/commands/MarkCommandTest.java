package greynekos.greybook.logic.commands;

import static greynekos.greybook.logic.commands.CommandTestUtil.assertCommandFailure;
import static greynekos.greybook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static greynekos.greybook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static greynekos.greybook.testutil.TypicalPersons.getTypicalGreyBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import greynekos.greybook.logic.Messages;
import greynekos.greybook.logic.parser.ArgumentParseResult;
import greynekos.greybook.logic.parser.GreyBookParser;
import greynekos.greybook.model.GreyBook;
import greynekos.greybook.model.Model;
import greynekos.greybook.model.ModelManager;
import greynekos.greybook.model.UserPrefs;
import greynekos.greybook.model.person.Person;
import greynekos.greybook.model.person.StudentID;
import greynekos.greybook.model.tag.Tag;
import greynekos.greybook.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * MarkCommand.
 */
public class MarkCommandTest {

    private Model model = new ModelManager(getTypicalGreyBook(), new UserPrefs());

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

        Model expectedModel = new ModelManager(new GreyBook(model.getGreyBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, markedPerson);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_PERSON_SUCCESS, markedPerson.getName(),
                "Present", Messages.format(markedPerson));

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

        Model expectedModel = new ModelManager(new GreyBook(model.getGreyBook()), new UserPrefs());
        expectedModel.setPerson(targetPerson, markedPerson);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_PERSON_SUCCESS, markedPerson.getName(), "Late",
                Messages.format(markedPerson));

        assertCommandSuccess(markCommand, model, arg, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        MarkCommand markCommand = new MarkCommand();
        GreyBookParser parser = new GreyBookParser();
        markCommand.addToParser(parser);

        String userInput = "mark 999 -p";
        ArgumentParseResult arg = parser.parse(userInput);

        assertCommandFailure(markCommand, model, arg, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentId_throwsCommandException() throws Exception {
        MarkCommand markCommand = new MarkCommand();
        GreyBookParser parser = new GreyBookParser();
        markCommand.addToParser(parser);

        String userInput = "mark i/A0000000X -a";
        ArgumentParseResult arg = parser.parse(userInput);

        assertCommandFailure(markCommand, model, arg, "No student found with ID: A0000000X");
    }

    @Test
    public void execute_missingAttendanceFlag_throwsCommandException() throws Exception {
        MarkCommand markCommand = new MarkCommand();
        GreyBookParser parser = new GreyBookParser();
        markCommand.addToParser(parser);

        String userInput = "mark " + INDEX_FIRST_PERSON.getOneBased();
        ArgumentParseResult arg = parser.parse(userInput);

        assertCommandFailure(markCommand, model, arg, MarkCommand.MESSAGE_MISSING_ATTENDANCE_FLAG);
    }

    // helper method to merge tag sets
    private static Set<Tag> mergeTags(Set<Tag> original, Tag newTag) {
        Set<Tag> updated = new HashSet<>(original);
        updated.add(newTag);
        return updated;
    }
}
