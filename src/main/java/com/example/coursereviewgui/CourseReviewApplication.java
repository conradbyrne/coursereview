package com.example.coursereviewgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseReviewApplication extends Application {
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
        stage.setTitle("Course Review");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void changeScene(String FXML) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(FXML));
        stage.getScene().setRoot(pane);

    }

    public static void main(String[] args) {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.connect();
        databaseManager.createTables();
        launch();
    }
}