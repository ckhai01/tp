package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.ArgumentParseResult;
import seedu.address.logic.parser.GreyBookParser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, "Clears the list of members", this);
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
