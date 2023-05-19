package com.mycompany.librarysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;

public class UpdateScene extends Scene {
    private HashMap<String, UUID> itemMap = new HashMap<>();


    User user;

    public UpdateScene(Stage stage, User user) {
        super(new BorderPane(), 1000, 500);
        this.user = user;
        BorderPane borderPane = (BorderPane) this.getRoot();
        HomeScene homeScene = new HomeScene(stage, user);
        Node buttons = homeScene.getButtons(stage);
        borderPane.setTop(buttons);

        ArrayList<Button> updateButtons = new ArrayList<>();

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

        // TODO connect to database
        // delete item
        Button deleteButton = new Button("Delete");
        GridPane.setConstraints(deleteButton, 2, 7);
        deleteButton.setOnAction(e -> {
            ObservableList<String> selectedItems = resultList.getSelectionModel().getSelectedItems();
            if (!selectedItems.isEmpty()) {
                // Display confirmation dialog
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText("Delete Confirmation");
                alert.setContentText("Do you want to delete just the selected copy or the entire item and all its copies?");

                ButtonType deleteCopyButton = new ButtonType("Delete Selected Copy");
                ButtonType deleteItemButton = new ButtonType("Delete Entire Item");

                alert.getButtonTypes().setAll(deleteCopyButton, deleteItemButton, ButtonType.CANCEL);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == deleteCopyButton) {
                    // Delete selected copy
                    deleteSelectedCopy(selectedItems);
                } else if (result.isPresent() && result.get() == deleteItemButton) {
                    // Delete entire item and all its copies
                    deleteEntireItem(selectedItems);
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
                String sql = "SELECT item.itemid, item.title, item.isbn, item.publisher, item.location, item.type, copy.availability " +
                        "FROM public.item " +
                        "JOIN public.copy ON item.itemid = copy.itemid " +
                        "WHERE LOWER(item.title) LIKE '%" + query + "%' " +
                        "   OR item.isbn LIKE '%" + query + "%' " +
                        "   OR item.publisher LIKE '%" + query + "%' " +
                        "   OR item.location LIKE '%" + query + "%' " +
                        "GROUP BY item.itemid, copy.availability";
                ResultSet rs = stmt.executeQuery(sql);

                // Retrieve attribute names from ResultSet
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                String[] attributeNames = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    attributeNames[i] = metaData.getColumnName(i + 1);
                }

                // Find the index of the "itemid" attribute
                int itemIdIndex = -1;
                for (int i = 0; i < columnCount; i++) {
                    if (attributeNames[i].equalsIgnoreCase("itemid")) {
                        itemIdIndex = i;
                        break;
                    }
                }

                // Display attribute names at the top of the ListView
                StringBuilder attributeNamesBuilder = new StringBuilder();
                for (int i = 0; i < columnCount; i++) {
                    if (i != itemIdIndex) {
                        attributeNamesBuilder.append(attributeNames[i]).append(" - ");
                    }
                }
                items.add(attributeNamesBuilder.toString().trim());

                // Retrieve attribute values and construct item strings
                while (rs.next()) {
                    StringBuilder itemStringBuilder = new StringBuilder();
                    for (int i = 0; i < columnCount; i++) {
                        if (i != itemIdIndex) {
                            itemStringBuilder.append(rs.getString(i + 1)).append(" - ");
                        }
                    }
                    String itemString = itemStringBuilder.toString().trim();
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


        Button updateButton = new Button("Update");
        GridPane.setConstraints(updateButton, 1, 7);
        /**
        // TODO connect to database
        // Update item
        // Define attributeNamesBuilder variable
        StringBuilder attributeNamesBuilder = new StringBuilder();
        updateButton.setOnAction(e -> {
            String selectedItem = resultList.getSelectionModel().getSelectedItem();
            if (selectedItem == null || selectedItem.equals(attributeNamesBuilder.toString().trim())) {
                // No item selected or selected item is the attribute names row
                return;
            }

            UUID itemId = itemMap.get(selectedItem);
            if (itemId == null) {
                // Item ID not found in the map
                return;
            }

            // Retrieve the item and copy details from the database
            try {
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                String sql = "SELECT item.title, item.isbn, item.publisher, item.location, item.type, copy.availability " +
                        "FROM public.item " +
                        "JOIN public.copy ON item.itemid = copy.itemid " +
                        "WHERE item.itemid = '" + itemId + "'";
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    // Retrieve the current values
                    String currentTitle = rs.getString("title");
                    String currentISBN = rs.getString("isbn");
                    String currentPublisher = rs.getString("publisher");
                    String currentLocation = rs.getString("location");
                    String currentType = rs.getString("type");
                    boolean currentAvailability = rs.getBoolean("availability");

                    // Create the update item form
                    GridPane updateItemForm = new GridPane();
                    updateItemForm.setVgap(10);
                    updateItemForm.setHgap(10);
                    updateItemForm.setPadding(new Insets(10));

                    TextField titleField = new TextField(currentTitle);
                    TextField isbnField = new TextField(currentISBN);
                    TextField publisherField = new TextField(currentPublisher);
                    TextField locationField = new TextField(currentLocation);
                    TextField typeField = new TextField(currentType);
                    CheckBox availabilityCheckBox = new CheckBox("Available");
                    availabilityCheckBox.setSelected(currentAvailability);

                    updateItemForm.add(new Label("Title:"), 0, 0);
                    updateItemForm.add(titleField, 1, 0);
                    updateItemForm.add(new Label("ISBN:"), 0, 1);
                    updateItemForm.add(isbnField, 1, 1);
                    updateItemForm.add(new Label("Publisher:"), 0, 2);
                    updateItemForm.add(publisherField, 1, 2);
                    updateItemForm.add(new Label("Location:"), 0, 3);
                    updateItemForm.add(locationField, 1, 3);
                    updateItemForm.add(new Label("Type:"), 0, 4);
                    updateItemForm.add(typeField, 1, 4);
                    updateItemForm.add(availabilityCheckBox, 0, 5, 2, 1);

                    // Create the dialog
                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.initOwner(stage);
                    dialog.setTitle("Update Item");
                    dialog.setHeaderText("Update the item details");
                    dialog.getDialogPane().setContent(updateItemForm);

                    // Add buttons to the dialog
                    ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

                    // Set the result converter for the dialog
                    dialog.setResultConverter(buttonType -> {
                        if (buttonType == updateButtonType) {
                            // Return the updated item details as a string array
                            return new String[]{
                                    titleField.getText(),
                                    isbnField.getText(),
                                    publisherField.getText(),
                                    locationField.getText(),
                                    typeField.getText(),
                                    String.valueOf(availabilityCheckBox.isSelected())
                            };
                        }
                        return null;
                    });

                    // Show the dialog and process the result
                    Optional<ButtonType> result = dialog.showAndWait();
                    result.ifPresent(buttonType -> {
                        if (buttonType == updateButtonType) {
                            // Retrieve the updated item details
                            String[] updatedItemDet ails = dialog.getResult();
                            if (updatedItemDetails != null) {
                                try {
                                    // Update the item details in the database
                                    Statement updateStmt = conn.createStatement();
                                    String updateSql = "UPDATE public.item " +
                                            "SET title = '" + updatedItemDetails[0] + "', " +
                                            "isbn = '" + updatedItemDetails[1] + "', " +
                                            "publisher = '" + updatedItemDetails[2] + "', " +
                                            "location = '" + updatedItemDetails[3] + "', " +
                                            "type = '" + updatedItemDetails[4] + "' " +
                                            "WHERE itemid = '" + itemId + "'";
                                    updateStmt.executeUpdate(updateSql);

                                    // Update the copy availability in the database
                                    Statement updateCopyStmt = conn.createStatement();
                                    String updateCopySql = "UPDATE public.copy " +
                                            "SET availability = " + Boolean.parseBoolean(updatedItemDetails[5]) + " " +
                                            "WHERE itemid = '" + itemId + "'";
                                    updateCopyStmt.executeUpdate(updateCopySql);

                                    // Refresh the search results
                                    searchButton.fire();

                                    updateStmt.close();
                                    updateCopyStmt.close();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    });
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
         */


        // Title
        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();
        Button titleUpdateButton = new Button("Update");
        updateButtons.add(titleUpdateButton);
        GridPane.setConstraints(titleLabel, 0, 0);
        GridPane.setConstraints(titleField, 1, 0);
        GridPane.setConstraints(titleUpdateButton, 2, 0);

// Publisher
        Label publisherLabel = new Label("Publisher:");
        TextField publisherField = new TextField();
        Button publisherUpdateButton = new Button("Update");
        updateButtons.add(publisherUpdateButton);
        GridPane.setConstraints(publisherLabel, 0, 1);
        GridPane.setConstraints(publisherField, 1, 1);
        GridPane.setConstraints(publisherUpdateButton, 2, 1);

// Location
        Label locationLabel = new Label("Location:");
        TextField locationField = new TextField();
        Button locationUpdateButton = new Button("Update");
        updateButtons.add(locationUpdateButton);
        GridPane.setConstraints(locationLabel, 0, 2);
        GridPane.setConstraints(locationField, 1, 2);
        GridPane.setConstraints(locationUpdateButton, 2, 2);

// Type
        ObservableList<String> options = FXCollections.observableArrayList(
                "Course literature", "Other literature", "Magazine", "DVD");
        ChoiceBox<String> typeBox = new ChoiceBox<>(options);
        typeBox.getSelectionModel().selectFirst();
        Label typeLabel = new Label("Choose a type:");
        Button typeUpdateButton = new Button("Update");
        updateButtons.add(typeUpdateButton);
        GridPane.setConstraints(typeLabel, 0, 3);
        GridPane.setConstraints(typeBox, 1, 3);
        GridPane.setConstraints(typeUpdateButton, 2, 3);

        // Create a map to map options to integer values
        Map<String, Integer> optionMap = new HashMap<>();
        optionMap.put("Course literature", 1);
        optionMap.put("Other literature", 2);
        optionMap.put("Magazine", 3);
        optionMap.put("DVD", 4);

        // ISBN
        Label isbnLabel = new Label("ISBN:");
        TextField isbnField = new TextField();
        Button isbnUpdateButton = new Button("Update");
        GridPane.setConstraints(isbnLabel, 0, 4);
        GridPane.setConstraints(isbnField, 1, 4);
        GridPane.setConstraints(isbnUpdateButton, 2, 4);

        // Loanability
        Label loanabilityLabel = new Label("Loanable:");
        ToggleButton loanabilityButton = new ToggleButton("True");
        loanabilityButton.selectedProperty().addListener((obs, oldVal, newVal) -> {
            loanabilityButton.setText(newVal ? "True" : "False");
        });
        Button loanUpdateButton = new Button("Update");

        loanabilityButton.setMinWidth(50); // Set the minimum width of the button
        GridPane.setConstraints(loanabilityLabel, 0, 5);
        GridPane.setConstraints(loanabilityButton, 1, 5);
        GridPane.setConstraints(loanUpdateButton, 2, 5);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(searchInput, searchButton, resultList);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 10, 10, 10));
        borderPane.setLeft(vbox);

        // Create a GridPane for the title and publisher labels and fields
        GridPane titlePublisherPane = new GridPane();
        titlePublisherPane.setHgap(10);
        titlePublisherPane.setVgap(10);
        titlePublisherPane.setPadding(new Insets(10));
        titlePublisherPane.getChildren().addAll(titleLabel, titleField, publisherLabel, publisherField,
                isbnLabel, isbnField, locationLabel, locationField, typeLabel, typeBox, loanabilityLabel, loanabilityButton,
        titleUpdateButton, isbnUpdateButton, publisherUpdateButton, locationUpdateButton, typeUpdateButton, loanUpdateButton);
        // Set the GridPane in the center of the BorderPane
        borderPane.setCenter(titlePublisherPane);
        borderPane.setBottom(new VBox(deleteButton, updateButton));

        resultList.setItems(items);

        stage.setTitle("SearchScene");
        stage.setScene(this);
    }

    private void deleteEntireItem(ObservableList<String> selectedItems) {
    }

    private void deleteSelectedCopy(ObservableList<String> selectedItems) {
    }
}