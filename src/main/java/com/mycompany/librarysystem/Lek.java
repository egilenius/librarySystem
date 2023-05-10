package com.mycompany.librarysystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.sql.*;

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

                        if (addItem()) {
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
        gridPane.getChildren().add(addButton);
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean addItem() throws SQLException {
        //String query = "INSERT INTO public.item (itemid, title, isbn, publisher, location, type) VALUES (?, ?, ?, ?, ?, ?)";
        String query = "INSERT INTO public.item (itemid, title, isbn, publisher, location, type) VALUES (8c5fef02-46bb-4c3c-9938-44a188a447ba, 'Bibeln', '9780141182612', 'Jesus', 'GUD', 8)";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("PostgreSQL JDBC driver not found");
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             // create a Statement from the connection
             Statement statement = conn.createStatement();
            // insert the data
             statement.executeUpdate("INSERT INTO Customers " + "VALUES (1001, 'Simpson', 'Mr.', 'Springfield', 2001)");

             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.executeQuery();
            //ResultSet resultSet = preparedStatement.executeQuery();

        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }


        /**
        @Override
        public void start(Stage primaryStage) {

            // Play song
            Media media = new Media(new File("C:\\Users\\Sak i Mark\\Documents\\NetBeansProjects\\librarySystem\\pizzaSong.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();


            Label label = new Label("Help!");
            label.setOnMouseClicked(mouseEvent->{System.out.println("Hello World!");});
            Menu menu = new Menu("", label);
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().add(menu);


            StackPane root = new StackPane();
            root.getChildren().add(menuBar);

            Scene scene = new Scene(root, 300, 250);

            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    */
}
