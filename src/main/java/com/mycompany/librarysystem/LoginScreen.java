package com.mycompany.librarysystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginScreen extends Scene {
    //private Scene homeScene;

    public LoginScreen(Stage stage, Scene startScene) {
        super(new GridPane(), 300, 150);
        GridPane grid = (GridPane) this.getRoot();
        //this.homeScene = homeScene;
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

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
            HomeScene homeScene = new HomeScene(stage, startScene);
            stage.setScene(homeScene);
        });

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton);
        stage.setTitle("Login Screen");
    }
}