package seedu.address.logic.parser;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

// We know that all required and one_or_more flags exist in the ArgumentMultimap
// We also know that there are no duplicates for required
public class ArgumentParseResult {
    private Map<Flag<?>, List<?>> flagArgumentToResult;
    private Command command;

    public ArgumentParseResult(Command command, Map<Flag<?>, List<?>> map) {
        flagArgumentToResult = map;
        this.command = command;
    }

    // Cannot call with Optional or Zero or more
    public <T> T getValue(Flag<T> flag) {
        @SuppressWarnings("unchecked")
        List<T> values = (List<T>) flagArgumentToResult.get(flag);
        return values.get(values.size() - 1);
    }

    public <T> Optional<T> getOptionalValue(Flag<T> flag) {
        @SuppressWarnings("unchecked")
        List<T> values = (List<T>) flagArgumentToResult.get(flag);
        return values == null || values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    public <T> List<T> getAllValues(Flag<T> flag) {
        @SuppressWarnings("unchecked")
        List<T> values = (List<T>) flagArgumentToResult.get(flag);
        return values == null || values.isEmpty() ? List.of() : values;
    }

    public CommandResult execute(Model model) throws CommandException {
        return command.execute(model, this);
    }

    public Command getCommand() {
        return command;
    }
}
