package greynekos.greybook.logic.parser.commandoption;

import greynekos.greybook.logic.parser.ArgumentParser;

/**
 * Represents a single optional preamble command option occurs before any
 * prefixes. During parsing, each space separated string in the preamble is
 * parsed separately.
 */
public class OptionalSinglePreambleOption<T> extends PreambleOption<T> implements OptionalOption<T> {
    private OptionalSinglePreambleOption(String name, ArgumentParser<T> parser) {
        super(name, parser);
    }

    public static OptionalSinglePreambleOption<String> of(String name) {
        return new OptionalSinglePreambleOption<>(name, x -> x);
    }

    public static <S> OptionalSinglePreambleOption<S> of(String name, ArgumentParser<S> parser) {
        return new OptionalSinglePreambleOption<>(name, parser);
    }
}
