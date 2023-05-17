package com.mycompany.librarysystem;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoanScene extends Scene {
    private Scene startScene;
    User user;
    private Map<String, UUID> itemMap = new HashMap<>();


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

        // Create ListView components
        ListView<String> listOverdue = new ListView<>();
        ListView<String> listCurrentLoans = new ListView<>();
        ListView<String> listFinishedLoans = new ListView<>();

        Button returnButton = new Button("Return Selected");

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
        grid.add(returnButton, 2, 1);


        // Add grid to center of BorderPane
        borderPane.setCenter(grid);

        fetchUserLoans(user, listOverdue, listCurrentLoans, listFinishedLoans);

        returnButton.setOnAction(e -> {
            String selectedItem = listCurrentLoans.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                UUID itemId = itemMap.get(selectedItem);
                returnBook(user, itemId);
                listCurrentLoans.getItems().remove(selectedItem);
                listFinishedLoans.getItems().add(selectedItem);
            }
        });



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
                UUID itemId = (UUID) resultSet.getObject("itemid");
                String title = resultSet.getString("title");
                boolean finished = resultSet.getBoolean("finished");
                LocalDate returnDate = resultSet.getDate("datedue").toLocalDate();

                itemMap.put(title, itemId);


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
    private void returnBook(User user, UUID itemId) {
        String query = "UPDATE public.loan SET finished = true WHERE userid = ? AND itemid = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, user.getUserid());
            preparedStatement.setObject(2, itemId);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
