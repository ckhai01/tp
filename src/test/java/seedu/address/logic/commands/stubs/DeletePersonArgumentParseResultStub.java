package seedu.address.logic.commands.stubs;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.Flag;

public class DeletePersonArgumentParseResultStub extends ArgumentParseResultStub {
    private Index index;

    public DeletePersonArgumentParseResultStub(Index index) {
        super();
        this.index = index;
    }

    @Override
    // This stub should only be called with a flag to get the index
    public <T> T getValue(Flag<T> flag) {
        @SuppressWarnings("unchecked")
        T ret = (T) index;
        return ret;
    }
}
