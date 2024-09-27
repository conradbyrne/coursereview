package com.example.coursereviewgui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private Button viewButton;
    @FXML
    private Button addButton;
    @FXML
    private Button returnButton;
    @FXML
    private Button quitButton;

    public void viewReview(ActionEvent event) throws IOException {
        CourseReviewApplication app = new CourseReviewApplication();
        app.changeScene("viewreview.fxml");
    }
    public void addReview(ActionEvent event) throws IOException {
        CourseReviewApplication app = new CourseReviewApplication();
        app.changeScene("addreview.fxml");
    }
    public void returnToLogin(ActionEvent event) throws IOException {
        CourseReviewApplication app = new CourseReviewApplication();
        CurrentStudentTracker tracker = CurrentStudentTracker.getInstance();
        app.changeScene("loginpage.fxml");
        tracker.setCurrentStudent(null);
    }
    public void quit(ActionEvent event) throws IOException {
        System.exit(0);
    }

}
