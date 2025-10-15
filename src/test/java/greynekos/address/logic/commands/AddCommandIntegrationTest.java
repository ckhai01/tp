package greynekos.address.logic.commands;

import static greynekos.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static greynekos.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static greynekos.address.testutil.TypicalPersons.getTypicalGreyBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import greynekos.address.logic.Messages;
import greynekos.address.logic.commands.stubs.AddPersonArgumentParseResultStub;
import greynekos.address.model.Model;
import greynekos.address.model.ModelManager;
import greynekos.address.model.UserPrefs;
import greynekos.address.model.person.Person;
import greynekos.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGreyBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();
        AddPersonArgumentParseResultStub argStub = new AddPersonArgumentParseResultStub(validPerson);

        Model expectedModel = new ModelManager(model.getGreyBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(), model, argStub,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getGreyBook().getPersonList().get(0);
        AddPersonArgumentParseResultStub argStub = new AddPersonArgumentParseResultStub(personInList);
        assertCommandFailure(new AddCommand(), model, argStub, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
