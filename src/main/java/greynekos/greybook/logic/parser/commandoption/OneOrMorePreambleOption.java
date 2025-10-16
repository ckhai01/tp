package greynekos.greybook.logic.parser.commandoption;

import greynekos.greybook.logic.parser.ArgumentParser;

/**
 * Represents a preamble command option occurs before any prefixes. During
 * parsing, each space separated string in the preamble is parsed separately.
 */
public class OneOrMorePreambleOption<T> extends PreambleOption<T> implements RequiredOption<T>, MultipleOption<T> {
    private OneOrMorePreambleOption(String name, ArgumentParser<T> parser) {
        super(name, parser);
    }

    public static OneOrMorePreambleOption<String> of(String name) {
        return new OneOrMorePreambleOption<>(name, x -> x);
    }

    public static <S> OneOrMorePreambleOption<S> of(String name, ArgumentParser<S> parser) {
        return new OneOrMorePreambleOption<>(name, parser);
    }
}
