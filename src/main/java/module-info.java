module com.example.elissa {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires kernel;
    requires layout;


    opens com.example.elissa.Models;


    opens com.example.elissa to javafx.fxml;
    opens com.example.elissa.Controller to javafx.fxml; // Open the Controller package
    exports com.example.elissa;
}
