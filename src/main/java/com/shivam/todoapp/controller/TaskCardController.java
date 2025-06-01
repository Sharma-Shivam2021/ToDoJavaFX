package com.shivam.todoapp.controller;

import com.shivam.todoapp.dto.TaskDTO;
import com.shivam.todoapp.managers.TaskList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class TaskCardController {
    public Label taskName;
    public Label taskTimeStamp;
    public Label taskStatus;

    public UUID taskId;
    private TaskList taskList = new TaskList();

    private TodoController todoController;

    public void handleViewTask() {
        showTaskViewDialog();
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    private void showTaskViewDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/shivam/todoapp/Views/task_view_dialog.fxml"));
        try {
            VBox dialogPane = fxmlLoader.load();
            TaskViewDialogController dialogController = fxmlLoader.getController();
            dialogController.setTaskDetails(taskList.getTaskById(taskId), this);

            Stage dialogStage = new Stage();
            dialogStage.setTitle(taskName.getText());
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(dialogPane);
            String css = Objects.requireNonNull(this.getClass().getResource("/com/shivam/todoapp/stylesheets/viewdialogstyles.css")).toExternalForm();
            scene.getStylesheets().add(css);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTaskDetails(String name, LocalDateTime timeStamp, String status, UUID taskId, TodoController controller) {
        this.taskId = taskId;
        taskName.setText(name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a, dd.MM.yyyy");
        taskTimeStamp.setText(timeStamp.format(formatter));
        taskStatus.setText(status);
        applyStatusColor(status);
        todoController = controller;
    }

    private void applyStatusColor(String status) {
        switch (status) {
            case "ToDo" -> taskStatus.setStyle("-fx-text-fill: grey;");
            case "InProgress" -> taskStatus.setStyle("-fx-text-fill: orange;");
            case "Done" -> taskStatus.setStyle("-fx-text-fill: green;");
            default -> taskStatus.setStyle("-fx-text-fill: black;");
        }
    }

    public void updateTask(TaskDTO task) {
        taskList.updateTask(task);
        taskName.setText(task.getTitle());
        taskStatus.setText(task.getStatus());
        applyStatusColor(task.getStatus());
        todoController.redrawTaskList();
    }

    public void deleteTask(TaskDTO task) {
        taskList.removeTask(task);
        todoController.redrawTaskList();
    }
}
