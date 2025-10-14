package greynekos.address.logic.parser.commandoption;

import greynekos.address.logic.parser.ArgumentParser;
import greynekos.address.logic.parser.Prefix;

/**
 * Represents an optional prefix option that can occur more than once.
 */
public class ZeroOrMorePrefixOption<T> extends PrefixOption<T> implements OptionalOption<T>, MultipleOption<T> {
    private ZeroOrMorePrefixOption(Prefix prefix, String name, ArgumentParser<T> parser) {
        super(prefix, name, parser);
    }

    public static ZeroOrMorePrefixOption<String> of(Prefix prefix, String name) {
        return new ZeroOrMorePrefixOption<>(prefix, name, x -> x);
    }

    public static <S> ZeroOrMorePrefixOption<S> of(Prefix prefix, String name, ArgumentParser<S> parser) {
        return new ZeroOrMorePrefixOption<>(prefix, name, parser);
    }
}
