package com.shivam.todoapp.controller;

import com.shivam.todoapp.dto.TaskDTO;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TaskViewDialogController {
    public MFXTextField taskTitleField;
    public TextArea taskDescriptionField;
    public MFXComboBox<String> statusComboBox;
    public MFXTextField commentField;
    public VBox commentList;

    private TaskDTO task;
    private TaskCardController taskCardController;

    public void setTaskDetails(TaskDTO task, TaskCardController taskCardController) {
        this.task = task;
        this.taskCardController = taskCardController;

        taskTitleField.setText(task.getTitle());
        taskDescriptionField.setText(task.getDescription());
        statusComboBox.getItems().clear();
        statusComboBox.getItems().addAll("ToDo", "InProgress", "Done");
        // makes sure that correct status is loaded after the ui is built
        Platform.runLater(() -> statusComboBox.setValue(task.getStatus()));

        task.getComments().forEach(this::displayComment);

    }

    private void displayComment(String comment) {
        Text commentLabel = new Text(comment);
        commentLabel.setStyle("-fx-padding: 3px;");
        commentList.getChildren().addFirst(commentLabel);
    }


    public void handleAddComment() {
        String comment = commentField.getText();
        if (!comment.isEmpty()) {
            task.addComment(comment);
            displayComment(comment);
            commentField.clear();
        }
    }

    public void handleCancel() {
        closeDialog();
    }

    public void handleUpdate() {
        task.setTitle(taskTitleField.getText());
        task.setDescription(taskDescriptionField.getText());
        task.setStatus(statusComboBox.getValue());
        taskCardController.updateTask(task);
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) taskTitleField.getScene().getWindow();
        stage.close();
    }


    public void handleDelete() {
        taskCardController.deleteTask(task);
        closeDialog();
    }
}
