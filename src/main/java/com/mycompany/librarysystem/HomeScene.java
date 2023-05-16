package com.mycompany.librarysystem;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HomeScene extends Scene {
    User user;

    public HomeScene(Stage stage, Scene startScene, User user) {
        super(new BorderPane(), 400, 300);
        this.user = user;
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
            Scene homeScene = new HomeScene(stage, startScene, user);
            stage.setScene(homeScene);
        });
        //Search
        Button searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 1, 0);
        searchButton.setOnAction(e -> {
            Scene searchScene = new SearchScene(stage, startScene,user);
            stage.setScene(searchScene);
        });
        //Loans
        Button LoanButton = new Button("Loans");
        GridPane.setConstraints(LoanButton, 2, 0);
        LoanButton.setOnAction(e -> {
            Scene LoanScene = new LoanScene(stage, startScene, user);
            stage.setScene(LoanScene);
        });

        //Create librarian buttons
        //Due items button
        Button dueButton = new Button("Due items");
        GridPane.setConstraints(dueButton, 0, 1);
        dueButton.setOnAction(e -> {
            Scene dueScene = new DueScene(stage, startScene, user);
            stage.setScene(dueScene);
        });
        //add item button
        Button addButton = new Button("Add item");
        GridPane.setConstraints(addButton, 1, 1);
        addButton.setOnAction(e -> {
            Scene addScene = new AddScene(stage, startScene, user);
            stage.setScene(addScene);
        });

        //change item button
        Button updateButton = new Button("Update item");
        GridPane.setConstraints(updateButton, 2, 1);
        updateButton.setOnAction(e -> {
            Scene updateScene = new UpdateScene(stage, startScene, user);
            stage.setScene(updateScene);
        });

        //Logout
        Button LogoutButton;
        if (user != null) {
            LogoutButton = new Button("Logout (" + user.getUsername() + ")");
        } else {
            LogoutButton = new Button("Not logged in");
            // Disable features that require a logged-in user
            /*
            LoanButton.setDisable(true);
            dueButton.setDisable(true);
            addButton.setDisable(true);
            updateButton.setDisable(true);
            */
        }
        GridPane.setConstraints(LogoutButton, 3, 0);
        LogoutButton.setOnAction(e -> {
            stage.setScene(startScene);
        });

        // Add buttons to top of BorderPane
        GridPane topGrid = new GridPane();
        topGrid.setPadding(new Insets(10, 10, 10, 10));
        topGrid.setHgap(10);
        topGrid.getChildren().addAll(homeButton, searchButton, LoanButton, LogoutButton, dueButton, addButton, updateButton);
        borderPane.setTop(topGrid);
        return borderPane;
    }
    /**
    private void addButtonsToTop(BorderPane borderPane, Stage stage, Scene startScene) {
        //User buttons
        //Home
        Button homeButton = new Button("Home");
        GridPane.setConstraints(homeButton, 0, 0);
        homeButton.setOnAction(e -> {
            Scene homeScene = new HomeScene(stage, startScene, user);
            stage.setScene(homeScene);
        });
        //Search
        Button searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 1, 0);
        searchButton.setOnAction(e -> {
            Scene searchScene = new SearchScene(stage, startScene, user);
            stage.setScene(searchScene);
        });
        //Loans
        Button LoanButton = new Button("Loans");
        GridPane.setConstraints(LoanButton, 2, 0);
        LoanButton.setOnAction(e -> {
            Scene LoanScene = new LoanScene(stage, startScene, user);
            stage.setScene(LoanScene);
        });
        //Logout
        Button LogoutButton = new Button("Logout(" + user.getUsername()+")");
        GridPane.setConstraints(LogoutButton, 3, 0);
        LogoutButton.setOnAction(e -> {
            stage.setScene(startScene);
        });
        //Create librarian buttons
        //Due items button
        Button dueButton = new Button("Due items");
        GridPane.setConstraints(dueButton, 0, 1);
        dueButton.setOnAction(e -> {
            Scene dueScene = new DueScene(stage, startScene, user);
            stage.setScene(dueScene);
        });
        //add item button
        Button addButton = new Button("Add item");
        GridPane.setConstraints(addButton, 1, 1);
        addButton.setOnAction(e -> {
            Scene addScene = new AddScene(stage, startScene, user);
            stage.setScene(addScene);
        });

        //change item button
        Button updateButton = new Button("Update item");
        GridPane.setConstraints(updateButton, 2, 1);
        updateButton.setOnAction(e -> {
            Scene updateScene = new UpdateScene(stage, startScene, user);
            stage.setScene(updateScene);
        });

        // Add buttons to top of BorderPane
        GridPane topGrid = new GridPane();
        topGrid.setPadding(new Insets(10, 10, 10, 10));
        topGrid.setHgap(10);
        topGrid.getChildren().addAll(homeButton, searchButton, LoanButton, LogoutButton, dueButton, addButton, updateButton);
        borderPane.setTop(topGrid);
    }
     */
}