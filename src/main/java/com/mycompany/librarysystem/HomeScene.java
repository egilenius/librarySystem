package com.mycompany.librarysystem;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HomeScene extends Scene {
    private User user;

    public HomeScene(Stage stage, User user) {
        super(new BorderPane(), 400, 300);
        this.user = user;
        BorderPane borderPane = (BorderPane) this.getRoot();
        //addButtonsToTop(borderPane, stage, startScene);

        Node buttons = getButtons(stage);
        //BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setTop(buttons);

        // Create main grid pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Show scene
        stage.setTitle("Library system");
        stage.setScene(this);
        stage.show();

    }

    public Node getButtons(Stage stage) {
        BorderPane borderPane = new BorderPane();
        // add buttons to the borderPane
        //User buttons

        //LogButton
        Button logButton = new Button("Login");
        GridPane.setConstraints(logButton, 0, 0);
        logButton.setPrefWidth(75);

        if (getUser() != null){
            logButton.setText("Logout");
            logButton.setOnAction(e -> {
                setUser(null);

            });
        } else {
            logButton.setText("Login");
            logButton.setOnAction(e -> {
                Scene loginScene = new LoginScene(stage);
                stage.setScene(loginScene);
            });

        }

        logButton.setOnAction(e -> {
            if (getUser() != null) {
                setUser(null);
                Scene homeScene = new HomeScene(stage, user);
                stage.setScene(homeScene);
                logButton.setText("Login");
            } else {
                Scene loginScene = new LoginScene(stage);
                stage.setScene(loginScene);
            }
        });

        //Search
        Button searchButton = new Button("Search");
        GridPane.setConstraints(searchButton, 1, 0);
        searchButton.setPrefWidth(75);
        searchButton.setOnAction(e -> {
            Scene searchScene = new SearchScene(stage,user);
            stage.setScene(searchScene);
        });
        //Loans
        Button LoanButton = new Button("Loans");
        GridPane.setConstraints(LoanButton, 2, 0);
        LoanButton.setPrefWidth(75);
        LoanButton.setOnAction(e -> {
            Scene LoanScene = new LoanScene(stage, user);
            stage.setScene(LoanScene);
        });

        //Create librarian buttons
        //Due items button
        Button dueButton = new Button("Due items");
        GridPane.setConstraints(dueButton, 0, 1);
        dueButton.setPrefWidth(75);
        dueButton.setOnAction(e -> {
            Scene dueScene = new DueScene(stage, user);
            stage.setScene(dueScene);
        });
        //add item button
        Button addButton = new Button("Insert");
        GridPane.setConstraints(addButton, 1, 1);
        addButton.setPrefWidth(75);
        addButton.setOnAction(e -> {
            Scene addScene = new AddScene(stage, user);
            stage.setScene(addScene);
        });

        //Update item button
        Button updateButton = new Button("Update");
        GridPane.setConstraints(updateButton, 2, 1);
        updateButton.setPrefWidth(75);
        updateButton.setOnAction(e -> {
            Scene updateScene = new UpdateScene(stage, user);
            stage.setScene(updateScene);
        });

        // Add buttons to top of BorderPane
        GridPane topGrid = new GridPane();
        topGrid.setPadding(new Insets(10, 10, 10, 10));
        topGrid.setHgap(10);
        topGrid.getChildren().addAll(searchButton, LoanButton, logButton, dueButton, addButton, updateButton);
        borderPane.setTop(topGrid);

        // Available buttons depending on kind of user

        if (getUser() == null){
            LoanButton.setDisable(true);
            dueButton.setDisable(true);
            addButton.setDisable(true);
            updateButton.setDisable(true);
        } else if (getUser().getAllowedloans() == 20){
            LoanButton.setDisable(false);
            dueButton.setDisable(true);
            addButton.setDisable(true);
            updateButton.setDisable(true);
            Label usernameLabel = new Label("Logged in as:\n" + getUser().getUsername());
            GridPane.setConstraints(usernameLabel, 0, 2);
            topGrid.getChildren().add(usernameLabel);
        } else {
            LoanButton.setDisable(false);
            dueButton.setDisable(false);
            addButton.setDisable(false);
            updateButton.setDisable(false);
            Label loginLabel = new Label("Logged in as:");
            Label usernameLabel = new Label(getUser().getUsername());
            usernameLabel.setStyle("-fx-font-weight: bold;");
            GridPane.setConstraints(loginLabel, 4, 0);
            GridPane.setConstraints(usernameLabel, 4, 1);
            topGrid.getChildren().addAll(loginLabel, usernameLabel);
        }


        return borderPane;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}