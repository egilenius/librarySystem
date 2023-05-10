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

        User user = new User("Test");

        //Login Button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 0, 0);
        loginButton.setOnAction(e ->
            {   Scene loginScene = new LoginScene(stage, startScene);
                stage.setScene(loginScene);
            });

        //Search Button
        Button searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 1, 0);
        searchButton.setOnAction(e -> {
            SearchScene searchScene = new SearchScene(stage, startScene, user);
            stage.setScene(searchScene);
        });

        //Test new functions
        Button updateButton = new Button("Update");
        GridPane.setConstraints(updateButton, 2, 0);
        updateButton.setOnAction(e ->
        {   Scene updateScene = new UpdateScene(stage, startScene, user);
            stage.setScene(updateScene);
        });

        Button dueButton = new Button("Overdue");
        GridPane.setConstraints(dueButton, 3, 0);
        dueButton.setOnAction(e ->
        {   Scene dueScene = new DueScene(stage, startScene, user);
            stage.setScene(dueScene);
        });

        Button guestSearchButton = new Button("GuestSearch");
        GridPane.setConstraints(guestSearchButton, 2, 1);
        guestSearchButton.setOnAction(e ->
        {   Scene guestSearchScene = new GuestSearchScene(stage, startScene);
            stage.setScene(guestSearchScene);
        });

        Button homeButton = new Button("Home");
        GridPane.setConstraints(homeButton, 3, 1);
        homeButton.setOnAction(e ->
        {   Scene homeScene = new HomeScene(stage, startScene, user);
            stage.setScene(homeScene);
        });

        //Add all the elements to the grid
        grid.getChildren().addAll(loginButton, searchButton, updateButton, dueButton, guestSearchButton, homeButton);

        startScene = new Scene(grid, 300, 150);
        stage.setScene(startScene);
        stage.show();
    }

}