package greynekos.greybook.logic.parser;

import greynekos.greybook.logic.parser.exceptions.ParseException;

/**
 * Functional interface for parsing arguments in commands
 */
@FunctionalInterface
public interface ArgumentParser<T> {
    T parse(String argument) throws ParseException;
}
