package org.visual.model.mvc.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.visual.model.mvc.base.Controller;
import org.visual.model.mvc.views.Settings;

@Slf4j
public class GlobalMenuController implements Controller {
    public MenuItem newProject;

    public void newProject(ActionEvent actionEvent) {
        val stage = new Stage();
        stage.centerOnScreen();
        DirectoryChooser directoryChooser = new DirectoryChooser();
    }

    public void openSettings(ActionEvent actionEvent) {
        Settings.SETTINGS.openSettings();
    }

    @Override
    public void initialize() {

    }
}
