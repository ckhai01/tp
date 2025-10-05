package seedu.address.logic.parser.commandoption;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPTY;

import seedu.address.logic.parser.ArgumentParser;

/**
 * Represents a command option that exists in the preamble. Behaves like a
 * {@link PrefixOption} with an empty prefix.
 */
public abstract class PreambleOption<T> extends CommandOption<T> {
    protected PreambleOption(String name, ArgumentParser<T> parser) {
        super(PREFIX_EMPTY, name, parser);
    }
}
