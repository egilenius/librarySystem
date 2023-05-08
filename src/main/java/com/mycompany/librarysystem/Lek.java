package com.mycompany.librarysystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.File;

public class Lek extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Add Item");

        // Create UI controls
        Label titleLabel = new Label("Title:");
        TextField titleTextField = new TextField();

        Label authorLabel = new Label("Author/Director:");
        TextField authorTextField = new TextField();

        Label isbnLabel = new Label("ISBN (for books only):");
        TextField isbnTextField = new TextField();

        Label genreLabel = new Label("Genre/Subject:");
        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.getItems().addAll("Programming", "Java", "System Development", "Horror", "Drama", "Comedy");

        Label ratingLabel = new Label("Rating (for movies only):");
        TextField ratingTextField = new TextField();

        Label countryLabel = new Label("Country (for movies only):");
        TextField countryTextField = new TextField();

        Label actorsLabel = new Label("Actors (for movies only):");
        TextField actorsTextField = new TextField();

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Save item to library system
            System.out.println("Item saved!");
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> primaryStage.close());

        // Create layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, titleLabel, titleTextField);
        gridPane.addRow(1, authorLabel, authorTextField);
        gridPane.addRow(2, isbnLabel, isbnTextField);
        gridPane.addRow(3, genreLabel, genreComboBox);
        gridPane.addRow(4, ratingLabel, ratingTextField);
        gridPane.addRow(5, countryLabel, countryTextField);
        gridPane.addRow(6, actorsLabel, actorsTextField);
        gridPane.addRow(7, saveButton, cancelButton);

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
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
