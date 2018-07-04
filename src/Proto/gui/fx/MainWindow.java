package Proto.gui.fx;

import Proto.domain.Supervisor;
import Proto.domain.User;
import Proto.domain.Student;
import javafx.application.Application;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainWindow extends Application {

    private static int numberOfWindows;
    private List<Stage> stages = new ArrayList<>();

    public static void open(String[] args) {
    	try {
			numberOfWindows = Integer.valueOf(args[0]);
			if (numberOfWindows < 1) {
				numberOfWindows = 1;
			}
		} catch (NumberFormatException e) {
    		throw new IllegalArgumentException("Ungüliger Programm Parameter. Bitte eine Zahl eingeben.");
		} catch (IndexOutOfBoundsException e) {
    		numberOfWindows = 1;
		}
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        for (int i = 0; i < numberOfWindows; ++i) {
        	Stage stage = new Stage();
			stage.setTitle("Projektvergabe Demo");
			stage.setScene(new LoginScene(i, this).getScene());
			stages.add(stage);
			stage.show();
		}
    }

    public void login(int stageId, User user) {
        if (user.getClass() == Student.class) {
            stages.get(stageId).setScene(new StudentScene(stageId, (Student) user, this).getScene());
        } else if(user.getClass() == Supervisor.class) {
			stages.get(stageId).setScene(new SupervisorScene(stageId, (Supervisor) user, this).getScene());
        } else {
			stages.get(stageId).setScene(new AdminScene(stageId, this).getScene());
        }
    }

    public void logout(int stageId) {
    	stages.get(stageId).setScene(new LoginScene(stageId, this).getScene());
    }

}
