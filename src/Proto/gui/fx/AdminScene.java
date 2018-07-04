package Proto.gui.fx;

import Proto.domain.Project;
import Proto.domain.Student;
import Proto.domain.Control;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

//TODO remove unnecessary table columns.
public class AdminScene{
	
	private Scene scene;
	private MainWindow window;
	private int stageId;
	
	public AdminScene(int stageId, MainWindow window){
		this.stageId = stageId;
		this.window = window;

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text sceneTitle = new Text("Übersicht der Projektvergabe");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 0, 0);

		FlowPane buttons = new FlowPane();
		buttons.setAlignment(Pos.CENTER_LEFT);
		buttons.setOrientation(Orientation.HORIZONTAL);
		buttons.setHgap(10);

		Button logout = new Button("Logout");
		HBox logoutBtn = new HBox(10);
		logoutBtn.getChildren().add(logout);
		logout.setOnAction(event -> window.logout(stageId));
		buttons.getChildren().add(logoutBtn);

		Button match = new Button("Vergebe Projekte");
		HBox machBtn = new HBox(10);
		machBtn.getChildren().add(match);
		match.setOnAction(event -> Control.match());
		buttons.getChildren().add(machBtn);

		Button reset = new Button("Vergabe zurücksetzten");
		HBox resetBtn = new HBox(10);
		resetBtn.getChildren().add(reset);
		reset.setOnAction(event -> Control.resetMatching());
		buttons.getChildren().add(resetBtn);

		grid.add(buttons, 0, 1);

		GridPane tables = new GridPane();
		tables.setAlignment(Pos.CENTER_LEFT);
		tables.setHgap(10);

		tables.add(getStudentsPane(), 0, 0);
		tables.add(getProjectsPane(), 1, 0);

		grid.add(tables, 0, 2);

		grid.autosize();
		scene = new Scene(grid);
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

		int maxProjLength = 0;
		for (Project p: Control.getProjects()) {
			int projLength = p.getTitle().length();
			if (projLength > maxProjLength) {
				maxProjLength = projLength;
			}
		}
		projectCol.setPrefWidth(maxProjLength * new Text("a").getLayoutBounds().getWidth());
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
