package com.example.coursereviewgui;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance = null;
    private Connection connection;
    private DatabaseManager() {}

    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    public void checkConnection() {
        try {
            if(connection == null || connection.isClosed()){
                throw new Exception();
            }
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    public void connect() {
        try {
            if(connection != null){
                throw new Exception();
            }
        } catch (Exception e) {
            throw new IllegalStateException();
        }
        try {
            String url = "jdbc:sqlite:" + "Reviews.sqlite3";
            try {
                connection = DriverManager.getConnection(url);
            }
            catch(SQLException e) {
                throw new IllegalStateException();
            }
        }
        catch (Exception ignored) {

        }
    }
    public void createTables() {
        try {

            Statement statement = connection.createStatement();if (tableExists("Students")) {

            } else {
                String studentsTable = "CREATE TABLE Students (" +
                        "ID        INTEGER         PRIMARY KEY  AUTOINCREMENT," +
                        "Name      VARCHAR(255)    NOT NULL," +
                        "Password  VARCHAR(255)    NOT NULL" +
                        ")";

                statement.execute(studentsTable);
            }
            if (tableExists("Courses")) {

            } else {
                String coursesTable = "CREATE TABLE Courses (" +
                        "ID              INTEGER          PRIMARY KEY   AUTOINCREMENT," +
                        "Department      VARCHAR(255)     NOT NULL," +
                        "Catalog_Number  VARCHAR(255)     NOT NULL" +
                        ")";
                statement.execute(coursesTable);
            }
            if (tableExists("Reviews")) {

            } else {
                String reviewsTable = "CREATE TABLE Reviews (" +
                        "ID            INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "StudentID INTEGER,"+
                        "CourseID INTEGER,"+
                        "Review_Text  VARCHAR(4095) NOT NULL," +
                        "Rating       INTEGER NOT NULL," +
                        "FOREIGN KEY  (StudentID) REFERENCES Students(ID) ON DELETE CASCADE," +
                        "FOREIGN KEY  (CourseID)  REFERENCES Courses(ID)   ON DELETE CASCADE" +
                        ")";
                statement.execute(reviewsTable);
            }
            statement.close();
        }
        catch (SQLException e) {
            throw new IllegalStateException("Failed to create tables.");
        }
    }

    public boolean tableExists(String tableName) {
        String checkIfTablesExistStatement = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'";
        boolean alreadyExists = false;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(checkIfTablesExistStatement);
            while (resultSet.next()) {
                alreadyExists = resultSet.getString("name") != null;
            }
            statement.close();
        } catch (Exception e) {

        }
        return alreadyExists;
    }
    public Student getStudentFromName(String name) {
        // return a student object (see Student.java) from the database with the given name.
        // if it doesn't exist, return null
        checkConnection();
        if(!tableExists("Students")){
            throw new IllegalStateException();
        }

        String sqlCommand = "Select * From Students Where Name Like \"%" + name + "%\"";
        Student student = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                //String name = resultSet.getString("Name");
                String password = resultSet.getString("Password");
                student = new Student(id, name, password);
            }
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        if(student == null) {
            return null;
        }
        return student;
    }

    public Student addStudent(String name, String password) {
        // Add a student to the database, note that you need to add a unique
        checkConnection();
        if(!tableExists("Students")){
            throw new IllegalStateException();
        }
        int id;
        String addStudentCommand = "Insert Into Students(ID, Name, Password) Values(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addStudentCommand);
            id = getNumberOfElements("Students");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return new Student(id, name, password);
    }

    public Course getCourseFromDepartmentandCatalogNumber(String department, String number) {
        // TO DO: return a course object (see Course.java) from the database with the given info
        // both department and number must match
        // if it doesn't exist, return null

        checkConnection();


        String sqlCommand = "SELECT * FROM Courses WHERE Department Like \"%" + department + "%\"" +
                "AND Catalog_Number LIKE \"%" + number + "%\"";
        Course c = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            int id;
            if (resultSet.next()) {
                id = resultSet.getInt("ID");
                c = new Course(id, department, number);
            }
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return c;

    }

    public Course addCourse(String department, String number) {
        // Add a course to the database, note that you need to add a unique
        // returns course obj
        checkConnection();
        String addCourseCommand = "Insert Into Courses(ID, Department, Catalog_Number) Values(?,?,?)";
        Course c;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addCourseCommand);
            int id = getNumberOfElements("Courses");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, department);
            preparedStatement.setString(3, number);
            preparedStatement.executeUpdate();
            c = new Course(id, department, number);
            preparedStatement.close();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return c;
    }

    public List<String> getReviewsForCourse(Course course) {
        // TO DO: return (as array of Strings) all the reviews for course
        int courseID = course.getId();
        try {
            String sql = "Select Review_Text FROM Reviews WHERE CourseID = " + courseID;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            List<String> reviews = new ArrayList<>();
            while(rs.next()) {
                reviews.add(rs.getString("Review_Text"));
            }
            statement.close();
            return reviews;
        }
        catch(SQLException e) {
            throw new IllegalStateException();
        }
    }

    public double getAverageRatingForCourse(Course course) {
        // TO DO: return (as double) the average rating for course
        int courseID = course.getId();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT AVG(Rating) FROM Reviews WHERE CourseID = " + courseID);
            rs.next();
            double averageRating = rs.getDouble(1);
            statement.close();
            return averageRating;
        }
        catch(SQLException e) {
            throw new IllegalStateException();
        }
    }


    public int getNumberOfElements(String table){
        int count =0;
        String query = "Select COUNT(*) From " + table;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            count = rs.getInt(1);
            stmt.close();
        } catch(SQLException e) {

        }
        return count;
    }

    public boolean hasStudentReviewedCourse(Student student, Course course) {
        //to-do: return true if student has reviewed course
        int count = 0;
        int stuID = student.getId();
        int courseID = course.getId();

        String query = "SELECT COUNT(*) FROM Reviews WHERE StudentID LIKE '"+ stuID +"' " +
                "AND CourseID LIKE '"+ courseID +"' ";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            count = rs.getInt(1);
            stmt.close();

        } catch(SQLException e) {

        }

        if(count != 0) {
            return true;
        }
        return false;
    }

    public void addReview(Student student, Course course, String text, int rating) {
        //adds review to table
        checkConnection();

        String addReviewCommand = "Insert Into Reviews(ID, StudentID, CourseID, Review_Text, Rating) Values(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addReviewCommand);
            preparedStatement.setInt(1, getNumberOfElements("Reviews"));
            preparedStatement.setInt(2, student.getId());
            preparedStatement.setInt(3, course.getId());
            preparedStatement.setString(4, text);
            preparedStatement.setInt(5, rating);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
