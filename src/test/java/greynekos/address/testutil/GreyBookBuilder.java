package greynekos.address.testutil;

import greynekos.address.model.GreyBook;
import greynekos.address.model.person.Person;

/**
 * A utility class to help with building GreyBook objects. Example usage:
 * <br>
 * {@code GreyBook ab = new GreyBookBuilder().withPerson("John", "Doe").build();}
 */
public class GreyBookBuilder {

    private GreyBook greyBook;

    public GreyBookBuilder() {
        greyBook = new GreyBook();
    }

    public GreyBookBuilder(GreyBook greyBook) {
        this.greyBook = greyBook;
    }

    /**
     * Adds a new {@code Person} to the {@code GreyBook} that we are building.
     */
    public GreyBookBuilder withPerson(Person person) {
        greyBook.addPerson(person);
        return this;
    }

    public GreyBook build() {
        return greyBook;
    }
}
