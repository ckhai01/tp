package seedu.address.logic.commands.stubs;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.commandoption.RequiredOption;

/**
 * Mock stub for ArgumentParseResult to test DeleteCommand. Only stores the
 * index of the person to be deleted
 */
public class DeletePersonArgumentParseResultStub extends ArgumentParseResultStub {
    private Index index;

    /** @param index The index of the person to be deleted */
    public DeletePersonArgumentParseResultStub(Index index) {
        super();
        this.index = index;
    }

    // This stub should only be called with a flag to get the index
    @Override
    public <T> T getValue(RequiredOption<T> flag) {
        @SuppressWarnings("unchecked")
        T ret = (T) index;
        return ret;
    }
}
