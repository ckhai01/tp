package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Flag.FlagOption;
import seedu.address.logic.parser.exceptions.ParseException;

public class CommandParser {
    private Map<Prefix, Flag<?>> flags = new HashMap<>();
    private String messageUsage;
    private Command command;

    CommandParser(String messageUsage, Command command) {
        this.messageUsage = messageUsage;
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public CommandParser addFlags(Flag<?>... flags) {
        for (Flag<?> flag : flags) {
            this.flags.put(flag.getPrefix(), flag);
        }
        return this;
    }

    public CommandParser addFlag(Flag<?> flag) {
        flags.put(flag.getPrefix(), flag);
        return this;
    }

    public ArgumentParseResult parse(String arguments) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, getPrefixes());

        if (!arePrefixesPresent(argMultimap, filterPrefixByOption(FlagOption.REQUIRED, FlagOption.ONE_OR_MORE,
                FlagOption.SINGLE_PREAMBLE, FlagOption.ONE_OR_MORE_PREAMBLES))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(filterPrefixByOption(FlagOption.REQUIRED, FlagOption.OPTIONAL));

        // Parse the arguments here, so that any ParseExceptions are thrown
        // Each Flag and List pair must share the same generic type
        Map<Flag<?>, List<?>> flagArgumentToResult = new HashMap<>();
        for (Flag<?> flag : flags.values()) {
            List<Object> result = new ArrayList<>();
            if (flag.getFlagOption() == FlagOption.ONE_OR_MORE_PREAMBLES) {
                for (String preamble : argMultimap.getAllValues(new Prefix(""))) {
                    if (!preamble.isEmpty()) {
                        result.add(flag.parseFlagArgument(preamble));
                    }
                }
            } else if (flag.getFlagOption() == FlagOption.SINGLE_PREAMBLE) {
                List<String> nonEmptyPreambles =
                        argMultimap.getAllValues(new Prefix("")).stream().filter(x -> !x.isEmpty()).toList();
                if (nonEmptyPreambles.size() != 1) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
                } else {
                    result.add(flag.parseFlagArgument(nonEmptyPreambles.get(0)));
                }
            } else {
                for (String arg : argMultimap.getAllValues(flag.getPrefix())) {
                    result.add(flag.parseFlagArgument(arg));
                }
            }
            flagArgumentToResult.put(flag, result);
        }

        return new ArgumentParseResult(command, flagArgumentToResult);
    }

    private Prefix[] getPrefixes() {
        return flags.keySet().toArray(new Prefix[0]);
    }

    private Prefix[] filterPrefixByOption(FlagOption... options) {
        return flags.values().stream().filter(flag -> List.of(options).contains(flag.getFlagOption()))
                .map(Flag::getPrefix).toList().toArray(new Prefix[0]);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
