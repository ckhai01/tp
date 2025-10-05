package seedu.address.logic.commands.stubs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.commandoption.Option;
import seedu.address.logic.parser.commandoption.OptionalOption;
import seedu.address.logic.parser.commandoption.RequiredOption;
import seedu.address.model.tag.Tag;

/**
 * Mock stub for ArgumentParseResult to test EditCommand.
 * Stores the index of the person to be editted and the EditPersonDescriptor for
 * the person.
 */
public class EditPersonArgumentParseResultStub extends ArgumentParseResultStub {
    private Index index;
    private EditPersonDescriptor descriptor;

    /**
     * @param index      the Index of the person to be editted
     * @param descriptor the EditorPersonDescriptor to edit the person
     */
    public EditPersonArgumentParseResultStub(Index index, EditPersonDescriptor descriptor) {
        super();
        this.index = index;
        this.descriptor = descriptor;
    }

    // Can only be called for index flag
    @Override
    public <T> T getValue(RequiredOption<T> flag) {
        @SuppressWarnings("unchecked")
        T ret = (T) index;

        return ret;
    }

    @Override
    public <T> Optional<T> getOptionalValue(OptionalOption<T> flag) {
        Object o = null;
        if (flag.getPrefix().equals(PREFIX_NAME)) {
            o = descriptor.getName();
        } else if (flag.getPrefix().equals(PREFIX_EMAIL)) {
            o = descriptor.getEmail();
        } else if (flag.getPrefix().equals(PREFIX_PHONE)) {
            o = descriptor.getPhone();
        } else if (flag.getPrefix().equals(PREFIX_ADDRESS)) {
            o = descriptor.getAddress();
        } else if (flag.getPrefix().equals(PREFIX_EMPTY)) {
            o = index;
        }

        @SuppressWarnings("unchecked")
        Optional<T> ret = (Optional<T>) o;
        return ret;
    }

    // Can only be called for tag flag
    @Override
    public <T> List<T> getAllValues(Option<T> flag) {
        @SuppressWarnings("unchecked")
        List<T> ret = (List<T>) new ArrayList<>(
                descriptor.getTags().isPresent() ? (descriptor.getTags().get().isEmpty() ? List.of(new Tag())
                        : descriptor.getTags().get()) : Set.of());

        return ret;
    }
}
