package seedu.address.logic.commands.stubs;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentParseResult;
import seedu.address.logic.parser.commandoption.MultipleOption;
import seedu.address.logic.parser.commandoption.OptionalOption;
import seedu.address.logic.parser.commandoption.RequiredOption;
import seedu.address.model.Model;

public class ArgumentParseResultStub extends ArgumentParseResult {
    public ArgumentParseResultStub() {
        super(null, null);
    }

    @Override
    public <T> T getValue(RequiredOption<T> flag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public <T> Optional<T> getOptionalValue(OptionalOption<T> flag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public <T> List<T> getAllValues(MultipleOption<T> flag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new AssertionError("This method should not be called.");
    }

}
