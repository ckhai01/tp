package greynekos.greybook.ui;

import java.util.logging.Logger;

import greynekos.greybook.commons.core.LogsCenter;
import greynekos.greybook.model.person.AttendanceStatus;
import greynekos.greybook.model.person.Person;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class PersonTablePanel extends UiPart<Region> {
    private static final String FXML = "PersonTablePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonTablePanel.class);

    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableColumn<Person, String> indexColumn;

    @FXML
    private TableColumn<Person, String> nameColumn;

    @FXML
    private TableColumn<Person, String> studentIdColumn;

    @FXML
    private TableColumn<Person, String> emailColumn;

    @FXML
    private TableColumn<Person, String> phoneColumn;

    @FXML
    private TableColumn<Person, String> tagsColumn;

    @FXML
    private TableColumn<Person, String> attendanceStatusColumn;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonTablePanel(ObservableList<Person> personList) {
        super(FXML);
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
        studentIdColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getStudentID().toString()));
        emailColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail().toString()));
        phoneColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone().toString()));
        tagsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTags().stream()
                .map(tag -> tag.tagName).reduce((x, y) -> x + ", " + y).orElse("")));

        // Custom cell factory for tags to display them as styled chips
        tagsColumn.setCellFactory(col -> new TableCell<Person, String>() {
            private final javafx.scene.layout.FlowPane flowPane = new javafx.scene.layout.FlowPane();

            {
                flowPane.setId("tags");
                flowPane.setHgap(4);
                flowPane.setVgap(4);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.isEmpty()) {
                    setGraphic(null);
                } else {
                    flowPane.getChildren().clear();
                    Person person = getTableView().getItems().get(getIndex());
                    person.getTags().forEach(tag -> {
                        javafx.scene.control.Label tagLabel = new javafx.scene.control.Label(tag.tagName);
                        flowPane.getChildren().add(tagLabel);
                    });
                    flowPane.setPrefWrapLength(getWidth()); // Wrap based on cell width
                    setGraphic(flowPane);
                }
            }
         
        attendanceStatusColumn.setCellValueFactory(cellData -> {
            AttendanceStatus status = cellData.getValue().getAttendance();
            String display = (status.value == AttendanceStatus.Status.NONE) ? "" : status.toString();
            return new SimpleStringProperty(display);
        });

        personTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        personTableView.setItems(personList);
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
