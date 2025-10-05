package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.stubs.AddPersonArgumentParseResultStub;
import seedu.address.logic.commands.stubs.ModelStub;
import seedu.address.logic.commands.stubs.ModelStubAcceptingPersonAdded;
import seedu.address.logic.commands.stubs.ModelStubWithPerson;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();
        AddPersonArgumentParseResultStub argStub = new AddPersonArgumentParseResultStub(validPerson);

        CommandResult commandResult = new AddCommand().execute(modelStub, argStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand();
        ModelStub modelStub = new ModelStubWithPerson(validPerson);
        AddPersonArgumentParseResultStub argStub = new AddPersonArgumentParseResultStub(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON,
                () -> addCommand.execute(modelStub, argStub));
    }
}
