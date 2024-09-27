package com.example.coursereviewgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button loginButton;
    @FXML
    private Button createUserButton;
    @FXML
    private TextField existingUserLoginField;
    @FXML
    private TextField createUsernameField;
    @FXML
    private PasswordField existingUserPasswordField;
    @FXML
    private PasswordField createPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label errorMessage;
    @FXML
    private Label successMessage;

    public void login(ActionEvent event) throws IOException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        CurrentStudentTracker tracker = CurrentStudentTracker.getInstance();
        CourseReviewApplication app = new CourseReviewApplication();

        String enteredName = existingUserLoginField.getText();
        String enteredPassword = existingUserPasswordField.getText();

        Student enteredStudent = databaseManager.getStudentFromName(enteredName);
        if (enteredStudent == null) {
            sendErrorMessage("Error: Student doesn't exist!");
        }
        else if(!enteredPassword.equals(enteredStudent.getPassword())) {
            sendErrorMessage("Password incorrect!");
        }
        else {
            tracker.setCurrentStudent(enteredStudent);
            app.changeScene("mainmenu.fxml");
        }
    }

    public void createNewUser(ActionEvent event) throws IOException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        CurrentStudentTracker tracker = CurrentStudentTracker.getInstance();

        String enteredName = createUsernameField.getText();
        String enteredPassword = createPasswordField.getText();
        String confirmedPassword = confirmPasswordField.getText();


        if (databaseManager.getStudentFromName(enteredName) != null) {
            sendErrorMessage("Error: User with this name already exists!");
        }
        else if (!enteredPassword.equals(confirmedPassword)) {
            sendErrorMessage("Entered passwords don't match!");
        }
        else {
            tracker.setCurrentStudent(databaseManager.addStudent(enteredName, enteredPassword));
            sendSuccessMessage("User created!");

        }
    }
    public void sendErrorMessage(String message) {
        successMessage.setText("");
        errorMessage.setText(message);
    }
    public void sendSuccessMessage(String message) {
        errorMessage.setText("");
        successMessage.setText(message);
    }




}
