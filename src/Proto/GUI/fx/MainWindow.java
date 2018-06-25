package Proto.gui.fx;

import Proto.domain.Nutzer;
import Proto.domain.Student;
import Proto.domain.Supervisor;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainWindow extends Application{

    private Stage primaryStage;

    public static void oeffne(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Projektvergabe Demo");
        setLoginScene();
        primaryStage.show();
    }

    public void login(Nutzer nutzer) {
        if (nutzer.getClass() == Student.class) {
            primaryStage.setScene(new StudentScene((Student) nutzer, this).getScene());
        } else if (nutzer.getClass() == Supervisor.class){
            primaryStage.setScene(new SupervisorScene((Supervisor) nutzer, this).getScene());
        }
    }

    public void logout() {
        setLoginScene();
    }

    private void setLoginScene() {
        primaryStage.setScene(new LoginScene(this).getScene());
    }
}
