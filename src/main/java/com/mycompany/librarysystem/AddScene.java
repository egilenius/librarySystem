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

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class AddScene extends Scene {
    User user;
    private int loanability = 1;

    public AddScene(Stage stage, User user) {
        super(new BorderPane(), 600, 400);
        this.user = user;

        // create main layout
        BorderPane borderPane = (BorderPane) this.getRoot();
        HomeScene homeScene = new HomeScene(stage, user);
        Node buttons = homeScene.getButtons(stage);
        borderPane.setTop(buttons);

        // create form
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setVgap(10);
        form.setHgap(10);

        // Title
        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();
        form.add(titleLabel, 0, 1);
        form.add(titleField, 1, 1);

        // Publisher
        Label publisherLabel = new Label("Publisher:");
        TextField publisherField = new TextField();
        form.add(publisherLabel, 0, 2);
        form.add(publisherField, 1, 2);

        // Location
        Label locationLabel = new Label("Location:");
        TextField locationField = new TextField();
        form.add(locationLabel, 0, 3);
        form.add(locationField, 1, 3);

        // Type
        ObservableList<String> options = FXCollections.observableArrayList(
                "Course literature", "Other literature", "Magazine", "DVD");
        ChoiceBox<String> typeBox = new ChoiceBox<>(options);
        typeBox.getSelectionModel().selectFirst();
        form.add(new Label("Choose type:"), 0, 4);
        form.add(typeBox, 1, 4);

        // Create a map to map options to integer values
        Map<String, Integer> optionMap = new HashMap<>();
        optionMap.put("Course literature", 1);
        optionMap.put("Other literature", 2);
        optionMap.put("Magazine", 3);
        optionMap.put("DVD", 4);

        // ISBN
        Label isbnLabel = new Label("ISBN:");
        TextField isbnField = new TextField();
        form.add(isbnLabel, 0, 5);
        form.add(isbnField, 1, 5);

        // Loanability
        Label loanabilityLabel = new Label("Loanable:");
        ToggleButton loanabilityButton = new ToggleButton("True");
        loanabilityButton.selectedProperty().addListener((obs, oldVal, newVal) -> {
            loanabilityButton.setText(newVal ? "True" : "False");
        });
        loanabilityButton.setSelected(true); // Set the initial state of the button
        loanabilityButton.setOnAction(e -> toggleButtonHandler()); // Set the event handler for button clicks
        loanabilityButton.setMinWidth(50); // Set the minimum width of the button
        form.add(loanabilityLabel, 0, 6);
        form.add(loanabilityButton, 1, 6);

        // Add listener to the typeBox
        typeBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                // If course literature
                isbnField.setDisable(false);
                loanabilityButton.setDisable(false);
            } else if (newValue.intValue() == 1) {
                // if other literature
                isbnField.setDisable(false);
                loanabilityButton.setDisable(true);
                loanabilityButton.setSelected(true);
            } else if (newValue.intValue() == 2) {
                // if magazine
                isbnField.setDisable(true);
                loanabilityButton.setDisable(true);
                loanabilityButton.setSelected(true);
            }else{
                // if DVD
                isbnField.setDisable(true);
                loanabilityButton.setDisable(true);
                loanabilityButton.setSelected(true);
            }
        });

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            try {
                String selectedOption = typeBox.getSelectionModel().getSelectedItem();
                int selectedValue = optionMap.get(selectedOption);
            addItem(titleField.getText(), isbnField.getText(), publisherField.getText(), locationField.getText(), selectedValue);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        });

        // add form to layout
        borderPane.setCenter(form);
        borderPane.setRight(new VBox(addButton));
    }

    private void toggleButtonHandler() {
        if (getLoanability() == 3){
            setLoanability(1);
        } else{
            setLoanability(3);
        }
    }



    private void addItem(String title, String ISBN, String publisher, String location, int type) throws SQLException {
        String queryCheck = "SELECT * FROM item WHERE title = ? AND isbn = ? AND publisher = ? AND location = ? AND type = ?";
        String queryItem = "INSERT INTO public.item (itemid, title, isbn, publisher, location, type) VALUES (cast(? as uuid), ?, ?, ?, ?, ?)";
        String queryCheck2 = "SELECT itemid FROM item WHERE title = ? AND isbn = ? AND publisher = ? AND location = ? AND type = ?";
        String queryCopy = "INSERT INTO public.copy (copyid, itemid, availability, lastloan) VALUES (uuid_generate_v4(), cast(? as uuid), ?, null)";
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement psCheck = connection.prepareStatement(queryCheck);
             PreparedStatement psItem = connection.prepareStatement(queryItem);
             PreparedStatement psCheck2 = connection.prepareStatement(queryCheck2);
             PreparedStatement psCopy = connection.prepareStatement(queryCopy)) {

            //Start transaction
            connection.setAutoCommit(false);

            // add typeField input to check
            psCheck.setString(1, title);
            psCheck.setString(2, ISBN);
            psCheck.setString(3, publisher);
            psCheck.setString(4, location);
            psCheck.setInt(5, type);
            // execute queryCheck statement.
            ResultSet resultSet = psCheck.executeQuery();

            // if item is inexistent, create item.
            if (!resultSet.next()){
                psItem.setString(1, uuidString);
                psItem.setString(2, title);
                psItem.setString(3, ISBN);
                psItem.setString(4, publisher);
                psItem.setString(5, location);
                psItem.setInt(6, type);
                // execute item creation statement.
                psItem.executeUpdate();
                // TODO check why can't insert like this.
                // Retrieve the newly generated itemid
                psCheck2.setString(1, title);
                psCheck2.setString(2, ISBN);
                psCheck2.setString(3, publisher);
                psCheck2.setString(4, location);
                psCheck2.setInt(5, type);
                ResultSet itemResultSet = psCheck2.executeQuery();
                if (itemResultSet.next()) {
                    String itemid = itemResultSet.getString("itemid");
                    psCopy.setString(1, itemid);
                    psCopy.setInt(2, getLoanability());
                    psCopy.executeUpdate();
                    } // TODO add else clause?

            } else {
                // retrieve itemid and use as fk for new copy
                String itemid = resultSet.getString("itemid");
                psCopy.setString(1, itemid);
                psCopy.setInt(2, getLoanability());
                psCopy.executeUpdate();
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Item added!");
            alert.showAndWait();

            connection.commit(); // Commit the transaction

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getLoanability() {
        return loanability;
    }
    public void setLoanability(int loanability) {
        this.loanability = loanability;
    }
}