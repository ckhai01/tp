package seedu.address.logic.commands.stubs;

import seedu.address.logic.parser.commandoption.RequiredOption;
import seedu.address.model.person.PersonIdentifier;

/**
 * Stub for ViewCommand: returns a single preamble value (index or studentId).
 */
public class ViewArgumentParseResultStub extends ArgumentParseResultStub {
    private final PersonIdentifier identifier;

    public ViewArgumentParseResultStub(PersonIdentifier identifier) {
        this.identifier = identifier;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getValue(RequiredOption<T> option) {
        return (T) identifier;
    }
}
