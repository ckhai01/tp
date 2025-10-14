package greynekos.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static greynekos.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static greynekos.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static greynekos.address.testutil.Assert.assertThrows;
import static greynekos.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import greynekos.address.logic.commands.ClearCommand;
import greynekos.address.logic.commands.CommandRegistry;
import greynekos.address.logic.commands.DeleteCommand;
import greynekos.address.logic.commands.EditCommand;
import greynekos.address.logic.commands.EditCommand.EditPersonDescriptor;
import greynekos.address.logic.commands.ExitCommand;
import greynekos.address.logic.commands.FindCommand;
import greynekos.address.logic.commands.HelpCommand;
import greynekos.address.logic.commands.ListCommand;
import greynekos.address.logic.parser.exceptions.ParseException;
import greynekos.address.model.person.NameContainsKeywordsPredicate;
import greynekos.address.model.person.Person;
import greynekos.address.model.person.PersonIdentifier;
import greynekos.address.testutil.EditPersonDescriptorBuilder;
import greynekos.address.testutil.PersonBuilder;
import greynekos.address.testutil.PersonUtil;

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
        ArgumentParseResult argResult =
                PARSER.parse(DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        PersonIdentifier result = (PersonIdentifier) argResult.getCommand().getParseResult(argResult);
        assertEquals(INDEX_FIRST_PERSON, result);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        ArgumentParseResult argResult = PARSER.parse(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
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
        ArgumentParseResult argResult =
                PARSER.parse(FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
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
