package greynekos.address.logic.parser;

import static greynekos.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static greynekos.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import greynekos.address.commons.core.LogsCenter;
import greynekos.address.logic.commands.Command;
import greynekos.address.logic.commands.HelpCommand;
import greynekos.address.logic.parser.exceptions.ParseException;

/**
 * Stores commands and parses user input
 */
public class GreyBookParser {
    /** A map between a command name and its {@link Command} class */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(GreyBookParser.class);

    private Map<String, CommandParser> commands = new HashMap<>();

    /**
     * Adds a new command to be parsed by the parser. If the command previously
     * existed, overrides it.
     *
     * @param name
     *            The name of the command
     * @return A {@link CommandParser} to add options for parsing
     */
    public CommandParser newCommand(String name, String messageUsage, Command command) {
        CommandParser cp = new CommandParser(messageUsage, command);
        commands.put(name, cp);
        logger.fine("Added new command: " + name);

        return cp;
    }

    /**
     * Returns the command associated with the command word
     *
     * @param word
     *            The word to activate the command
     * @return The command object associated with the word
     */
    public Command getCommand(String word) {
        return commands.get(word).getCommand();
    }

    /**
     * Parses the whole user input and checks whether it is a valid command
     *
     * @param userInput
     *            The input from the user
     * @return A {@link ArgumentParseResult} if successfully parsed, storing the
     *         objects associated with the options for this command
     * @throws A
     *             {@link ParseException} if the user input does not conform to the
     *             format defined by the command
     */
    public ArgumentParseResult parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        if (!commands.containsKey(commandWord)) {
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        return commands.get(commandWord).parse(arguments);
    }
}
