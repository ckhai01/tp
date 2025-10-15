package greynekos.address.logic.parser;

import greynekos.address.logic.parser.exceptions.ParseException;

/**
 * Functional interface for parsing arguments in commands
 */
@FunctionalInterface
public interface ArgumentParser<T> {
    T parse(String argument) throws ParseException;
}
