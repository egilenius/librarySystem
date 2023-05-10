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

import java.util.Optional;

public class SearchScene extends Scene {
    private Scene startScene;
    User user;
    String[][] bookData = {
            {"The Great Gatsby", "F. Scott Fitzgerald", "9780141182636", "Fiction"},
            {"To Kill a Mockingbird", "Harper Lee", "9780060935467", "Fiction"},
            {"Pride and Prejudice", "Jane Austen", "9780486284736", "Romance"},
            {"1984", "George Orwell", "9780451524935", "Science Fiction"},
            {"The Catcher in the Rye", "J.D. Salinger", "9780316769174", "Fiction"},
            {"Animal Farm", "George Orwell", "9780451526342", "Political Fiction"},
            {"Brave New World", "Aldous Huxley", "9780060850524", "Science Fiction"},
            {"The Hobbit", "J.R.R. Tolkien", "9780547928227", "Fantasy"},
            {"Lord of the Flies", "William Golding", "9780571295715", "Fiction"},
            {"The Da Vinci Code", "Dan Brown", "9780307474278", "Thriller"}
    };

    public SearchScene(Stage stage, Scene startScene, User user) {

        super(new BorderPane(), 700, 300);
        this.user = user;
        BorderPane borderPane = (BorderPane) this.getRoot();
        this.startScene = startScene;
        HomeScene homeScene = new HomeScene(stage, startScene, user);
        Node buttons = homeScene.getButtons(stage, startScene);
        borderPane.setTop(buttons);

        /**
        // Return to Start Screen
        Button returnButton = new Button("Return to Start Screen");
        GridPane.setConstraints(returnButton, 0, 0);
        returnButton.setOnAction(e -> {
            stage.setScene(this.startScene);
        });

        Button logoutButton = new Button("Logout");
        //logoutButton.setOnAction(e -> );
        GridPane.setConstraints(logoutButton, 8, 0);
         */
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
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Borrow Confirmation");
                dialog.setHeaderText(null);

                GridPane grid2 = new GridPane();
                grid2.setHgap(10);
                grid2.setVgap(10);
                grid2.setPadding(new Insets(20, 150, 10, 10));

                TextField borrowPeriod = new TextField("2 weeks");

                grid2.add(new Label("Borrow Period (in weeks):"), 0, 0);
                grid2.add(borrowPeriod, 1, 0);

                dialog.getDialogPane().setContent(grid2);

                ButtonType borrowButtonType = new ButtonType("Borrow", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(borrowButtonType);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == borrowButtonType) {
                        return borrowPeriod.getText();
                    }
                    return null;
                });

                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()) {
                    for (String item : selectedItems) {
                        System.out.println("Borrow \"" + item + "\" for " + result.get() + ".");
                    }
                }
            }
        });
        // TODO connect to database
        // Search button
        Button searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 1, 3);
        searchButton.setOnAction(e -> {
            String query = searchInput.getText().toLowerCase();
            items.clear();
            for (String[] book : bookData) {
                for (String field : book) {
                    if (field.toLowerCase().contains(query)) {
                        String result = book[0] + " - " + book[1] + " - " + book[2] + " - " + book[3];
                        items.add(result);
                        break;
                    }
                }
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

        // Add the nodes to the GridPane
        /**
        GridPane.setConstraints(returnButton, 0, 0);
        GridPane.setConstraints(logoutButton, 8, 0);
        gridPane.add(returnButton, 0, 2);
        gridPane.add(logoutButton, 8, 2);
        */
        stage.setTitle("SearchScene");
        stage.setScene(this);

        // Kolla över detta före att göra snyggt. Vilken av uppläggen?
        //gridPane.getChildren().addAll(returnButton, searchInput, searchButton, borrowButton, resultList, logoutButton);
        resultList.setItems(items);

        stage.setTitle("SearchScene");
        stage.setScene(this);
    }
}