package com.mycompany.librarysystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.StackPane;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.File;

public class Lek extends Application {

        @Override
        public void start(Stage primaryStage) {
            /**
            // Play song
            Media media = new Media(new File("C:\Users\Sak i Mark\Documents\NetBeansProjects\librarySystem\pizzSong.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
            */

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
}
