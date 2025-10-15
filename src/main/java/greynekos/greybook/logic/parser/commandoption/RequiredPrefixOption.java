package greynekos.greybook.logic.parser.commandoption;

import greynekos.greybook.logic.parser.ArgumentParser;
import greynekos.greybook.logic.parser.Prefix;

/**
 * Represents a required prefix option. If this option appears zero or more than
 * one time when parsing, a {@link ParseException} will be thrown.
 */
public class RequiredPrefixOption<T> extends PrefixOption<T> implements RequiredOption<T>, NoDuplicateOption<T> {

    private RequiredPrefixOption(Prefix prefix, String name, ArgumentParser<T> parser) {
        super(prefix, name, parser);
    }

    public static RequiredPrefixOption<String> of(Prefix prefix, String name) {
        return new RequiredPrefixOption<>(prefix, name, x -> x);
    }

    public static <S> RequiredPrefixOption<S> of(Prefix prefix, String name, ArgumentParser<S> parser) {
        return new RequiredPrefixOption<>(prefix, name, parser);
    }
}
