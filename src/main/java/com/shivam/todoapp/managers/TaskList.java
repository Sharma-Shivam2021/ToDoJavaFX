package com.shivam.todoapp.managers;

import com.shivam.todoapp.dto.TaskDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class TaskList implements Serializable {

    private static final String filePath = "src/task.bin";

    @Serial
    private static final long serialVersionUID = 1;

    private List<TaskDTO> tasks = new ArrayList<>();

    public TaskList() {
        loadTasks();
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void addTask(TaskDTO task) {
        tasks.add(task);
        sortTaskByStatus();
        saveTasks();
    }

    public void removeTask(TaskDTO task) {
        tasks.remove(task);
        saveTasks();
    }

    public TaskDTO getTaskById(UUID id) {
        for (TaskDTO task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public void updateTask(TaskDTO updateTask) {
        for (TaskDTO currentTask : tasks) {
            if (currentTask.getId().equals(updateTask.getId())) {
                currentTask.setTitle(updateTask.getTitle());
                currentTask.setDescription(updateTask.getDescription());
                currentTask.setStatus(updateTask.getStatus());
                currentTask.setComments(new ArrayList<>(updateTask.getComments()));
                break;
            }
        }
        sortTaskByStatus();
        saveTasks();
    }

    private void sortTaskByStatus() {
        tasks.sort(Comparator.comparingInt(
                        task -> {
                            switch (task.getStatus()) {
                                case "ToDo" -> {
                                    return 1;
                                }
                                case "InProgress" -> {
                                    return 2;
                                }
                                case "Done" -> {
                                    return 3;
                                }
                                default -> {
                                    return 4;
                                }
                            }
                        }
                )
        );
    }

    private void saveTasks() {
        try (
                FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(tasks);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTasks() {
        File file = new File(filePath);
        if (file.exists()) {
            try (
                    FileInputStream fileOutputStream = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fileOutputStream)
            ) {
                //noinspection unchecked
                tasks = (List<TaskDTO>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            saveTasks();
        }

    }

}
