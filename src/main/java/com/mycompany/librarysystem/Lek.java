package com.mycompany.librarysystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.sql.*;
import java.util.UUID;

/*
Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Item added!");
            alert.showAndWait();
 */

public class Lek extends Application {

    private static final String DB_URL = "jdbc:postgresql://hattie.db.elephantsql.com:5432/oaehwzla";
    private static final String DB_USER = "oaehwzla";
    private static final String DB_PASSWORD = "aj3XjlSmghfN4LGUZE12mOfUTDaKXiJY";

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Add Item");


        // Create layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
                    try {
                        doThing();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                }
        );
        gridPane.getChildren().add(addButton);
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void doThing() throws  SQLException {
        String query = "select * from item where title = 'Bibeln' and isbn = '9780141182612' and publisher = 'Jesus' and location = 'GUD' and type = 6";
        UUID uuid = UUID.randomUUID();

        System.out.println(uuid);
        System.out.println(uuid);
        System.out.println(uuid);
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             // add typeField input.
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            /*
             preparedStatement.setString(1, title);
             preparedStatement.setString(2, ISBN);
             preparedStatement.setString(3, publisher);
             preparedStatement.setString(4, location);
             preparedStatement.setInt(5, type);
             */

            // execute statement.
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                System.out.println("Has next.");
            } else {
                System.out.println("Empty.");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
