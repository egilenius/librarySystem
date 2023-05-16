package com.mycompany.librarysystem;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;

public class LoanScene extends Scene {
    private Scene startScene;
    User user;

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
        Label labelLoans = new Label("Loans");
        Label labelFinished = new Label("Finished");

        // Add labels and lists
        grid.add(labelOverdue, 0, 0);
        grid.add(listOverdue, 1, 0);
        grid.add(labelLoans, 0, 1);
        grid.add(listCurrentLoans, 1, 1);
        grid.add(labelFinished, 0, 2);
        grid.add(listFinishedLoans, 1, 2);

        // Add grid to center of BorderPane
        borderPane.setCenter(grid);

        fetchUserLoans(user, listOverdue, listCurrentLoans, listFinishedLoans);

        // Return button
        Label returnLabel = new Label("Return: ");
        TextField locationField = new TextField();
        grid.add(returnLabel, 0, 3);
        grid.add(locationField, 1, 3);

        // Show scene
        stage.setScene(this);
        stage.setTitle("LoanScene");
        stage.show();
    }

    private void fetchUserLoans(User user, ListView<String> listOverdue, ListView<String> listCurrentLoans, ListView<String> listFinishedLoans) {
        String query = "SELECT loan.*, item.* FROM public.loan JOIN public.item ON loan.itemid = item.itemid WHERE loan.userid = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, user.getUserid());
            ResultSet resultSet = preparedStatement.executeQuery();

            LocalDate today = LocalDate.now();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                boolean finished = resultSet.getBoolean("finished");
                LocalDate returnDate = resultSet.getDate("datedue").toLocalDate();

                if (finished) {
                    listFinishedLoans.getItems().add(title);
                } else if (returnDate.isAfter(today)) {
                    listCurrentLoans.getItems().add(title);
                } else {
                    listOverdue.getItems().add(title);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
