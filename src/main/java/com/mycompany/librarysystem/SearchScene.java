package com.mycompany.librarysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class SearchScene extends Scene {
    private Scene startScene;
    User user;

    public SearchScene(Stage stage, Scene startScene, User user) {

        super(new BorderPane(), 700, 300);
        this.user = user;
        BorderPane borderPane = (BorderPane) this.getRoot();
        this.startScene = startScene;
        HomeScene homeScene = new HomeScene(stage, startScene, user);
        Node buttons = homeScene.getButtons(stage, startScene);
        borderPane.setTop(buttons);

        // Search bar
        TextField searchInput = new TextField();
        searchInput.setPromptText("Search");
        GridPane.setConstraints(searchInput, 0, 3);

        // Result list
        ListView<String> resultList = new ListView<>();
        resultList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        resultList.setPrefHeight(300);
        resultList.setPrefWidth(400);
        ObservableList<String> items = FXCollections.observableArrayList();
        GridPane.setConstraints(resultList, 0, 7);

        // Borrow button
        Button borrowButton = new Button("Borrow");
        GridPane.setConstraints(borrowButton, 1, 7);
        borrowButton.setOnAction(e -> {
            ObservableList<String> selectedItems = resultList.getSelectionModel().getSelectedItems();

            if (selectedItems.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select at least one book to borrow.");
                alert.showAndWait();
            } else {
                // Similar to the original code...
            }
        });

        // Search button
        Button searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 1, 3);
        searchButton.setOnAction(e -> {
            String query = searchInput.getText().toLowerCase();
            items.clear();
            try {
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                String sql = "SELECT title, isbn FROM item WHERE LOWER(title) LIKE '%" + query + "%' OR isbn LIKE '%" + query + "%'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    items.add(rs.getString("title") + " - " + rs.getString("isbn"));
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            resultList.setItems(items);
        });

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 10, 10, 10));
        gridPane.add(searchInput, 0, 0);
        gridPane.add(searchButton, 1, 0);
        gridPane.add(resultList, 0, 1, 2, 1);
        gridPane.add(borrowButton, 1, 2);

        // Add the GridPane to the center of the BorderPane
        borderPane.setCenter(gridPane);

        stage.setTitle("SearchScene");
        stage.setScene(this);

        // Setting up the List View
        resultList.setItems(items);

        stage.setTitle("SearchScene");
        stage.setScene(this);
    }
}

