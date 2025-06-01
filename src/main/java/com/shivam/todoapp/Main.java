package com.shivam.todoapp;

import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA)
                .themes(MaterialFXStylesheets.forAssemble(true))
                .setDeploy(true)
                .setResolveAssets(true)
                .build().setGlobal();


        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/shivam/todoapp/Views/todo.fxml")));

        Scene scene = new Scene(root, Color.TRANSPARENT);

        String css = Objects.requireNonNull(this.getClass().getResource("/com/shivam/todoapp/stylesheets/styles.css")).toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        primaryStage.setMaxHeight(600);
        primaryStage.setTitle("Todo App");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
