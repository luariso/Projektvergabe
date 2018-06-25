package Proto.gui.fx;

import Proto.domain.Bewertung;
import Proto.domain.Student;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class StudentScene {

    private Scene scene;
    private Student student;
    private MainWindow window;

    public StudentScene(Student student, MainWindow window) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Student " + student.getName() + " " + student.getSurename());
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Button logout = new Button("Logout");
        HBox logoutBtn = new HBox(10);
        logoutBtn.getChildren().add(logout);
        grid.add(logoutBtn, 1, 1);

        Button save = new Button("Übernehmen");
        HBox saveBtn = new HBox(10);
        saveBtn.getChildren().add(save);
        grid.add(saveBtn, 0, 1);

        TableView<Bewertung> table = getBewertungsPane(student);
        grid.add(table, 0, 2, 2, 1);

        save.setOnAction(event -> save(table));
        logout.setOnAction(event -> window.logout());

        scene = new Scene(grid, 500, 500);
    }

    private void save(TableView<Bewertung> table) {
        // TODO implement
    }


    public TableView<Bewertung> getBewertungsPane(Student s){
        TableView<Bewertung> tabelle = new TableView<>();

        TableColumn<Bewertung, String> idCol = new TableColumn<>("Projekt");
        idCol.setCellValueFactory(new PropertyValueFactory<>("project"));

        TableColumn<Bewertung, Integer> bewertungCol = new TableColumn<>("Bewetrung (1 - 5)");
        bewertungCol.setCellFactory((param) -> new StudentScene.RadioButtonCell<>(new Integer[]{1, 2, 3, 4, 5}));
        bewertungCol.setCellValueFactory(new PropertyValueFactory<>("note"));

        tabelle.setItems(FXCollections.observableArrayList(s.getBewertungen()));
        tabelle.getColumns().addAll(idCol, bewertungCol);

        tabelle.setPrefWidth(450);
        return tabelle;
    }

    public static class RadioButtonCell<S, T> extends TableCell<S, T> {
        private T[] choices;
        public RadioButtonCell(T[] choices){
            this.choices = choices;
        }
        @Override
        protected void updateItem(T item, boolean empty){
            super.updateItem(item, empty);
            if(!empty){
                HBox hb = new HBox(7);
                hb.setAlignment(Pos.CENTER);
                final ToggleGroup group = new ToggleGroup();

                for(T t : choices){
                    RadioButton radioButton = new RadioButton();
                    radioButton.setUserData(t);
                    radioButton.setToggleGroup(group);
                    hb.getChildren().add(radioButton);
                    if(t.equals(item)){
                        radioButton.setSelected(true);
                    }
                }

                group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void changed(ObservableValue<? extends Toggle> observable,
                                        Toggle oldValue, Toggle newValue) {
                        getTableView().edit(getIndex(), getTableColumn());
                        StudentScene.RadioButtonCell.this.commitEdit((T) newValue.getUserData());
                    }
                });
                setGraphic(hb);
            }
        }
    }

    public Scene getScene() {
        return scene;
    }
}
