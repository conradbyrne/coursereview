package com.example.coursereviewgui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;

import java.io.IOException;

public class adderController {
    @FXML
    private Label errorMessage;
    @FXML
    private TextField reviewTextField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField courseNameField;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button addButton;
    @FXML
    private Label successMessage;
    public void addReview(ActionEvent event) throws IOException {
        String courseName = courseNameField.getText();
        if(!courseName.contains(" ")) {
            sendError("Invalid course name format!");
            return;
        }
        String department = courseName.split(" ")[0];
        String catalog_number = courseName.split(" ")[1];

        if(department.length() > 4) {
            sendError("Course department is too long ( > 4 letters)!");
        }
        else if(departmentNotUppercase(department)) {
            sendError("Course department must consist of uppercase letters!");
        }
        else if(catalog_number.length() != 4 || catalogNumberNotDigits(catalog_number)) {
            sendError("Course number must be 4 digits!");
        }
        else {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            CurrentStudentTracker tracker = CurrentStudentTracker.getInstance();
            Course course = databaseManager.getCourseFromDepartmentandCatalogNumber(department, catalog_number);
            if (course == null)
                course = databaseManager.addCourse(department, catalog_number);

            if (databaseManager.hasStudentReviewedCourse(tracker.getCurrentStudent(), course)) {
                sendError("You have already reviewed this course!");
            } else {
                String text = reviewTextField.getText();
                try {
                    int rating = Integer.parseInt(ratingField.getText());
                    if (!(rating >= 1 && rating <= 5))
                        sendError("Your rating must be from 1 - 5!");
                    else {
                        databaseManager.addReview(tracker.getCurrentStudent(), course, text, rating);
                        sendSuccess("Review added!");
                    }
                } catch (NumberFormatException e) {
                    sendError("Rating must be an integer (1 - 5)!");
                }
            }
        }
    }

    public void goToMainMenu(ActionEvent event) throws IOException {
        CourseReviewApplication app = new CourseReviewApplication();
        app.changeScene("mainmenu.fxml");
    }

    private boolean departmentNotUppercase(String dept) {
        for(char c : dept.toCharArray()) {
            if(!Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean catalogNumberNotDigits(String number) {
        for (char c : number.toCharArray()) {
            if (!Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    private void sendError(String s) {
        errorMessage.setText(s);
        successMessage.setText("");
    }
    private void sendSuccess(String s) {
        errorMessage.setText("");
        successMessage.setText(s);
    }

}
