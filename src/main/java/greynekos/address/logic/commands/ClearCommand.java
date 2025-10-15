package greynekos.address.logic.commands;

import static java.util.Objects.requireNonNull;

import greynekos.address.logic.parser.ArgumentParseResult;
import greynekos.address.logic.parser.GreyBookParser;
import greynekos.address.model.GreyBook;
import greynekos.address.model.Model;

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
        model.setGreyBook(new GreyBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
