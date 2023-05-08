package com.mycompany.librarysystem;

import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DueScene extends Scene {

    private String[][] bookData = {
            {"The Great Gatsby", "no", "2023-05-10"}, //loan
            {"To Kill a Mockingbird", "no", "2023-05-12"}, //loan
            {"Pride and Prejudice", "yes", "2023-04-20"}, //finished
            {"1984", "no", "2023-05-15"}, //loan
            {"The Catcher in the Rye", "yes", "2023-04-22"}, //finished
            {"Animal Farm", "yes", "2023-04-25"}, // finished
            {"Brave New World", "no", "2023-03-20"}, //overdue
            {"The Hobbit", "no", "2023-03-08"}, //overdue
            {"Lord of the Flies", "yes", "2023-04-24"}, // finished
            {"The Da Vinci Code", "no", "2023-05-18"} //loan
    };

    public DueScene(Stage stage, Scene startScene) {
        super(new BorderPane(), 400, 300);
        BorderPane borderPane = (BorderPane) this.getRoot();

        HomeScene homeScene = new HomeScene(stage, startScene);
        Node buttons = homeScene.getButtons(stage, startScene);
        borderPane.setTop(buttons);


        // Create main grid pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Create ListView component
        ListView<String> listDue = new ListView<>();

        // Create label for the list
        Label labelDue = new Label("Overdue");

        // Add label and list to grid
        grid.add(labelDue, 0, 0);
        grid.add(listDue, 1, 0);

        // Add grid to center of BorderPane
        borderPane.setCenter(grid);

        LocalDate today = LocalDate.now();

        // Show overdue books
        for (String[] book : bookData) {
            String title = book[0];
            String returned = book[1];
            LocalDate returnDate = LocalDate.parse(book[2]);

            if (!returned.equals("yes") && returnDate.isBefore(today)) {
                // Title is overdue
                listDue.getItems().add(title);
            }
        }

        // Show scene
        stage.setScene(this);
        stage.setTitle("DueScene");
        stage.show();

    }
}

