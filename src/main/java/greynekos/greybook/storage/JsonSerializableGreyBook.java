package greynekos.greybook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import greynekos.greybook.commons.exceptions.IllegalValueException;
import greynekos.greybook.model.GreyBook;
import greynekos.greybook.model.ReadOnlyGreyBook;
import greynekos.greybook.model.person.Person;

/**
 * An Immutable GreyBook that is serializable to JSON format.
 */
@JsonRootName(value = "greybook")
class JsonSerializableGreyBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableGreyBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableGreyBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyGreyBook} into this class for Jackson use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            {@code JsonSerializableGreyBook}.
     */
    public JsonSerializableGreyBook(ReadOnlyGreyBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this GreyBook into the model's {@code GreyBook} object.
     *
     * @throws IllegalValueException
     *             if there were any data constraints violated.
     */
    public GreyBook toModelType() throws IllegalValueException {
        GreyBook greyBook = new GreyBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (greyBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            greyBook.addPerson(person);
        }
        return greyBook;
    }

}
