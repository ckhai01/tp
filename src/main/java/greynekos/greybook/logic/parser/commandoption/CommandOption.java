package greynekos.greybook.logic.parser.commandoption;

import greynekos.greybook.logic.parser.ArgumentParser;
import greynekos.greybook.logic.parser.Prefix;
import greynekos.greybook.logic.parser.exceptions.ParseException;

/**
 * Represents a command option to give structure to commands. This class stores
 * properties of such options to make parsing commands simpler.
 */
public abstract class CommandOption<T> implements Option<T> {

    /** The parser associated with this command */
    protected ArgumentParser<T> parser;
    private Prefix prefix;
    private String name;

    protected CommandOption(Prefix prefix, String name, ArgumentParser<T> parser) {
        this.prefix = prefix;
        this.parser = parser;
        this.name = name;
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public T parseOptionArgument(String argument) throws ParseException {
        return parser.parse(argument);
    }

    @Override
    public int hashCode() {
        return prefix.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CommandOption<?>) {
            CommandOption<?> other = (CommandOption<?>) obj;
            return other.prefix.equals(prefix) && other.name.equals(name);
        }
        return false;
    }
}
