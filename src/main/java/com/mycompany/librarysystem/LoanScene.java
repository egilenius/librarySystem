package com.mycompany.librarysystem;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.time.LocalDate;

public class LoanScene extends Scene {
    private Scene startScene;
    User user;

    //ArrayList<Loan> bookData = new ArrayList<>();
    Loan loan = new Loan("1", "true", LocalDate.of(2023, 05, 10));
    //bookData.add(loan);


    public LoanScene(Stage stage, Scene startScene, User user) {
        super(new BorderPane(), 700, 300);
        this.user = user;
        BorderPane borderPane = (BorderPane) this.getRoot();
        this.startScene = startScene;
        HomeScene homeScene = new HomeScene(stage, startScene, user);
        Node buttons = homeScene.getButtons(stage, startScene);
        borderPane.setTop(buttons);

        // Create main grid pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Create four ListView components
        ListView<String> listOverdue = new ListView<>();
        ListView<String> listCurrentLoans = new ListView<>();
        ListView<String> listFinishedLoans = new ListView<>();

        // Create labels for each list
        Label labelOverdue = new Label("Overdue");
        Label label2 = new Label("Loans");
        Label label3 = new Label("Finished");

        // Add labels and lists
        grid.add(labelOverdue, 0, 0);
        grid.add(listOverdue, 1, 0);
        grid.add(label2, 0, 1);
        grid.add(listCurrentLoans, 1, 1);
        grid.add(label3, 0, 2);
        grid.add(listFinishedLoans, 1, 2);

        // Add grid to center of BorderPane
        borderPane.setCenter(grid);

        LocalDate today = LocalDate.now();
        // show books
        // TODO connect to database
        for (String[] book : user.bookData) {
            String title = book[0];
            String returned = book[1];
            LocalDate returnDate = LocalDate.parse(book[2]);

            if (returned.equals("yes")) {
                // Title is finished
                listFinishedLoans.getItems().add(title);
            } else if (returnDate.isAfter(today)) {
                // Title is current loan
                listCurrentLoans.getItems().add(title);
            } else {
                // Title is overdue
                listOverdue.getItems().add(title);
            }
        }

        // Show scene
        stage.setScene(this);
        stage.setTitle("LoanScene");
        stage.show();

    }

}