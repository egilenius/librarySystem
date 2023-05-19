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

public class UpdateScene extends Scene {
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

    public UpdateScene(Stage stage, User user) {
        /**
        super(new GridPane(), 700, 300);
        GridPane grid = (GridPane) this.getRoot();
        this.startScene = startScene;
        HomeScene homeScene = new HomeScene(stage, startScene);
        Node buttons = homeScene.getButtons(stage, startScene);
        borderPane.setTop(buttons);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
         */
        super(new BorderPane(), 700, 300);
        this.user = user;
        BorderPane borderPane = (BorderPane) this.getRoot();
        this.startScene = startScene;
        HomeScene homeScene = new HomeScene(stage, user);
        Node buttons = homeScene.getButtons(stage);
        borderPane.setTop(buttons);


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
        // Update item
        Button updateButton = new Button("Update");
        GridPane.setConstraints(updateButton, 1, 7);
        updateButton.setOnAction(e -> {});

        // TODO connect to database
        // delete item
        Button deleteButton = new Button("Delete");
        GridPane.setConstraints(deleteButton, 2, 7);
        deleteButton.setOnAction(e -> {});

        // TODO conncet to database
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
        });
        /**
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 10, 10, 10));
        //gridPane.add(searchInput, 0, 0);
        //gridPane.add(searchButton, 1, 0);
        //gridPane.add(resultList, 0, 1, 2, 1);

        // Add the GridPane to the center of the BorderPane
        borderPane.setCenter(gridPane);
        borderPane.getChildren().addAll(searchInput, searchButton, updateButton, deleteButton, resultList);
         */

        VBox vbox = new VBox();
        vbox.getChildren().addAll(searchInput, searchButton, resultList);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 10, 10, 10));
        borderPane.setCenter(vbox);
        borderPane.setRight(new VBox(updateButton, deleteButton));

        resultList.setItems(items);

        stage.setTitle("SearchScene");
        stage.setScene(this);
    }
}