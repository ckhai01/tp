package greynekos.address.logic.parser.commandoption;

import greynekos.address.logic.parser.ArgumentParser;
import greynekos.address.logic.parser.Prefix;

/**
 * Represents a command option with a prefix that associates arguments to the
 * command with this option.
 */
public abstract class PrefixOption<T> extends CommandOption<T> {
    protected PrefixOption(Prefix prefix, String name, ArgumentParser<T> parser) {
        super(prefix, name, parser);
    }
}
