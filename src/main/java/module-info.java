module com.example.coursereviewgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.coursereviewgui to javafx.fxml;
    exports com.example.coursereviewgui;
}