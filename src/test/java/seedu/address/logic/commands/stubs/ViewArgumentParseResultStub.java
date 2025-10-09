package seedu.address.logic.commands.stubs;

import seedu.address.logic.parser.commandoption.RequiredOption;

/**
 * Stub for ViewCommand: returns a single preamble value (index or studentId).
 */
public class ViewArgumentParseResultStub extends ArgumentParseResultStub {
    private final String value;

    public ViewArgumentParseResultStub(String value) {
        this.value = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getValue(RequiredOption<T> option) {
        return (T) value;
    }
}
