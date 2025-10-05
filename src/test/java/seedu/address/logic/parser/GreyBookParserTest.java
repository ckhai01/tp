package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandRegistry;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class GreyBookParserTest {

    private static final GreyBookParser PARSER = new GreyBookParser();

    @BeforeAll
    public static void initialiseCommands() {
        CommandRegistry.addCommandsToParser(PARSER);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();

        ArgumentParseResult argResult = PARSER.parse(PersonUtil.getAddCommand(person));
        assertEquals(person, argResult.getCommand().getParseResult(argResult));
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(PARSER.parse(ClearCommand.COMMAND_WORD).getCommand() instanceof ClearCommand);
        assertTrue(PARSER.parse(ClearCommand.COMMAND_WORD + " 3").getCommand() instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        ArgumentParseResult argResult = PARSER
                .parse(DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(INDEX_FIRST_PERSON, argResult.getCommand().getParseResult(argResult));
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        ArgumentParseResult argResult = PARSER.parse(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(descriptor, argResult.getCommand().getParseResult(argResult));
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(PARSER.parse(ExitCommand.COMMAND_WORD).getCommand() instanceof ExitCommand);
        assertTrue(PARSER.parse(ExitCommand.COMMAND_WORD + " 3").getCommand() instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        ArgumentParseResult argResult = PARSER
                .parse(FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new NameContainsKeywordsPredicate(keywords), argResult.getCommand().getParseResult(argResult));
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(PARSER.parse(HelpCommand.COMMAND_WORD).getCommand() instanceof HelpCommand);
        assertTrue(PARSER.parse(HelpCommand.COMMAND_WORD + " 3").getCommand() instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(PARSER.parse(ListCommand.COMMAND_WORD).getCommand() instanceof ListCommand);
        assertTrue(PARSER.parse(ListCommand.COMMAND_WORD + " 3").getCommand() instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE),
                () -> PARSER.parse(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> PARSER.parse("unknownCommand"));
    }
}
