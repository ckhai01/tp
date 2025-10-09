package seedu.address.logic.commands.stubs;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.commandoption.RequiredOption;
import seedu.address.model.person.StudentID;

/**
 * Mock stub for ArgumentParseResult to test DeleteCommand. Stores the
 * identifier string (either index or student ID) of the person to be deleted.
 */
public class DeletePersonArgumentParseResultStub extends ArgumentParseResultStub {
    private String identifier;

    /**
     * @param index
     *            The index of the person to be deleted
     */
    public DeletePersonArgumentParseResultStub(Index index) {
        super();
        this.identifier = String.valueOf(index.getOneBased());
    }

    /**
     * @param studentID
     *            The student ID of the person to be deleted
     */
    public DeletePersonArgumentParseResultStub(StudentID studentID) {
        super();
        this.identifier = studentID.value;
    }

    // This stub should only be called with a flag to get the identifier
    @Override
    public <T> T getValue(RequiredOption<T> flag) {
        @SuppressWarnings("unchecked")
        T ret = (T) identifier;
        return ret;
    }
}
