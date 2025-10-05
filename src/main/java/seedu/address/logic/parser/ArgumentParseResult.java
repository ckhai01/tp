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

public class ArgumentParseResult {
    private Map<Option<?>, List<?>> optionArgumentToResult;
    private Command command;

    public ArgumentParseResult(Command command, Map<Option<?>, List<?>> map) {
        optionArgumentToResult = map;
        this.command = command;
    }

    // Cannot call with Optional or Zero or more
    public <T> T getValue(RequiredOption<T> option) {
        @SuppressWarnings("unchecked")
        List<T> values = (List<T>) optionArgumentToResult.get(option);
        return values.get(values.size() - 1);
    }

    public <T> Optional<T> getOptionalValue(OptionalOption<T> option) {
        @SuppressWarnings("unchecked")
        List<T> values = (List<T>) optionArgumentToResult.get(option);
        return values == null || values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    public <T> List<T> getAllValues(MultipleOption<T> option) {
        @SuppressWarnings("unchecked")
        List<T> values = (List<T>) optionArgumentToResult.get(option);
        return values == null || values.isEmpty() ? List.of() : values;
    }

    public CommandResult execute(Model model) throws CommandException {
        return command.execute(model, this);
    }

    public Command getCommand() {
        return command;
    }
}
