package com.mycompany.librarysystem;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddScene extends Scene {

    public AddScene(Stage stage, Scene startScene) {
        super(new BorderPane(), 600, 400);

        // create main layout
        BorderPane borderPane = (BorderPane) this.getRoot();
        HomeScene homeScene = new HomeScene(stage, startScene);
        Node buttons = homeScene.getButtons(stage, startScene);
        borderPane.setTop(buttons);

        // create form
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setVgap(10);
        form.setHgap(10);

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

        // add form to layout
        borderPane.setCenter(form);
    }
}
