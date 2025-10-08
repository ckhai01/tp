package seedu.address.logic.commands;

import seedu.address.logic.parser.GreyBookParser;

/** Represents all the commands in this app */
public class CommandRegistry {
    private static final Command[] commands = {new AddCommand(), new ClearCommand(), new DeleteCommand(),
        new EditCommand(), new ExitCommand(), new FindCommand(), new HelpCommand(), new ListCommand(),
            new ViewCommand()};

    /** Adds all the commands defined in the registry to the parser */
    public static void addCommandsToParser(GreyBookParser parser) {
        for (Command command : commands) {
            command.addToParser(parser);
        }
    }

}
