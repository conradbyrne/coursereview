package com.example.coursereviewgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class viewerController {
    @FXML
    private TextField courseNameField;
    @FXML
    private Button showResultsButton;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Label averageRating;
    @FXML
    private Label courseReviews;
    @FXML
    private Label errorMessage;
    public void showResults(ActionEvent event) throws IOException {
        String courseName = courseNameField.getText();
        if (!courseName.contains(" ")) {
            errorMessage.setText("Invalid course name format!");
            return;
        }
        String department = courseName.split(" ")[0];
        String catalog_number = courseName.split(" ")[1];

        if(department.length() > 4) {
            errorMessage.setText("Course department is too long ( > 4 letters)!");
        }
        else if(departmentNotUppercase(department)) {
            errorMessage.setText("Course department must consist of uppercase letters!");
        }
        else if(catalog_number.length() != 4 || catalogNumberNotDigits(catalog_number)) {
            errorMessage.setText("Course number must be 4 digits!");
        }
        else {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            Course course = databaseManager.getCourseFromDepartmentandCatalogNumber(department, catalog_number);
            if (course == null) {
                errorMessage.setText("Sorry, no reviews for this course.");
            }
            else {
                List<String> reviews = databaseManager.getReviewsForCourse(course);
                String text = "";
                for(String r : reviews) {
                    text = text + r + "\n";
                }
                errorMessage.setText("");
                courseReviews.setText(text);
                averageRating.setText("" + databaseManager.getAverageRatingForCourse(course));
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

}

