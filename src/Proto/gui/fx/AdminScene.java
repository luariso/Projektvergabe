package Proto.gui.fx;

import Proto.domain.Project;
import Proto.domain.Student;
import Proto.domain.Control;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class AdminScene{
	
	private Scene scene;
	private MainWindow window;
	
	public AdminScene(MainWindow window){
		this.window = window;
		BorderPane pane = new BorderPane();
		pane.setCenter(getStudentsPane());
		pane.setRight(getProjectsPane());
		pane.autosize();
		scene = new Scene(pane);
	}
	
	public Pane getStudentsPane(){
		BorderPane pane = new BorderPane();

		TableView<Student> tableView = new TableView<>();
		tableView.setEditable(true);

		TableColumn<Student, String> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Student, String> nameCol = new TableColumn<>("Vorname");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Student, String> surnameCol = new TableColumn<>("Nachname");
		surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
		TableColumn<Student, String> matriculationCol = new TableColumn<>("Matrikelnummer");
		matriculationCol.setCellValueFactory(new PropertyValueFactory<>("matriculation"));
		TableColumn<Student, String> projectCol = new TableColumn<>("Projekt");
		projectCol.setCellValueFactory(new PropertyValueFactory<>("project"));

		tableView.setItems(FXCollections.observableArrayList(Control.getStudents()));
		tableView.getColumns().addAll(idCol, nameCol, surnameCol, matriculationCol, projectCol);

		Label title = new Label("Studenten");
		title.setPadding(new Insets(5, 5, 5, 5));
		pane.setTop(title);
		pane.setCenter(tableView);
		return pane;
	}
	
	public Pane getProjectsPane(){
		BorderPane pane = new BorderPane();
		
		TableView<Project>  tableView = new TableView<>();
		
		TableColumn<Project, String> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Project, String> titleCol = new TableColumn<>("Titel");
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		TableColumn<Project, String> memberCol = new TableColumn<>("Teilnehmer");
		memberCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getMembers().size() + " von " + e.getValue().getMaxMembers()));
		tableView.setItems(FXCollections.observableArrayList(Control.getProjects()));
		tableView.getColumns().addAll(idCol, titleCol, memberCol);
		
		Label title = new Label("Projekte");
		title.setPadding(new Insets(5, 5, 5, 5));
		pane.setTop(title);
		pane.setCenter(tableView);
		pane.setPrefWidth(500);
		return pane;
	}
	
	public Scene getScene(){
		return scene;
	}
}
