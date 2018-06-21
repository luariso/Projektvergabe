package Proto.gui.fx;

import Proto.domain.User;
import Proto.domain.Student;
import Proto.domain.Supervisor;
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
    private Scene scene;
    private MainWindow window;

    LoginScene(MainWindow window) {
        this.window = window;
        students = Control.getStudents();
        supervisors = Control.getSupervisors();
        GridPane grid = createGridPane();

        scene = new Scene(grid, 300, 275);
    }

    private void okClicked(Text loginFailed, RadioButton studentToggle, TextField userName) {
        boolean exceptionThrown = false;
        Optional<? extends User> user = Optional.empty();
        try {
            String fullName = userName.getText().toLowerCase();
            String name = fullName.substring(0, fullName.indexOf(" "));
            String sureName = fullName.substring(fullName.indexOf(" ") + 1);


            if (studentToggle.isSelected()) {
                user = students.stream().filter(s -> s.getName().toLowerCase().equals(name) && s.getSurname().toLowerCase().equals(sureName)).findFirst();
            } else {
                user = supervisors.stream().filter(s -> s.getName().toLowerCase().equals(name) && s.getSurname().toLowerCase().equals(sureName)).findFirst();
            }
        } catch (StringIndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        if(user.isPresent() && !exceptionThrown) { // TODO WTF!? resolve warning
            window.login(user.get());
        } else {
            loginFailed.setFill(Color.FIREBRICK);
            loginFailed.setText("Benutzername existiert nicht.");
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
        radioGroup.selectToggle(studentToggle);
        grid.add(studentToggle, 0, 3);
        grid.add(supervisorToggle, 0, 4);

        Button btn = new Button("OK");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text loginFailed = new Text();
        grid.add(loginFailed, 1, 6);
        btn.setOnAction(event -> okClicked(loginFailed, studentToggle, userTextField));
        return grid;
    }

    public Scene getScene() {
        return scene;
    }
}
