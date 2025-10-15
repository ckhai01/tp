package greynekos.greybook.logic.commands.stubs;

import static greynekos.greybook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static greynekos.greybook.logic.parser.CliSyntax.PREFIX_NAME;
import static greynekos.greybook.logic.parser.CliSyntax.PREFIX_PHONE;
import static greynekos.greybook.logic.parser.CliSyntax.PREFIX_STUDENTID;

import java.util.ArrayList;
import java.util.List;

import greynekos.greybook.logic.parser.commandoption.Option;
import greynekos.greybook.logic.parser.commandoption.RequiredOption;
import greynekos.greybook.model.person.Person;

/**
 * Mock stub for ArgumentParseResult to test AddCommand. Only stores the person
 * to be added
 */
public class AddPersonArgumentParseResultStub extends ArgumentParseResultStub {
    private Person person;

    /**
     * @param person
     *            The person to be added
     */
    public AddPersonArgumentParseResultStub(Person person) {
        super();
        this.person = person;
    }

    @Override
    public <T> T getValue(RequiredOption<T> flag) {
        Object o = null;
        if (flag.getPrefix().equals(PREFIX_NAME)) {
            o = person.getName();
        } else if (flag.getPrefix().equals(PREFIX_EMAIL)) {
            o = person.getEmail();
        } else if (flag.getPrefix().equals(PREFIX_PHONE)) {
            o = person.getPhone();
        } else if (flag.getPrefix().equals(PREFIX_STUDENTID)) {
            o = person.getStudentID();
        }

        @SuppressWarnings("unchecked")
        T ret = (T) o;
        return ret;
    }

    // Can only be called for tag flag
    @Override
    public <T> List<T> getAllValues(Option<T> flag) {
        @SuppressWarnings("unchecked")
        List<T> ret = (List<T>) new ArrayList<>(person.getTags());

        return ret;
    }
}
