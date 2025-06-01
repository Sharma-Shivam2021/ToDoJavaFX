package com.shivam.todoapp.controller;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TaskAddDialogController {
    public MFXTextField taskTitleField;
    public TextArea taskDescriptionField;

    private TodoController mainController;

    public void setMainController(TodoController mainController) {
        this.mainController = mainController;
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeDialog();
    }

    public void handleSubmit(ActionEvent actionEvent) {
        String title = taskTitleField.getText();
        String description = taskDescriptionField.getText();
        if (!title.isEmpty()) {
            mainController.addTaskFromDialog(title, description);
            closeDialog();
        } else {
            taskTitleField.setPromptText("This field cannot be empty");
            System.out.println("Title is required");
        }
    }

    private void closeDialog() {
        // getting the current window from a node in the current scene,
        // casting it into stage and then closing the stage.
        Stage stage = (Stage) taskTitleField.getScene().getWindow();
        stage.close();
    }
}
