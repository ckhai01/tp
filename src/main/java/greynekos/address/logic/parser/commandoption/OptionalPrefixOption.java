package greynekos.address.logic.parser.commandoption;

import greynekos.address.logic.parser.ArgumentParser;
import greynekos.address.logic.parser.Prefix;

/**
 * Represents an optional prefix option. If this option appears more than one
 * time when parsing, a {@link ParseException} will be thrown.
 */
public class OptionalPrefixOption<T> extends PrefixOption<T> implements OptionalOption<T>, NoDuplicateOption<T> {
    private OptionalPrefixOption(Prefix prefix, String name, ArgumentParser<T> parser) {
        super(prefix, name, parser);
    }

    public static OptionalPrefixOption<String> of(Prefix prefix, String name) {
        return new OptionalPrefixOption<>(prefix, name, x -> x);
    }

    public static <S> OptionalPrefixOption<S> of(Prefix prefix, String name, ArgumentParser<S> parser) {
        return new OptionalPrefixOption<>(prefix, name, parser);
    }
}
