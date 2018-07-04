package Proto.gui.fx;

import Proto.domain.Control;
import Proto.domain.Project;
import Proto.domain.Student;
import Proto.domain.Supervisor;
import demo_ruelling.Proj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupervisorScene {

	private int width = 500, height = 500, padding = 25;
	private Scene scene;
	private Supervisor supervisor;

	SupervisorScene(int stageId, Supervisor supervisor, MainWindow window) {
		this.supervisor = supervisor;
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(padding, padding, padding, padding));

		Text sceneTitle = new Text("Projekte von " + supervisor.getName()+ " " + supervisor.getSurname());
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 0, 0, 2, 1);

		Button logout = new Button("Logout");
		HBox logoutBtn = new HBox(10);
		logoutBtn.getChildren().add(logout);
		grid.add(logoutBtn, 0, 1);

		ListView<String> projects = getProjects();
		projects.setMinHeight(100);
		projects.setPrefSize(width - (2 * padding), 150);
		grid.add(projects, 0, 2, 2, 1);

		Text detailsHeadline = new Text("Bitte wählen Sie ein Projekt.");
		detailsHeadline.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		grid.add(detailsHeadline, 0, 3, 2, 1);

		ListView<String> details = new ListView<>();
		details.setItems(FXCollections.observableArrayList(""));
		grid.add(details, 0, 4, 2, 1);

		logout.setOnAction(event -> window.logout(stageId));
		projects.setOnMouseClicked(event -> projectSelected(projects.getSelectionModel().getSelectedItem(), details, detailsHeadline));

		scene = new Scene(grid, width, height);
	}

	private void projectSelected(String selectedItemStr, ListView<String> details, Text headline) {
		Optional<Project> selectedProject = Optional.empty();
		for (Project p: Control.getProjects()) {
			if (p.toString().equals(selectedItemStr)) {
				selectedProject = Optional.of(p);
			}
		}

		if (selectedProject.isPresent()) {
			headline.setText("Teilnehmer von \"" + selectedProject.get().getTitle() + "\":");
			List<String> members = selectedProject.get().getMembers().stream().map(Student::toString).collect(Collectors.toList());
			if (members.isEmpty()) {
				details.setItems(FXCollections.observableArrayList("Das Projekt hat noch keine Teilnehmer"));
			}
			else {
				details.setItems(FXCollections.observableArrayList(members));
			}
		}
		else {
			System.out.println("Error while loading details of Project \"" + selectedItemStr + "\": Project not found");
		}
	}

	private ListView<String> getProjects() {
		ListView<String> list = new ListView<>();

		List<String> projects = new ArrayList<>();
		for (Project p: supervisor.getProjects()) {
			projects.add(p.toString());
		}

		ObservableList<String> items = FXCollections.observableArrayList(projects);
		list.setItems(items);

		return list;
	}

	public Scene getScene() {
		return scene;
	}
}
