package greynekos.greybook.model;

import greynekos.greybook.model.person.Person;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an GreyBook
 */
public interface ReadOnlyGreyBook {

    /**
     * Returns an unmodifiable view of the persons list. This list will not contain
     * any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
