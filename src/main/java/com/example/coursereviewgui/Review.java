package com.example.coursereviewgui;

public class Review {
    private int id; //auto-increment
    private Student student;
    private Course course;
    private String review_text;
    private int rating; // 1-5

    public Review(int id, Student student, Course course, String text, int rating) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.review_text = text;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getReview() {
        return review_text;
    }

    public void setReview(String review_text) {
        this.review_text = review_text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
