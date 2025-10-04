package seedu.address.logic.commands;

import seedu.address.logic.parser.GreyBookParser;

public class CommandRegistry {
    private static final Command[] commands = {new AddCommand(), new ClearCommand(), new DeleteCommand(),
        new EditCommand(), new ExitCommand(), new FindCommand(), new HelpCommand(), new ListCommand()};

    public static void addCommandsToParser(GreyBookParser parser) {
        for (Command command : commands) {
            command.addToParser(parser);
        }
    }

}
