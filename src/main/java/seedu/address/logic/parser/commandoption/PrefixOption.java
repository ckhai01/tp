package seedu.address.logic.parser.commandoption;

import seedu.address.logic.parser.ArgumentParser;
import seedu.address.logic.parser.Prefix;

/**
 * Represents a command option with a prefix that associates arguments to the
 * command with this option.
 */
public abstract class PrefixOption<T> extends CommandOption<T> {
    protected PrefixOption(Prefix prefix, String name, ArgumentParser<T> parser) {
        super(prefix, name, parser);
    }
}
