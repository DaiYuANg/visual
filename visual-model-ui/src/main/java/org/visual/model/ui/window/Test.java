package org.visual.model.ui.window;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.val;
import org.jetbrains.annotations.NotNull;

public class Test extends Application {
    @Override
    public void start(@NotNull Stage stage) throws Exception {
        val panningWindow = new PanningWindow();
        panningWindow.getChildren().add(new Button("test"));
        val scene = new Scene(new StackPane(panningWindow), 600, 600);
        scene.getStylesheets().add("/window.css");
        stage.setScene(scene);
        stage.show();
    }
}
