package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentParseResult;
import seedu.address.logic.parser.Flag;
import seedu.address.logic.parser.Flag.FlagOption;
import seedu.address.logic.parser.GreyBookParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) " + "[" + PREFIX_NAME + "NAME] " + "[" + PREFIX_PHONE
            + "PHONE] " + "[" + PREFIX_EMAIL + "EMAIL] " + "[" + PREFIX_ADDRESS + "ADDRESS] " + "[" + PREFIX_TAG
            + "TAG]...\n" + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PHONE + "91234567 " + PREFIX_EMAIL
            + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Flag<Index> indexFlag = Flag.of("INDEX", FlagOption.SINGLE_PREAMBLE, ParserUtil::parseIndex);
    private final Flag<Name> nameFlag = Flag.of(PREFIX_NAME, "NAME", FlagOption.OPTIONAL, ParserUtil::parseName);
    private final Flag<Phone> phoneFlag = Flag.of(PREFIX_PHONE, "PHONE", FlagOption.OPTIONAL, ParserUtil::parsePhone);
    private final Flag<Email> emailFlag = Flag.of(PREFIX_EMAIL, "EMAIL", FlagOption.OPTIONAL, ParserUtil::parseEmail);
    private final Flag<Address> addressFlag =
            Flag.of(PREFIX_ADDRESS, "ADDRESS", FlagOption.OPTIONAL, ParserUtil::parseAddress);
    private final Flag<Tag> tagFlag =
            Flag.of(PREFIX_TAG, "TAG", FlagOption.ZERO_OR_MORE, ParserUtil::parseTagAllowEmpty);

    @Override
    public void addToParser(GreyBookParser parser) {
        parser.newCommand(COMMAND_WORD, MESSAGE_USAGE, this).addFlags(indexFlag, nameFlag, phoneFlag, emailFlag,
                addressFlag, tagFlag);
    }

    private Optional<Set<Tag>> parseTagsForEdit(Collection<Tag> tags) {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }

        Collection<Tag> tagSet = tags.size() == 1 && tags.stream().anyMatch(tag -> tag.tagName.equals(""))
                ? Collections.emptySet()
                : tags;
        return Optional.of(Set.copyOf(tagSet));
    }

    @Override
    public CommandResult execute(Model model, ArgumentParseResult arg) throws CommandException {
        requireNonNull(model);

        Index index = arg.getValue(indexFlag);

        EditPersonDescriptor editPersonDescriptor = getParseResult(arg);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new CommandException(EditCommand.MESSAGE_NOT_EDITED);
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public EditPersonDescriptor getParseResult(ArgumentParseResult argResult) {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argResult.getOptionalValue(nameFlag).isPresent()) {
            editPersonDescriptor.setName(argResult.getValue(nameFlag));
        }
        if (argResult.getOptionalValue(phoneFlag).isPresent()) {
            editPersonDescriptor.setPhone(argResult.getValue(phoneFlag));
        }
        if (argResult.getOptionalValue(emailFlag).isPresent()) {
            editPersonDescriptor.setEmail(argResult.getValue(emailFlag));
        }
        if (argResult.getOptionalValue(addressFlag).isPresent()) {
            editPersonDescriptor.setAddress(argResult.getValue(addressFlag));
        }
        parseTagsForEdit(argResult.getAllValues(tagFlag)).ifPresent(editPersonDescriptor::setTags);

        return editPersonDescriptor;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will
     * replace the corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}. A defensive copy of
         * {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws
         * {@code UnsupportedOperationException} if modification is attempted. Returns
         * {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).add("name", name).add("phone", phone).add("email", email)
                    .add("address", address).add("tags", tags).toString();
        }
    }
}
