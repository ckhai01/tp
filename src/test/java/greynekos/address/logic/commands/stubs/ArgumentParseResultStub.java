package greynekos.address.logic.commands.stubs;

import java.util.List;
import java.util.Optional;

import greynekos.address.logic.commands.CommandResult;
import greynekos.address.logic.commands.exceptions.CommandException;
import greynekos.address.logic.parser.ArgumentParseResult;
import greynekos.address.logic.parser.commandoption.Option;
import greynekos.address.logic.parser.commandoption.OptionalOption;
import greynekos.address.logic.parser.commandoption.RequiredOption;
import greynekos.address.model.Model;

/** Mock stub for a ArgumentParseResult that does nothing */
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
    public <T> List<T> getAllValues(Option<T> flag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new AssertionError("This method should not be called.");
    }

}
