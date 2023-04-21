package com.mycompany.librarysystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private Scene startScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Start Screen");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Login Button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 0, 0);
        loginButton.setOnAction(e ->
            {   Scene loginScene = new LoginScreen(stage, startScene);
                stage.setScene(loginScene);
            });

        //Search Button
        Button searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 1, 0);
        searchButton.setOnAction(e -> {
            SearchScene searchScene = new SearchScene(stage, startScene);
            stage.setScene(searchScene);
        });

        //Add all the elements to the grid
        grid.getChildren().addAll(loginButton, searchButton);

        startScene = new Scene(grid, 300, 150);
        stage.setScene(startScene);
        stage.show();
    }

}
