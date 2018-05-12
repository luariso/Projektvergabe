package Proto;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaFXGUI extends Application {
	
	public static void oeffne(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle("Projektvergabe");
		
		primaryStage.setScene(getBewertungsScene());
		primaryStage.show();
	}
	
	public Scene getBewertungsScene(){
		StackPane layout = new StackPane();
		Scene scene = new Scene(layout);
		
		TableView<Projekt> tableView = new TableView<>();
		tableView.setEditable(true);
		
		TableColumn<Projekt, String> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Projekt, String> titelCol = new TableColumn<>("Titel");
		titelCol.setCellValueFactory(new PropertyValueFactory<>("titel"));
		TableColumn<Projekt, String> teilnehmerCol = new TableColumn<>("Plätze");
		teilnehmerCol.setCellValueFactory(new PropertyValueFactory<>("maxTeilnehmer"));
		
		TableColumn<Projekt, ComboBox> bewertungCol = new TableColumn<>("Bewetrung");
		
		tableView.setItems(FXCollections.observableArrayList(Verwaltung.getProjekte()));
		tableView.getColumns().addAll(idCol, titelCol, teilnehmerCol, bewertungCol);
		
		layout.getChildren().addAll(tableView);
		return scene;
	}
}
