package greynekos.greybook.logic.parser.commandoption;

import greynekos.greybook.logic.parser.ArgumentParser;
import greynekos.greybook.logic.parser.Prefix;

/**
 * Represents a prefix option that can occur more than once, but must appear at
 * least once. If this option appears zero times when parsing, a
 * {@link ParseException} will be thrown.
 */
public class OneOrMorePrefixOption<T> extends PrefixOption<T> implements RequiredOption<T>, MultipleOption<T> {
    private OneOrMorePrefixOption(Prefix prefix, String name, ArgumentParser<T> parser) {
        super(prefix, name, parser);
    }

    public static OneOrMorePrefixOption<String> of(Prefix prefix, String name) {
        return new OneOrMorePrefixOption<>(prefix, name, x -> x);
    }

    public static <S> OneOrMorePrefixOption<S> of(Prefix prefix, String name, ArgumentParser<S> parser) {
        return new OneOrMorePrefixOption<>(prefix, name, parser);
    }
}
