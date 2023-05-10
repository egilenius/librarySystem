package com.mycompany.librarysystem;

import java.sql.*;
import java.util.UUID;



import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginScene extends Scene {

    private User user;

    public LoginScene(Stage stage, Scene startScene, User user) {
        super(new GridPane(), 300, 150);
        GridPane grid = (GridPane) this.getRoot();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        this.user = user;

        //Username Label
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);

        //Username Input
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");
        GridPane.setConstraints(usernameInput, 1, 0);

        //Password Label
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        //Password Input
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        GridPane.setConstraints(passwordInput, 1, 1);

        //Login Button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);

        loginButton.setOnAction(e -> {
            try {
                // username asd
                // password as
                User loggedInUser = validateUser(usernameInput.getText(), passwordInput.getText());
                if (loggedInUser != null) {
                    this.user = loggedInUser;
                    HomeScene homeScene = new HomeScene(stage, startScene, loggedInUser);
                    stage.setScene(homeScene);

                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password!");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton);
        stage.setTitle("Login Screen");
    }

    private User validateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM public.anv WHERE username = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                UUID userid = UUID.fromString(resultSet.getString("userid"));
                int allowedloans = resultSet.getInt("allowedloans");
                int presentloans = resultSet.getInt("presentloans");

                return new User(username, userid, allowedloans, presentloans);
            } else {
                return null;
            }
        }
    }
}
