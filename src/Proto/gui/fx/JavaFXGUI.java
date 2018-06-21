package Proto.gui.fx;

import Proto.domain.Rating;
import Proto.domain.Student;
import Proto.domain.Control;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class JavaFXGUI extends Application {

	public static void oeffne(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		BorderPane mainLayout = new BorderPane();
		Scene mainScene = new Scene(mainLayout);

		primaryStage.setTitle("Projektvergabe");
		primaryStage.setScene(mainScene);
		mainLayout.setCenter(getBewertungsScene(Control.getStudent(0)));
		mainLayout.setRight(getStudentenScene());

		primaryStage.sizeToScene();

		primaryStage.show();
	}

	public Pane getBewertungsScene(Student s){
		BorderPane layout = new BorderPane();

		TableView<Rating> tabelle = new TableView<>();

		TableColumn<Rating, String> idCol = new TableColumn<>("Project");
		idCol.setCellValueFactory(new PropertyValueFactory<>("projekt"));

		TableColumn<Rating, Integer> bewertungCol = new TableColumn<>("Bewetrung (1 - 5)");
		bewertungCol.setCellFactory((param) -> new RadioButtonCell<>(new Integer[]{1, 2, 3, 4, 5}));
		bewertungCol.setCellValueFactory(new PropertyValueFactory<>("note"));

		tabelle.setItems(FXCollections.observableArrayList(s.getRatings()));
		tabelle.getColumns().addAll(idCol, bewertungCol);

		Label ueberschrift = new Label("Die Bewertungen von " + s);
		ueberschrift.setPadding(new Insets(5, 5, 5, 5));
		layout.setTop(ueberschrift);
		layout.setCenter(tabelle);
		layout.setMinWidth(450);
		return layout;
	}

	public static class RadioButtonCell<S, T> extends TableCell<S, T>{
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
						RadioButtonCell.this.commitEdit((T) newValue.getUserData());
					}
				});
				setGraphic(hb);
			}
		}
	}

	public Pane getStudentenScene(){
		BorderPane layout = new BorderPane();

		TableView<Student> tableView = new TableView<>();
		tableView.setEditable(true);

		TableColumn<Student, String> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Student, String> vornameCol = new TableColumn<>("Vorname");
		vornameCol.setCellValueFactory(new PropertyValueFactory<>("vorname"));
		TableColumn<Student, String> nachnameCol = new TableColumn<>("Nachname");
		nachnameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Student, String> matrikelCol = new TableColumn<>("Matrikelnummer");
		matrikelCol.setCellValueFactory(new PropertyValueFactory<>("matrikelNummer"));

		tableView.setItems(FXCollections.observableArrayList(Control.getStudents()));
		tableView.getColumns().addAll(idCol, vornameCol, nachnameCol, matrikelCol);

		Label ueberschrift = new Label("Studenten");
		ueberschrift.setPadding(new Insets(5, 5, 5, 5));
		layout.setTop(ueberschrift);
		layout.setCenter(tableView);
		return layout;
	}
}
