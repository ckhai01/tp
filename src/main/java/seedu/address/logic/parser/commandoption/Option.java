package seedu.address.logic.parser.commandoption;

import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The API of the Option component.
 */
public interface Option<T> {
    Prefix getPrefix();

    T parseOptionArgument(String argument) throws ParseException;
}
