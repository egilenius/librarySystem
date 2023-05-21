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
    private String itemid2;

    private String copyid2;


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
        resultList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        resultList.setPrefHeight(300);
        resultList.setPrefWidth(400);
        ObservableList<String> items = FXCollections.observableArrayList();
        GridPane.setConstraints(resultList, 0, 7);

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
                    for (String selectedItem : selectedItems) {
                        String[] parts = selectedItem.split(" - ");
                        if (parts.length < 1) {
                            // Invalid selected item format
                            continue;
                        }
                        String copyId = parts[parts.length - 1]; // Assuming copyid is the last part of the string
                        if (copyId.length() >= 2) {
                            copyId = copyId.substring(0, copyId.length() - 2);
                        }
                        // Use the copyId to delete the selected copy
                        deleteCopy(UUID.fromString(copyId));
                    }
                } else if (result.isPresent() && result.get() == deleteItemButton) {
                    // Delete entire item and all its copies
                    for (String selectedItem : selectedItems) {
                        UUID itemId = itemMap.get(selectedItem);
                        if (itemId != null) {
                            // Use the itemId to delete the entire item and its copies
                            deleteItem(itemId);
                        }
                    }
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
                String sql = "SELECT item.itemid, item.title, item.isbn, item.publisher, item.location, item.type, copy.availability, copy.copyid " +
                        "FROM public.item " +
                        "JOIN public.copy ON item.itemid = copy.itemid " +
                        "WHERE LOWER(item.title) LIKE '%" + query + "%' " +
                        "   OR item.isbn LIKE '%" + query + "%' " +
                        "   OR item.publisher LIKE '%" + query + "%' " +
                        "   OR item.location LIKE '%" + query + "%' " +
                        "GROUP BY item.itemid, copy.availability, copy.copyid";
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

        Button editButton = new Button("Edit");
        GridPane.setConstraints(editButton, 1, 7);
        editButton.setOnAction(e -> {
            // Define attributeNamesBuilder variable
            StringBuilder attributeNamesBuilder = new StringBuilder();

            String selectedItem = resultList.getSelectionModel().getSelectedItem();
            //System.out.println(selectedItem);
            if (selectedItem == null || selectedItem.equals(attributeNamesBuilder.toString().trim())) {
                // No item selected or selected item is the attribute names row
                return;
            }

            UUID itemId = itemMap.get(selectedItem);
            if (itemId == null) {
                // Item ID not found in the map
                return;
            }

            // Extract copyid from selectedItem string
            String[] parts = selectedItem.split(" - ");
            if (parts.length < 1) {
                // Invalid selected item format
                return;
            }
            String copyId = parts[parts.length - 1]; // Assuming copyid is the last part of the string
            if (copyId.length() >= 2) {
                copyId = copyId.substring(0, copyId.length() - 2);
            }

            // Retrieve the item and copy details from the database
            try {
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                String sql = "SELECT item.itemid, item.title, item.isbn, item.publisher, item.location, item.type, copy.copyid, copy.availability " +
                        "FROM public.item " +
                        "JOIN public.copy ON item.itemid = copy.itemid " +
                        "WHERE item.itemid = '" + itemId + "'" +
                        "AND copy.copyid = '" + copyId + "'";
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    // Retrieve the current values
                    String currentTitle = rs.getString("title");
                    String currentISBN = rs.getString("isbn");
                    String currentPublisher = rs.getString("publisher");
                    String currentLocation = rs.getString("location");
                    String currentType = rs.getString("type");
                    int currentAvailability = rs.getInt("availability");

                    // Update the text fields with the current item details
                    titleField.setText(currentTitle);
                    isbnField.setText(currentISBN);
                    publisherField.setText(currentPublisher);
                    locationField.setText(currentLocation);
                    typeBox.getSelectionModel().select(currentType);
                    //loanabilityButton.setSelected(currentAvailability);

                    // Update the loanability button based on availability
                    // TODO Bug if you chose a borrowed item and try to edit...?
                    if (currentAvailability == 1) {
                        loanabilityButton.setText("True");
                        loanabilityButton.setSelected(true);
                    } else if (currentAvailability == 3) {
                        loanabilityButton.setText("False");
                        loanabilityButton.setSelected(false);
                    }

                    // Assign the value of itemid to itemid2
                    itemid2 = rs.getString("itemid");
                    copyid2 = rs.getString("copyid");

                    rs.close();
                    stmt.close();
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // give updateButtons functionality
        titleUpdateButton.setOnAction(e -> {
            String newTitle = titleField.getText();
            if (newTitle.isEmpty()) {
                showAlert("Title cannot be empty.");
                return;
            }
            updateItemAttribute("title", newTitle, UUID.fromString(getItemid2()));
        });
        // ISBN
        isbnUpdateButton.setOnAction(e -> {
            String newISBN = isbnField.getText();
            if (newISBN.isEmpty()) {
                showAlert("ISBN cannot be empty.");
                return;
            }
            updateItemAttribute("isbn", newISBN, UUID.fromString(getItemid2()));
        });
        // Publisher
        publisherUpdateButton.setOnAction(e -> {
            String newPublisher = publisherField.getText();
            if (newPublisher.isEmpty()) {
                showAlert("Publisher cannot be empty.");
                return;
            }
            updateItemAttribute("publisher", newPublisher, UUID.fromString(getItemid2()));
        });
        // Location
        locationUpdateButton.setOnAction(e -> {
            String newLocation = locationField.getText();
            if (newLocation.isEmpty()) {
                showAlert("Location cannot be empty.");
                return;
            }
            updateItemAttribute("location", newLocation, UUID.fromString(getItemid2()));
        });
        // Type
        typeUpdateButton.setOnAction(e -> {
            String newType = typeBox.getValue();
            if (newType == null) {
                showAlert("Please choose a type.");
                return;
            }
            int intType = optionMap.get(newType);
            System.out.println(intType); // Output: 1
            updateItemAttribute("type", Integer.toString(intType), UUID.fromString(getItemid2()));
        });
        //loanability
        loanUpdateButton.setOnAction(e -> {
            int newAvailability = loanabilityButton.isSelected() ? 1 : 3;
            updateItemAttribute("availability", String.valueOf(newAvailability), UUID.fromString(getCopyid2()));
        });


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
        borderPane.setBottom(new VBox(deleteButton, editButton));

        resultList.setItems(items);

        stage.setTitle("SearchScene");
        stage.setScene(this);
    }

    // Helper method to update item attribute in the database
    private void updateItemAttribute(String attributeName, String attributeValue, UUID itemId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            String sql;
            if (attributeName == "availability") {
                sql = "UPDATE public.copy " +
                        "SET " + attributeName + " = '" + attributeValue + "' " +
                        "WHERE itemid = '" + getItemid2() + "' " +
                        "AND copyid = '" + getCopyid2() + "'";
            } else {
                sql = "UPDATE public.item " +
                        "SET " + attributeName + " = '" + attributeValue + "' " +
                        "WHERE itemid = '" + itemId + "'";
            }

            stmt.executeUpdate(sql);
            showAlert("Attribute updated successfully!");
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error updating attribute.");
        }
    }

    // Helper method to display an alert dialog
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void deleteItem(UUID itemId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM public.copy WHERE itemid = '" + itemId + "';" +
                    "DELETE FROM public.item WHERE itemid = '" + itemId + "'";
            stmt.executeUpdate(sql);
            showAlert("Copy deleted successfully!");
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error deleting copy.");
        }

    }

    private void deleteCopy(UUID copyid) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM public.copy WHERE copyid = '" + copyid + "'";
            stmt.executeUpdate(sql);
            showAlert("Copy deleted successfully!");
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error deleting copy.");
        }
    }

    public String getItemid2() {
        return itemid2;
    }

    public String getCopyid2() {
        return copyid2;
    }

}