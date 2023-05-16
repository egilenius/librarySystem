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

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class SearchScene extends Scene {
    private Scene startScene;
    private HashMap<String, UUID> itemMap = new HashMap<>();
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
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    String sql = "INSERT INTO public.loan (userid, itemid, dateborrowed, datedue) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);

                    for (String item : selectedItems) {
                        // Get the UUID from itemMap
                        UUID itemId = itemMap.get(item);

                        // Current date
                        java.sql.Date dateBorrowed = java.sql.Date.valueOf(LocalDate.now());

                        // Due date is 14 days from the current date
                        java.sql.Date dateDue = java.sql.Date.valueOf(LocalDate.now().plusDays(14));

                        pstmt.setObject(1, user.getUserid());
                        pstmt.setObject(2, itemId);
                        pstmt.setDate(3, dateBorrowed);
                        pstmt.setDate(4, dateDue);

                        pstmt.addBatch();
                    }

                    pstmt.executeBatch();
                    pstmt.close();
                    conn.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("You have successfully borrowed the selected book(s).");
                    alert.showAndWait();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Database Error");
                    alert.setHeaderText(null);
                    alert.setContentText("There was an error while trying to borrow the book(s). Please try again.");
                    alert.showAndWait();
                }
            }
        });




        // Search button
        Button searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 1, 3);
        searchButton.setOnAction(e -> {
            String query = searchInput.getText().toLowerCase();
            items.clear();
            itemMap.clear(); // Clear the map at the beginning of each search
            try {
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                String sql = "SELECT item.itemid, item.title, item.isbn, COUNT(copy.itemid) AS total_copies, COUNT(copy.availability = 1 OR NULL) AS available_copies FROM item LEFT JOIN copy ON item.itemid = copy.itemid WHERE LOWER(item.title) LIKE '%" + query + "%' OR item.isbn LIKE '%" + query + "%' GROUP BY item.itemid";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String itemString = rs.getString("title") + " - " + rs.getString("isbn") + " - Total Copies: " + rs.getInt("total_copies") + ", Available Copies: " + rs.getInt("available_copies");
                    UUID itemId = UUID.fromString(rs.getString("itemid"));
                    itemMap.put(itemString, itemId); // Map the item string to its UUID
                    items.add(itemString);
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

