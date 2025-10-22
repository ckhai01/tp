package greynekos.greybook.logic.commands.stubs;

import greynekos.greybook.logic.parser.commandoption.RequiredOption;
import greynekos.greybook.model.person.PersonIdentifier;

/**
 * Mock stub for ArgumentParseResult to test DeleteCommand. Stores the
 * identifier string (either index or student ID) of the person to be deleted.
 */
public class DeletePersonArgumentParseResultStub extends ArgumentParseResultStub {
    private PersonIdentifier identifier;

    /**
     * @param index
     *            The index of the person to be deleted
     */
    public DeletePersonArgumentParseResultStub(PersonIdentifier identifier) {
        super();
        this.identifier = identifier;
    }

    // This stub should only be called with a flag to get the identifier
    @Override
    public <T> T getValue(RequiredOption<T> flag) {
        @SuppressWarnings("unchecked")
        T ret = (T) identifier;
        return ret;
    }
}
