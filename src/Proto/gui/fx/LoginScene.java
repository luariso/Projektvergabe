package Proto.gui.fx;

import Proto.domain.*;
import Proto.domain.Control;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.Collection;
import java.util.Optional;

public class LoginScene {

    private Collection<Student> students;
    private Collection<Supervisor> supervisors;
    private Collection<Admin> admins;
    private Scene scene;
    private MainWindow window;

    LoginScene(MainWindow window) {
        this.window = window;
        students = Control.getStudents();
        supervisors = Control.getSupervisors();
        admins = Control.getAdmins();
        GridPane grid = createGridPane();

        scene = new Scene(grid, 300, 275);
    }

    private void okClicked(Text loginFailed, RadioButton studentToggle, RadioButton supervisorToggle, TextField userName) {
        Optional<? extends User> user;

        if (studentToggle.isSelected()) {
            user = students.stream().filter(s -> (s.getName() + " " + s.getSurname()).equalsIgnoreCase(userName.getText())).findFirst();
        } else if (supervisorToggle.isSelected()) {
            user = supervisors.stream().filter(s -> (s.getName() + " " +  s.getSurname()).equalsIgnoreCase(userName.getText())).findFirst();
        } else {
            user = admins.stream().filter(a -> (a.getName() + " " +  a.getSurname()).equalsIgnoreCase(userName.getText())).findFirst();
        }

        if(user.isPresent()) {
            window.login(user.get());
        } else {
            loginFailed.setFill(Color.FIREBRICK);
            loginFailed.setText("Benutzer existiert nicht.");
        }
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Login");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("Benutzername:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
    
        Label pw = new Label("Passwort:");
        grid.add(pw, 0, 2);
    
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
    
        final ToggleGroup radioGroup = new ToggleGroup();
        RadioButton studentToggle = new RadioButton("Student");
        studentToggle.setToggleGroup(radioGroup);
        RadioButton supervisorToggle = new RadioButton("Betreuer");
        supervisorToggle.setToggleGroup(radioGroup);
        RadioButton adminToggle = new RadioButton("Admin");
        adminToggle.setToggleGroup(radioGroup);
        radioGroup.selectToggle(studentToggle);
        grid.add(studentToggle, 0, 3);
        grid.add(supervisorToggle, 0, 4);
        grid.add(adminToggle, 0, 5);
    
        Button btn = new Button("OK");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 5);
        
        grid.setOnKeyReleased(event -> {
            if(event.getCode().toString().equals("ENTER")){
                btn.fire();
            }
        });

        final Text loginFailed = new Text();
        grid.add(loginFailed, 1, 6, 2, 1);
        btn.setOnAction(event -> okClicked(loginFailed, studentToggle, supervisorToggle, userTextField));
        return grid;
    }

    public Scene getScene() {
        return scene;
    }
}
