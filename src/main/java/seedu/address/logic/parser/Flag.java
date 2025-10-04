package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;

public class Flag<T> {
    public enum FlagOption {
        REQUIRED, OPTIONAL, ZERO_OR_MORE, ONE_OR_MORE, SINGLE_PREAMBLE, ONE_OR_MORE_PREAMBLES
    }

    private Prefix prefix;
    private FlagOption option;
    private ArgumentParser<T> parser;
    private String name;

    private Flag(Prefix prefix, String name, FlagOption option, ArgumentParser<T> parser) {
        this.prefix = prefix;
        this.option = option;
        this.parser = parser;
        this.name = name;
    }

    // For preambles
    public static Flag<String> of(String name, FlagOption option) {
        if (option != FlagOption.SINGLE_PREAMBLE && option != FlagOption.ONE_OR_MORE_PREAMBLES) {
            throw new IllegalArgumentException("FlagOption can only be SINGLE_PREAMBLE or MULTIPLE_PREAMBLES");
        }
        return new Flag<>(new Prefix(""), name, option, x -> x);
    }

    public static <S> Flag<S> of(String name, FlagOption option, ArgumentParser<S> parser) {
        if (option != FlagOption.SINGLE_PREAMBLE && option != FlagOption.ONE_OR_MORE_PREAMBLES) {
            throw new IllegalArgumentException("FlagOption can only be SINGLE_PREAMBLE or MULTIPLE_PREAMBLES");
        }
        return new Flag<>(new Prefix(""), name, option, parser);
    }

    public static Flag<String> of(Prefix prefix, String name, FlagOption option) {
        return new Flag<>(prefix, name, option, x -> x);
    }

    public static <S> Flag<S> of(Prefix prefix, String name, FlagOption option, ArgumentParser<S> parser) {
        return new Flag<>(prefix, name, option, parser);
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public FlagOption getFlagOption() {
        return option;
    }

    public T parseFlagArgument(String argument) throws ParseException {
        return parser.parse(argument);
    }

    @Override
    public int hashCode() {
        return prefix.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Flag<?>) {
            Flag<?> other = (Flag<?>) obj;
            return other.prefix.equals(prefix) && other.option.equals(option) && other.name.equals(name);
        }
        return false;
    }
}
