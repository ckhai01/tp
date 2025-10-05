package seedu.address.logic.parser;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.commandoption.MultipleOption;
import seedu.address.logic.parser.commandoption.Option;
import seedu.address.logic.parser.commandoption.OptionalOption;
import seedu.address.logic.parser.commandoption.RequiredOption;
import seedu.address.model.Model;

/**
 * Stores a map between {@link Option} and a list representing the objects
 * associated to the option. Provides an interface to access this map.
 */
public class ArgumentParseResult {
    private Map<Option<?>, List<?>> optionArgumentToResult;
    private Command command;

    /**
     * Stores the map and command provided
     */
    public ArgumentParseResult(Command command, Map<Option<?>, List<?>> map) {
        optionArgumentToResult = map;
        this.command = command;
    }

    /**
     * Gets the value associated to the option. This value must exist otherwise the
     * input would not have been parsed.
     *
     * @param option
     *            A {@link RequiredOption}
     * @return The parsed object associated with the option. If the option is a
     *         {@link MultipleOption}, returns the last value.
     */
    public <T> T getValue(RequiredOption<T> option) {
        @SuppressWarnings("unchecked")
        List<T> values = (List<T>) optionArgumentToResult.get(option);
        return values.get(values.size() - 1);
    }

    /**
     * Gets the value associated to the optional option. This value may or may not
     * exist
     *
     * @param option
     *            A {@link OptionalOption}
     * @return An {@link Optional} that contains the parsed object if it exists. If
     *         the option is a {@link MultipleOption}, the Optional contains the
     *         last value if it exists.
     */
    public <T> Optional<T> getOptionalValue(OptionalOption<T> option) {
        @SuppressWarnings("unchecked")
        List<T> values = (List<T>) optionArgumentToResult.get(option);
        return values == null || values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Gets the list of values associated to the option.
     *
     * @param option
     *            Any {@link Option}
     * @return the list of values associated with the option, if the option does not
     *         belong to this command, returns the empty list.
     */
    public <T> List<T> getAllValues(Option<T> option) {
        @SuppressWarnings("unchecked")
        List<T> values = (List<T>) optionArgumentToResult.get(option);
        return values == null ? List.of() : values;
    }

    public CommandResult execute(Model model) throws CommandException {
        return command.execute(model, this);
    }

    public Command getCommand() {
        return command;
    }
}
