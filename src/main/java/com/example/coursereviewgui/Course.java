package com.example.coursereviewgui;

public class Course {
    private int id; //auto-increment
    private String department;
    private String catalog_number;

    public Course(int id, String department, String catalog_number) {
        this.id = id;
        this.department = department;
        this.catalog_number = catalog_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCatalogNumber() {
        return catalog_number;
    }

    public void setCatalogNumber(String catalog_number) {
        this.catalog_number = catalog_number;
    }
}
