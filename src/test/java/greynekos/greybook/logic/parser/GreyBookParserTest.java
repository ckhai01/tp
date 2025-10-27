package greynekos.greybook.logic.parser;

import static greynekos.greybook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static greynekos.greybook.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static greynekos.greybook.testutil.Assert.assertThrows;
import static greynekos.greybook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import greynekos.greybook.logic.commands.ClearCommand;
import greynekos.greybook.logic.commands.CommandRegistry;
import greynekos.greybook.logic.commands.DeleteCommand;
import greynekos.greybook.logic.commands.EditCommand;
import greynekos.greybook.logic.commands.EditCommand.EditPersonDescriptor;
import greynekos.greybook.logic.commands.ExitCommand;
import greynekos.greybook.logic.commands.FindCommand;
import greynekos.greybook.logic.commands.HelpCommand;
import greynekos.greybook.logic.commands.ListCommand;
import greynekos.greybook.logic.parser.exceptions.ParseException;
import greynekos.greybook.model.person.NameOrStudentIdPredicate;
import greynekos.greybook.model.person.Person;
import greynekos.greybook.model.person.PersonIdentifier;
import greynekos.greybook.testutil.EditPersonDescriptorBuilder;
import greynekos.greybook.testutil.PersonBuilder;
import greynekos.greybook.testutil.PersonUtil;

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
        assertEquals(new NameOrStudentIdPredicate(keywords, java.util.Collections.emptyList()),
                argResult.getCommand().getParseResult(argResult));
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
