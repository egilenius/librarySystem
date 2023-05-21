package com.mycompany.librarysystem;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {

    private UUID userid;
    private String username;
    private int allowedloans;
    private int presentloans;



    public User(String username, UUID userid, int allowedloans, int presentloans) {
        this.username = username;
        this.userid = userid;
        this.allowedloans = allowedloans;
        this.presentloans = presentloans;
    }

    public int getPresentLoansFromDatabase() {
        int presentLoans = 0;

        String query = "SELECT presentloans FROM public.anv WHERE userid = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, this.getUserid());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                presentLoans = resultSet.getInt("presentloans");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("There was an error while fetching present loans. Please try again.");
            alert.showAndWait();
        }
        return presentLoans;
    }

    public int getAllowedLoansFromDatabase() {
        int allowedLoans = 0;

        String query = "SELECT allowedloans FROM public.anv WHERE userid = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, this.getUserid());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                allowedLoans = resultSet.getInt("allowedloans");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("There was an error while fetching allowed loans. Please try again.");
            alert.showAndWait();
        }
        return allowedLoans;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUserid() {
        return userid;
    }

    public int getAllowedloans() {
        return getAllowedLoansFromDatabase();
    }

    public int getPresentloans() {
        return getPresentLoansFromDatabase();
    }

    public boolean hasReachedLoanLimit() {
        return this.getPresentloans() >= this.getAllowedloans();
    }
    public void setPresentloans(int newPresentLoans) {
    }



    // Constructor, getters, setters, and database methods


}
