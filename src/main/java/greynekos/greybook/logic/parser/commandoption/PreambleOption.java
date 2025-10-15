package greynekos.greybook.logic.parser.commandoption;

import static greynekos.greybook.logic.parser.CliSyntax.PREFIX_EMPTY;

import greynekos.greybook.logic.parser.ArgumentParser;

/**
 * Represents a command option that exists in the preamble. Behaves like a
 * {@link PrefixOption} with an empty prefix.
 */
public abstract class PreambleOption<T> extends CommandOption<T> {
    protected PreambleOption(String name, ArgumentParser<T> parser) {
        super(PREFIX_EMPTY, name, parser);
    }
}
