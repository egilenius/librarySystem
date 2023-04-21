package com.mycompany.librarysystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HomeScreen extends Scene {

    public HomeScreen(Stage stage, Scene startScene) {
        super(new BorderPane(), 400, 300);
        BorderPane borderPane = (BorderPane) this.getRoot();

        // Create menu bar
        MenuBar menuBar = new MenuBar();

// Create menu
        Menu menu = new Menu("Menu");

// Create menu items
        MenuItem searchItem = new MenuItem("Search");
        MenuItem loanItem = new MenuItem("Loans");
        MenuItem logoutItem = new MenuItem("Logout");
        menu.getItems().addAll(searchItem, loanItem, logoutItem);
        menuBar.getMenus().addAll(menu);
        borderPane.setTop(menuBar);
        borderPane.setTop(menuBar);

        // Create main grid pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Add grid to center of BorderPane
        borderPane.setCenter(grid);

        // Show scene
        stage.setTitle("HomeScreen");
        stage.setScene(this);
        stage.show();

        // Add button functionality
        searchItem.setOnAction(e -> {
            SearchScene searchScene = new SearchScene(stage, startScene);
            stage.setScene(searchScene);
        });
        loanItem.setOnAction(e -> {
            LoanScene loanScene = new LoanScene(stage, startScene);
            stage.setScene(loanScene);
        });
    }
}