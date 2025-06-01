module com.shivam.todoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires MaterialFX;

    opens com.shivam.todoapp to javafx.fxml;
    exports com.shivam.todoapp;
    exports com.shivam.todoapp.controller;
    exports com.shivam.todoapp.dto;
    exports com.shivam.todoapp.managers;
    opens com.shivam.todoapp.controller to javafx.fxml;
}