package com.mycompany.librarysystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoanScene extends Scene {

    //ArrayList<Loan> bookData = new ArrayList<>();
    Loan loan = new Loan("1", "true", LocalDate.of(2023, 05, 10));
    //bookData.add(loan);

    /**
    bookData.add(Arrays.asList("The Great Gatsby", "no", "2023-05-10")); //loan
    bookData.add(Arrays.asList("To Kill a Mockingbird", "no", "2023-05-12")); //loan
    bookData.add(Arrays.asList("Pride and Prejudice", "yes", "2023-04-20")); //finished
    bookData.add(Arrays.asList("1984", "no", "2023-05-15")); //loan
    bookData.add(Arrays.asList("The Catcher in the Rye", "yes", "2023-04-22")); //finished
    bookData.add(Arrays.asList("Animal Farm", "yes", "2023-04-25")); // finished
    bookData.add(Arrays.asList("Brave New World", "no", "2023-03-20")); //overdue
    bookData.add(Arrays.asList("The Hobbit", "no", "2023-03-08")); //overdue
    bookData.add(Arrays.asList("Lord of the Flies", "yes", "2023-04-24")); // finished
    bookData.add(Arrays.asList("The Da Vinci Code", "no", "2023-05-18")); //loan
     */


    String[][] bookData = {
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


    public LoanScene(Stage stage, Scene startScene) {
        super(new BorderPane(), 400, 300);
        BorderPane borderPane = (BorderPane) this.getRoot();

        // Create menu bar
        MenuBar menuBar = new MenuBar();

        // Create menu
        Menu menu = new Menu("Menu");

        // Create menu items
        MenuItem searchItem = new MenuItem("Search");
        MenuItem loanItem = new MenuItem("Loans");
        MenuItem logoutItem = new MenuItem("Logout");
        menu.getItems().addAll(searchItem, loanItem, logoutItem);
        menuBar.getMenus().addAll(menu);
        borderPane.setTop(menuBar);
        borderPane.setTop(menuBar);

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
        for (String[] book : bookData) {
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

        // Add button functionality
        searchItem.setOnAction(e -> {
            SearchScene searchScene = new SearchScene(stage, startScene);
            stage.setScene(searchScene);
        });
    }

}