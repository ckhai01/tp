package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    @FXML
    private TableView<Person> personTableView;

    @FXML
    TableColumn<Person, String> indexColumn;

    @FXML
    TableColumn<Person, String> nameColumn;

    @FXML
    TableColumn<Person, String> studentIDColumn;

    @FXML
    TableColumn<Person, String> emailColumn;

    @FXML
    TableColumn<Person, String> phoneColumn;

    @FXML
    TableColumn<Person, String> tagsColumn;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        indexColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index + 1)); // +1 to make it 1-based index
                }
            }
        });

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName().toString()));
        studentIDColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getStudentID().toString()));
        emailColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail().toString()));
        phoneColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone().toString()));
        tagsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getTags().stream().map(Object::toString).reduce((x, y) -> x + "," + y).orElse("")));

        personTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        personTableView.setItems(personListView.getItems());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using
     * a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
