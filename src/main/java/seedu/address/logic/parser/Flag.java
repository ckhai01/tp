package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a command line flag to give structure to commands. This class
 * stores properties of such flags to make parsing commands simpler.
 */
public class Flag<T> {

    /** Options for the flag */
    public enum FlagOption {
        /**
         * Indicates a required flag. If this flag appears zero or more than one time
         * when parsing, a {@link ParseException} will be thrown.
         */
        REQUIRED,
        /**
         * Indicates an optional flag. If this flag appears more than one time when
         * parsing, a {@link ParseException} will be thrown.
         */
        OPTIONAL,
        /**
         * Indicates a flag that can appear zero or more times.
         */
        ZERO_OR_MORE,
        /**
         * Indicates a flag that can appear one or more times. If this flag appears zero
         * times when parsing, a {@link ParseException} will be thrown.
         */
        ONE_OR_MORE,
        /**
         * Indicates that the command takes in a preamble with no flags. During parsing,
         * the whole preamble will be parsed, including spaces
         */
        SINGLE_PREAMBLE,
        /**
         * Indicates that the command takes in space separated preambles with no flags.
         * During parsing, each space separated string in the preamble will be parsed
         * individually.
         */
        ONE_OR_MORE_PREAMBLES
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
            throw new IllegalArgumentException(
                    "Flags without prefixes can only be SINGLE_PREAMBLE or MULTIPLE_PREAMBLES");
        }
        return new Flag<>(new Prefix(""), name, option, x -> x);
    }

    public static <S> Flag<S> of(String name, FlagOption option, ArgumentParser<S> parser) {
        if (option != FlagOption.SINGLE_PREAMBLE && option != FlagOption.ONE_OR_MORE_PREAMBLES) {
            throw new IllegalArgumentException(
                    "Flags without prefixes can only be SINGLE_PREAMBLE or MULTIPLE_PREAMBLES");
        }
        return new Flag<>(new Prefix(""), name, option, parser);
    }

    public static Flag<String> of(Prefix prefix, String name, FlagOption option) {
        if (option == FlagOption.SINGLE_PREAMBLE && option == FlagOption.ONE_OR_MORE_PREAMBLES) {
            throw new IllegalArgumentException("Flags with prefixes can not be SINGLE_PREAMBLE or MULTIPLE_PREAMBLES");
        }
        return new Flag<>(prefix, name, option, x -> x);
    }

    public static <S> Flag<S> of(Prefix prefix, String name, FlagOption option, ArgumentParser<S> parser) {
        if (option == FlagOption.SINGLE_PREAMBLE && option == FlagOption.ONE_OR_MORE_PREAMBLES) {
            throw new IllegalArgumentException("Flag with prefixes can not be SINGLE_PREAMBLE or MULTIPLE_PREAMBLES");
        }
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
