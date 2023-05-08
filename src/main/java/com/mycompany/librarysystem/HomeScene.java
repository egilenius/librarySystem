package com.mycompany.librarysystem;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HomeScene extends Scene {

    public HomeScene(Stage stage, Scene startScene) {
        super(new BorderPane(), 400, 300);
        //BorderPane borderPane = (BorderPane) this.getRoot();
        //addButtonsToTop(borderPane, stage, startScene);

        Node buttons = getButtons(stage, startScene);
        BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setTop(buttons);

        // Create main grid pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Show scene
        stage.setTitle("Library system");
        stage.setScene(this);
        stage.show();

    }

    public Node getButtons(Stage stage, Scene startScene) {
        BorderPane borderPane = new BorderPane();
        // add buttons to the borderPane
        //User buttons
        //Home
        Button homeButton = new Button("Home");
        GridPane.setConstraints(homeButton, 0, 0);
        homeButton.setOnAction(e -> {
            Scene homeScene = new HomeScene(stage, startScene);
            stage.setScene(homeScene);
        });
        //Search
        Button searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 1, 0);
        searchButton.setOnAction(e -> {
            Scene searchScene = new SearchScene(stage, startScene);
            stage.setScene(searchScene);
        });
        //Loans
        Button LoanButton = new Button("Loans");
        GridPane.setConstraints(LoanButton, 2, 0);
        LoanButton.setOnAction(e -> {
            Scene LoanScene = new LoanScene(stage, startScene);
            stage.setScene(LoanScene);
        });
        //Logout
        Button LogoutButton = new Button("Logout");
        GridPane.setConstraints(LogoutButton, 3, 0);
        LogoutButton.setOnAction(e -> {
            stage.setScene(startScene);
        });
        //Create librarian buttons
        //Due items button
        Button dueButton = new Button("Due items");
        GridPane.setConstraints(dueButton, 0, 1);
        dueButton.setOnAction(e -> {
            Scene dueScene = new DueScene(stage, startScene);
            stage.setScene(dueScene);
        });
        //add item button
        Button addButton = new Button("Add item");
        GridPane.setConstraints(addButton, 1, 1);
        addButton.setOnAction(e -> {
            Scene addScene = new AddScene(stage, startScene);
            stage.setScene(addScene);
        });

        //change item button
        Button updateButton = new Button("Update item");
        GridPane.setConstraints(updateButton, 2, 1);
        updateButton.setOnAction(e -> {
            Scene updateScene = new UpdateScene(stage, startScene);
            stage.setScene(updateScene);
        });

        // Add buttons to top of BorderPane
        GridPane topGrid = new GridPane();
        topGrid.setPadding(new Insets(10, 10, 10, 10));
        topGrid.setHgap(10);
        topGrid.getChildren().addAll(homeButton, searchButton, LoanButton, LogoutButton, dueButton, addButton, updateButton);
        borderPane.setTop(topGrid);
        return borderPane;
    }

    private void addButtonsToTop(BorderPane borderPane, Stage stage, Scene startScene) {
        //User buttons
        //Home
        Button homeButton = new Button("Home");
        GridPane.setConstraints(homeButton, 0, 0);
        homeButton.setOnAction(e -> {
            Scene homeScene = new HomeScene(stage, startScene);
            stage.setScene(homeScene);
        });
        //Search
        Button searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 1, 0);
        searchButton.setOnAction(e -> {
            Scene searchScene = new SearchScene(stage, startScene);
            stage.setScene(searchScene);
        });
        //Loans
        Button LoanButton = new Button("Loans");
        GridPane.setConstraints(LoanButton, 2, 0);
        LoanButton.setOnAction(e -> {
            Scene LoanScene = new LoanScene(stage, startScene);
            stage.setScene(LoanScene);
        });
        //Logout
        Button LogoutButton = new Button("Logout");
        GridPane.setConstraints(LogoutButton, 3, 0);
        LogoutButton.setOnAction(e -> {
            stage.setScene(startScene);
        });
        //Create librarian buttons
        //Due items button
        Button dueButton = new Button("Due items");
        GridPane.setConstraints(dueButton, 0, 1);
        dueButton.setOnAction(e -> {
            Scene dueScene = new DueScene(stage, startScene);
            stage.setScene(dueScene);
        });
        //add item button
        Button addButton = new Button("Add item");
        GridPane.setConstraints(addButton, 1, 1);
        addButton.setOnAction(e -> {
            Scene addScene = new AddScene(stage, startScene);
            stage.setScene(addScene);
        });

        //change item button
        Button updateButton = new Button("Update item");
        GridPane.setConstraints(updateButton, 2, 1);
        updateButton.setOnAction(e -> {
            Scene updateScene = new UpdateScene(stage, startScene);
            stage.setScene(updateScene);
        });

        // Add buttons to top of BorderPane
        GridPane topGrid = new GridPane();
        topGrid.setPadding(new Insets(10, 10, 10, 10));
        topGrid.setHgap(10);
        topGrid.getChildren().addAll(homeButton, searchButton, LoanButton, LogoutButton, dueButton, addButton, updateButton);
        borderPane.setTop(topGrid);
    }
}