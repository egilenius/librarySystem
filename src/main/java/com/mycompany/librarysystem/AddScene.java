package com.mycompany.librarysystem;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class AddScene extends Scene {
    User user;
    private static final String DB_URL = "jdbc:postgresql://hattie.db.elephantsql.com:5432/oaehwzla";
    private static final String DB_USER = "oaehwzla";
    private static final String DB_PASSWORD = "aj3XjlSmghfN4LGUZE12mOfUTDaKXiJY";

    public AddScene(Stage stage, Scene startScene, User user) {
        super(new BorderPane(), 600, 400);
        this.user = user;
        // create main layout
        BorderPane borderPane = (BorderPane) this.getRoot();
        HomeScene homeScene = new HomeScene(stage, startScene, user);
        Node buttons = homeScene.getButtons(stage, startScene);
        borderPane.setTop(buttons);

        // create form
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setVgap(10);
        form.setHgap(10);

        /**
         // Item ID
         Label itemIdLabel = new Label("Item ID:");
         TextField itemIdField = new TextField();
         form.add(itemIdLabel, 0, 0);
         form.add(itemIdField, 1, 0); */

        // Title
        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();
        form.add(titleLabel, 0, 1);
        form.add(titleField, 1, 1);

        // ISBN
        Label isbnLabel = new Label("ISBN:");
        TextField isbnField = new TextField();
        form.add(isbnLabel, 0, 2);
        form.add(isbnField, 1, 2);

        // Publisher
        Label publisherLabel = new Label("Publisher:");
        TextField publisherField = new TextField();
        form.add(publisherLabel, 0, 3);
        form.add(publisherField, 1, 3);

        // Location
        Label locationLabel = new Label("Location:");
        TextField locationField = new TextField();
        form.add(locationLabel, 0, 4);
        form.add(locationField, 1, 4);

        // Type
        Label typeLabel = new Label("Type:");
        TextField typeField = new TextField();
        form.add(typeLabel, 0, 5);
        form.add(typeField, 1, 5);

        /**
         // Release Date
         Label releaseDateLabel = new Label("Release Date:");
         TextField releaseDateField = new TextField();
         form.add(releaseDateLabel, 0, 6);
         form.add(releaseDateField, 1, 6); */

        // create add button
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
                    try {

                        if (addItem(titleField.getText(), isbnField.getText(), publisherField.getText(), locationField.getText(), Integer.parseInt(typeField.getText()))) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Item added!");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Invalid input!");
                            alert.showAndWait();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                }
        );
        /**
         // create add button
         Button addButton = new Button("Add");
         addButton.setOnAction(event -> {
         try {
         // connect to database
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

         // prepare statement
         PreparedStatement stmt = conn.prepareStatement(
         "INSERT INTO library_items (title, isbn, publisher, physical_location, type) VALUES (?, ?, ?, ?, ?)");
         stmt.setString(1, titleField.getText());
         stmt.setString(2, isbnField.getText());
         stmt.setString(3, publisherField.getText());
         stmt.setString(4, locationField.getText());
         stmt.setString(5, typeField.getText());

         // execute statement
         int rowsAffected = stmt.executeUpdate();

         if (rowsAffected == 1) {
         // show success message and return to home scene
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("Success");
         alert.setHeaderText(null);
         alert.setContentText("Item added successfully.");
         alert.showAndWait();

         //stage.setScene(homeScene);
         } else {
         // show error message
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error");
         alert.setHeaderText(null);
         alert.setContentText("Failed to add item.");
         alert.showAndWait();
         }
         } catch (SQLException ex) {
         // show error message
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error");
         alert.setHeaderText(null);
         alert.setContentText("Failed to connect to database.");
         alert.showAndWait();
         }
         }
         );*/

        // add form to layout
        borderPane.setCenter(form);
        borderPane.setRight(new VBox(addButton));
    }

    private boolean addItem(String title, String ISBN, String publisher, String location, int type) throws SQLException {
       //String query = "INSERT INTO public.item (itemid, title, isbn, publisher, location, type) VALUES (?, ?, ?, ?, ?, ?)";
       String query = "INSERT INTO public.item (itemid, title, isbn, publisher, location, type) VALUES (8c5fef02-46bb-4c3c-9938-44a188a447ba, 'Bibeln', '9780141182612', 'Jesus', 'GUD', 8)";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("PostgreSQL JDBC driver not found");
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {


            preparedStatement.setString(1, "uuid_generate_v4()");
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, ISBN);
            preparedStatement.setString(4, publisher);
            preparedStatement.setString(5, location);
            preparedStatement.setString(6, String.valueOf(type));


            ResultSet resultSet = preparedStatement.executeQuery();

        }
        return true;
    }

}


/**
 // create form fields
 Label physicalLocationLabel = new Label("Physical Location:");
 TextField physicalLocationField = new TextField();

 Label authorDirectorLabel = new Label("Author/Director:");
 TextField authorDirectorField = new TextField();

 Label isbnLabel = new Label("ISBN:");
 TextField isbnField = new TextField();

 Label classificationLabel = new Label("Classification:");
 TextField classificationField = new TextField();

 Label genreLabel = new Label("Genre:");
 TextField genreField = new TextField();

 Label ageRatingLabel = new Label("Age Rating:");
 TextField ageRatingField = new TextField();

 Label productionCountryLabel = new Label("Production Country:");
 TextField productionCountryField = new TextField();

 Label actorsLabel = new Label("Actors:");
 TextField actorsField = new TextField();

 // add form fields to form
 form.add(physicalLocationLabel, 0, 0);
 form.add(physicalLocationField, 1, 0);

 form.add(authorDirectorLabel, 0, 1);
 form.add(authorDirectorField, 1, 1);

 form.add(isbnLabel, 0, 2);
 form.add(isbnField, 1, 2);

 form.add(classificationLabel, 0, 3);
 form.add(classificationField, 1, 3);

 form.add(genreLabel, 0, 4);
 form.add(genreField, 1, 4);

 form.add(ageRatingLabel, 0, 5);
 form.add(ageRatingField, 1, 5);

 form.add(productionCountryLabel, 0, 6);
 form.add(productionCountryField, 1, 6);

 form.add(actorsLabel, 0, 7);
 form.add(actorsField, 1, 7);
 */