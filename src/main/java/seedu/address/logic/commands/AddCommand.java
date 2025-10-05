package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentParseResult;
import seedu.address.logic.parser.Flag;
import seedu.address.logic.parser.Flag.FlagOption;
import seedu.address.logic.parser.GreyBookParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. " + "Parameters: "
            + PREFIX_NAME + "NAME " + PREFIX_PHONE + "PHONE " + PREFIX_EMAIL + "EMAIL " + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n" + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 " + PREFIX_EMAIL + "johnd@example.com " + PREFIX_ADDRESS
            + "311, Clementi Ave 2, #02-25 " + PREFIX_TAG + "friends " + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Flag<Name> nameFlag = Flag.of(PREFIX_NAME, "NAME", FlagOption.REQUIRED, ParserUtil::parseName);
    private final Flag<Phone> phoneFlag = Flag.of(PREFIX_PHONE, "PHONE", FlagOption.REQUIRED, ParserUtil::parsePhone);
    private final Flag<Email> emailFlag = Flag.of(PREFIX_EMAIL, "EMAIL", FlagOption.REQUIRED, ParserUtil::parseEmail);
    private final Flag<Address> addressFlag =
            Flag.of(PREFIX_ADDRESS, "ADDRESS", FlagOption.REQUIRED, ParserUtil::parseAddress);
    private final Flag<Tag> tagFlag = Flag.of(PREFIX_TAG, "TAG", FlagOption.ZERO_OR_MORE, ParserUtil::parseTag);

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, MESSAGE_USAGE, this).addFlags(nameFlag, phoneFlag, emailFlag, addressFlag,
                tagFlag);
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException {
        requireNonNull(model);

        Person toAdd = getParseResult(arg);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public Person getParseResult(ArgumentParseResult argResult) {
        return new Person(argResult.getValue(nameFlag), argResult.getValue(phoneFlag), argResult.getValue(emailFlag),
                argResult.getValue(addressFlag), Set.copyOf(argResult.getAllValues(tagFlag)));
    }
}
