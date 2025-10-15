package greynekos.address.logic.parser.commandoption;

import greynekos.address.logic.parser.Prefix;
import greynekos.address.logic.parser.exceptions.ParseException;

/**
 * The API of the Option component.
 */
public interface Option<T> {
    Prefix getPrefix();

    T parseOptionArgument(String argument) throws ParseException;
}
