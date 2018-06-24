package Proto.gui.fx;

import Proto.domain.Supervisor;
import Proto.domain.User;
import Proto.domain.Student;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainWindow extends Application{

    private Stage primaryStage;

    public static void open(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Projektvergabe Demo");
        setLoginScene();
        primaryStage.show();
    }

    public void login(User user) {
        if (user.getClass() == Student.class) {
            primaryStage.setScene(new StudentScene((Student) user, this).getScene());
        } else if(user.getClass() == Supervisor.class) {
            // TODO implement
        } else {
            primaryStage.setScene(new AdminScene(this).getScene());
        }
    }

    public void logout() {
        setLoginScene();
    }

    private void setLoginScene() {
        primaryStage.setScene(new LoginScene(this).getScene());
    }
}
