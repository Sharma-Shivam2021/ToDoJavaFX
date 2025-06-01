package com.shivam.todoapp.controller;

import com.shivam.todoapp.dto.TaskDTO;
import com.shivam.todoapp.managers.TaskList;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TodoController {

    public VBox taskListVBox;
    public MFXComboBox<String> statusComboBox;

    private TaskList taskList;

    public void initialize() {
        taskList = new TaskList();
        statusComboBox.getItems().addAll("All", "ToDo", "InProgress", "Done");
        statusComboBox.setValue("All");
        statusComboBox.valueProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        filterTaskByStatus(newValue);
                    }
                }
        );
        redrawTaskList();
    }

    private void filterTaskByStatus(String status) {
        taskListVBox.getChildren().clear();
        List<TaskDTO> filteredTask;

        if ("All".equals(status)) {
            filteredTask = taskList.getTasks();
        } else {
            filteredTask = taskList.getTasks().stream().filter(
                    task ->
                            task.getStatus().equals(status)

            ).collect(Collectors.toList());
        }

        for (TaskDTO task : filteredTask) {
            displayTask(task);
        }

    }

    public void handleAddTask(ActionEvent actionEvent) {
        showAddTaskDialog();
    }


    public void addTaskFromDialog(String title, String description) {
        addTask(title, description, LocalDateTime.now(), "ToDo");
    }

    public void redrawTaskList() {
        taskListVBox.getChildren().clear();
        for (TaskDTO task : taskList.getTasks()) {
            displayTask(task);
        }
        statusComboBox.setValue("All");
    }

    private void showAddTaskDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/shivam/todoapp/Views/task_add_dialog.fxml"));
        try {
            VBox dialogPane = fxmlLoader.load();
            TaskAddDialogController dialogController = fxmlLoader.getController();
            dialogController.setMainController(this);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Task");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(dialogPane);
            String css = Objects.requireNonNull(this.getClass().getResource("/com/shivam/todoapp/stylesheets/addtaskstyles.css")).toExternalForm();
            scene.getStylesheets().add(css);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addTask(String title, String description, LocalDateTime dateAdded, String status) {
        TaskDTO newTask = new TaskDTO(title, description, dateAdded, status);
        taskList.addTask(newTask);
        redrawTaskList();
    }

    private void displayTask(TaskDTO task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/shivam/todoapp/Views/task-card.fxml"));
            HBox taskCard = loader.load();

            TaskCardController cardController = loader.getController();
            cardController.setTaskList(taskList);
            cardController.setTaskDetails(task.getTitle(), task.getDateAdded(), task.getStatus(), task.getId(), this);

            taskListVBox.getChildren().add(taskCard);

        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

}
