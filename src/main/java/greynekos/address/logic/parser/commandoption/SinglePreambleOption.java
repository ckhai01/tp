package greynekos.address.logic.parser.commandoption;

import greynekos.address.logic.parser.ArgumentParser;

/**
 * Represents a preamble command option occurs before any prefixes. During
 * parsing, the whole preamble will be parsed, including spaces.
 */
public class SinglePreambleOption<T> extends PreambleOption<T> implements RequiredOption<T> {
    private SinglePreambleOption(String name, ArgumentParser<T> parser) {
        super(name, parser);
    }

    public static SinglePreambleOption<String> of(String name) {
        return new SinglePreambleOption<>(name, x -> x);
    }

    public static <S> SinglePreambleOption<S> of(String name, ArgumentParser<S> parser) {
        return new SinglePreambleOption<>(name, parser);
    }
}
