package greynekos.address.logic.commands;

import static greynekos.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static greynekos.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static greynekos.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static greynekos.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static greynekos.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static greynekos.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static greynekos.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static greynekos.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static greynekos.address.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import greynekos.address.commons.core.index.Index;
import greynekos.address.logic.Messages;
import greynekos.address.logic.commands.EditCommand.EditPersonDescriptor;
import greynekos.address.logic.commands.stubs.EditPersonArgumentParseResultStub;
import greynekos.address.model.AddressBook;
import greynekos.address.model.Model;
import greynekos.address.model.ModelManager;
import greynekos.address.model.UserPrefs;
import greynekos.address.model.person.Person;
import greynekos.address.testutil.EditPersonDescriptorBuilder;
import greynekos.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder().withStudentID(personToEdit.getStudentID().value).build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditPersonArgumentParseResultStub argStub =
                new EditPersonArgumentParseResultStub(INDEX_FIRST_PERSON, descriptor);
        EditCommand editCommand = new EditCommand();

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, argStub, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson =
                personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditPersonArgumentParseResultStub argStub = new EditPersonArgumentParseResultStub(indexLastPerson, descriptor);
        EditCommand editCommand = new EditCommand();

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, argStub, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();

        EditPersonArgumentParseResultStub argStub = new EditPersonArgumentParseResultStub(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
        EditCommand editCommand = new EditCommand();

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, argStub, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        EditPersonArgumentParseResultStub argStub =
                new EditPersonArgumentParseResultStub(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        EditCommand editCommand = new EditCommand();

        String expectedMessage = EditCommand.MESSAGE_NOT_EDITED;

        assertCommandFailure(editCommand, model, argStub, expectedMessage);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditPersonArgumentParseResultStub argStub =
                new EditPersonArgumentParseResultStub(INDEX_SECOND_PERSON, descriptor);
        EditCommand editCommand = new EditCommand();

        assertCommandFailure(editCommand, model, argStub, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditPersonArgumentParseResultStub argStub = new EditPersonArgumentParseResultStub(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());
        EditCommand editCommand = new EditCommand();

        assertCommandFailure(editCommand, model, argStub, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditPersonArgumentParseResultStub argStub = new EditPersonArgumentParseResultStub(outOfBoundIndex, descriptor);
        EditCommand editCommand = new EditCommand();

        assertCommandFailure(editCommand, model, argStub, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but
     * smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditPersonArgumentParseResultStub argStub = new EditPersonArgumentParseResultStub(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
        EditCommand editCommand = new EditCommand();

        assertCommandFailure(editCommand, model, argStub, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
