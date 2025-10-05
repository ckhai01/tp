package seedu.address.logic.parser.commandoption;

import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

public interface Option<T> {
    Prefix getPrefix();

    T parseOptionArgument(String argument) throws ParseException;
}
