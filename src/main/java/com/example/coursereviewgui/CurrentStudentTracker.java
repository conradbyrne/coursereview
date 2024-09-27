package com.example.coursereviewgui;

public class CurrentStudentTracker {
    private static CurrentStudentTracker instance = null;
    private Student currentStudent;
    private CurrentStudentTracker() {}

    public static CurrentStudentTracker getInstance() {
        if(instance == null) {
            instance = new CurrentStudentTracker();
        }
        return instance;
    }
    public Student getCurrentStudent() {
        return currentStudent;
    }
    public void setCurrentStudent(Student s) {
        currentStudent = s;
    }
}
