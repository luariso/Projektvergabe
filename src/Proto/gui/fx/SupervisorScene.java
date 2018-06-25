package Proto.gui.fx;

import Proto.domain.Project;
import Proto.domain.Supervisor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SupervisorScene {

	private int width = 500, height = 500, padding = 25;
	private Scene scene;
	private Supervisor supervisor;

	public SupervisorScene(Supervisor supervisor, MainWindow window) {
		this.supervisor = supervisor;
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(padding, padding, padding, padding));

		Text sceneTitle = new Text("Projekte von " + supervisor.getName()+ " " + supervisor.getSurename());
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 0, 0, 2, 1);

		Button logout = new Button("Logout");
		HBox logoutBtn = new HBox(10);
		logoutBtn.getChildren().add(logout);
		grid.add(logoutBtn, 0, 1);

		ListView<String> projects = getProjects();
		projects.setPrefSize(width - (2 * padding), 150);
		grid.add(projects, 0, 2, 2, 1);

		ListView<String> details = new ListView<>();

		logout.setOnAction(event -> window.logout());

		scene = new Scene(grid, width, height);
	}

	private ListView<String> getProjects() {
		ListView<String> list = new ListView<>();

		List<String> projects = new ArrayList<>();
		for (Project p: supervisor.getProjects()) {
			projects.add(p.getId() + " " + p.getTitel());
		}

		ObservableList<String> items = FXCollections.observableArrayList(projects);
		list.setItems(items);

		return list;
	}

	public Scene getScene() {
		return scene;
	}
}
