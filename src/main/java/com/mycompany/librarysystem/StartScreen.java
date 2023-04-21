package com.mycompany.librarysystem;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartScreen extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Start Screen");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Login Button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 0, 0);

        //Search Button
        Button searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 1, 0);

        //Add all the elements to the grid
        grid.getChildren().addAll(loginButton, searchButton);

        Scene scene = new Scene(grid, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}



