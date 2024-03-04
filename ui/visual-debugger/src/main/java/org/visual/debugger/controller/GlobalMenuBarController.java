package org.visual.debugger.controller;

import com.dlsc.preferencesfx.PreferencesFx;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class GlobalMenuBarController implements Initializable {
  @Inject PreferencesFx preferencesFx;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}

  public void openPreferences(ActionEvent actionEvent) {
    preferencesFx.show(true);
  }
}
