package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;

@FunctionalInterface
public interface ArgumentParser<T> {
    T parse(String argument) throws ParseException;
}
