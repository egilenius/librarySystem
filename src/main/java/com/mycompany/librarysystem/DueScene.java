package com.mycompany.librarysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DueScene extends Scene {
    User user;

    public DueScene(Stage stage, User user) {
        super(new BorderPane(), 400, 300);
        this.user = user;
        BorderPane borderPane = (BorderPane) this.getRoot();

        HomeScene homeScene = new HomeScene(stage,  user);
        Node buttons = homeScene.getButtons(stage);
        borderPane.setTop(buttons);

        // Create main grid pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);


        // Create TableView component
        TableView<Loan> tableDue = new TableView<>();
        tableDue.setEditable(false);

        // Create columns for the table
        TableColumn<Loan, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Loan, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<Loan, LocalDate> returnDateCol = new TableColumn<>("Due Date");
        returnDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        // Add columns to table
        tableDue.getColumns().addAll(titleCol, nameCol, returnDateCol);

        // Add table to grid
        grid.add(tableDue, 0, 0);

        // Add grid to center of BorderPane
        borderPane.setCenter(grid);

        fetchOverdueLoans(tableDue);

        // Show scene
        stage.setScene(this);
        stage.setTitle("DueScene");
        stage.show();

    }

    private void fetchOverdueLoans(TableView<Loan> tableDue) {

        String query = "SELECT loan.datedue, item.title, anv.firstname, anv.lastname \n" +
                "FROM public.loan \n" +
                "JOIN public.item ON loan.itemid = item.itemid \n" +
                "JOIN public.anv ON loan.userid = anv.userid \n" +
                "WHERE loan.datedue < CURRENT_DATE AND loan.finished = false;";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String title = resultSet.getString("title");
                LocalDate returnDate = resultSet.getDate("datedue").toLocalDate();
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");

                Loan loan = new Loan(title, returnDate, firstName, lastName);

                tableDue.getItems().add(loan);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

