package greynekos.greybook.logic.commands.stubs;

import java.util.List;
import java.util.Optional;

import greynekos.greybook.logic.commands.CommandResult;
import greynekos.greybook.logic.commands.exceptions.CommandException;
import greynekos.greybook.logic.parser.ArgumentParseResult;
import greynekos.greybook.logic.parser.commandoption.Option;
import greynekos.greybook.logic.parser.commandoption.OptionalOption;
import greynekos.greybook.logic.parser.commandoption.RequiredOption;
import greynekos.greybook.model.Model;

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
