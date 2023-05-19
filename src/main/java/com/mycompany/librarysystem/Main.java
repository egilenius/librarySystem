package com.mycompany.librarysystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

    public class Main extends Application {
        private Scene mainScene;
        private User user;
        private Button loginButton, searchButton, updateButton, dueButton, addButton, homeButton, loansButton, logoutButton;
        private GridPane grid;

        public static void main(String[] args) {
            launch(args);}

        @Override
        public void start(Stage stage) throws Exception {
            Scene homeScene = new HomeScene(stage, user);
            stage.setScene(homeScene);
            /**
            stage.setTitle("Start Screen");

            grid = new GridPane();
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setVgap(8);
            grid.setHgap(10);

            user = null;

            //Login Button
            loginButton = new Button("Login");
            GridPane.setConstraints(loginButton, 0, 0);
            loginButton.setOnAction(e -> {
                LoginScene loginScene = new LoginScene(stage, mainScene, user);
                stage.setScene(loginScene);
                // perform login operations
                // updateButtonState();  // Uncomment this line after setting the user in your login operations.
            });

            //Home Button
            homeButton = new Button("Home");
            GridPane.setConstraints(homeButton, 1, 0);
            homeButton.setOnAction(e -> {
                Scene homeScene = new HomeScene(stage, mainScene, user);
                stage.setScene(homeScene);

            });
                // perform home operations


            //Search Button
            searchButton = new Button("Search");
            GridPane.setConstraints(searchButton, 2, 0);
            searchButton.setOnAction(e -> {
                SearchScene searchScene = new SearchScene(stage, mainScene, user);
                stage.setScene(searchScene);

                // perform search operations
            });

            //Loans Button
            loansButton = new Button("Loans");
            GridPane.setConstraints(loansButton, 3, 0);
            loansButton.setOnAction(e -> {
                Scene LoanScene = new LoanScene(stage, mainScene, user);
                stage.setScene(LoanScene);
                // perform loan operations
            });

            //Update Button
            updateButton = new Button("Update item");
            GridPane.setConstraints(updateButton, 0, 1);
            updateButton.setOnAction(e -> {
                Scene updateScene = new UpdateScene(stage, mainScene, user);
                stage.setScene(updateScene);
                // perform update operations
            });

            //Due Button
            dueButton = new Button("Due items");
            GridPane.setConstraints(dueButton, 1, 1);
            dueButton.setOnAction(e -> {
                Scene dueScene = new DueScene(stage, mainScene, user);
                stage.setScene(dueScene);
                // perform overdue operations
            });

            //Add Button
            addButton = new Button("Add item");
            GridPane.setConstraints(addButton, 2, 1);
            addButton.setOnAction(e -> {
                Scene addScene = new AddScene(stage, mainScene, user);
                stage.setScene(addScene);
                // perform add operations
            });

            //Logout Button
            logoutButton = new Button("Not logged in");
            GridPane.setConstraints(logoutButton, 3, 1);
            logoutButton.setOnAction(e -> {
                user = null;
                updateButtonState();
            });

            //Add all the elements to the grid
            grid.getChildren().addAll(loginButton, homeButton, searchButton, loansButton, updateButton, dueButton, addButton, logoutButton);

            mainScene = new Scene(grid, 500, 200);
            stage.setScene(mainScene);
            stage.show();

            // initially disable buttons
            updateButtonState();
             */
        }

        private void updateButtonState() {
            // Only enable buttons if user is logged in
            boolean loggedIn = user != null;
            searchButton.setDisable(!loggedIn);
            updateButton.setDisable(!loggedIn);
            dueButton.setDisable(!loggedIn);
            addButton.setDisable(!loggedIn);
            loansButton.setDisable(!loggedIn);

            // Change login button text based on login state
            loginButton.setText(loggedIn ? "Logout" : "Login");

            // Set logout button text
            logoutButton.setText(loggedIn ? "Logout (" + user.getUsername() + ")" : "Not logged in");
        }
    }


