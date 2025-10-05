package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentParseResult;
import seedu.address.logic.parser.Flag;
import seedu.address.logic.parser.Flag.FlagOption;
import seedu.address.logic.parser.GreyBookParser;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the
 * argument keywords. Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Flag<String> keywordFlag = Flag.of("INDEX", FlagOption.ONE_OR_MORE_PREAMBLES);

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, MESSAGE_USAGE, this).addFlag(keywordFlag);
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPersonList(getParseResult(arg));
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public NameContainsKeywordsPredicate getParseResult(ArgumentParseResult argResult) {
        return new NameContainsKeywordsPredicate(argResult.getAllValues(keywordFlag));
    }
}
