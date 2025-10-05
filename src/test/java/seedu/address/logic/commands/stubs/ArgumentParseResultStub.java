package seedu.address.logic.commands.stubs;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentParseResult;
import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;

public class ArgumentParseResultStub extends ArgumentParseResult {
    public ArgumentParseResultStub() {
        super(null, null);
    }

    @Override
    public <T> T getValue(Flag<T> flag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public <T> Optional<T> getOptionalValue(Flag<T> flag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public <T> List<T> getAllValues(Flag<T> flag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new AssertionError("This method should not be called.");
    }

}
