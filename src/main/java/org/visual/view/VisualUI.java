/* (C)2024*/
package org.visual.view;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.visual.constant.FXMLView;
import org.visual.context.ApplicationContext;
import org.visual.handle.GlobalExceptionHandler;
import org.visual.local.store.repository.HistoryRepository;

@Slf4j
public class VisualUI extends Application {
  private final Parent rootFxml = ApplicationContext.INSTANCE.load(FXMLView.MAIN_LAYOUT);

  private final Scene rootScene = new Scene(rootFxml);

  private final Stage rootStage = ApplicationContext.INSTANCE.get(Stage.class);

  private final GlobalExceptionHandler exceptionHandler =
      ApplicationContext.INSTANCE.get(GlobalExceptionHandler.class);

  //  private final HistoryRepository historyRepository =
  //      ApplicationContext.INSTANCE.get(HistoryRepository.class);

  private final String theme = new PrimerLight().getUserAgentStylesheet();

  @Override
  public void init() {
    Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
    Application.setUserAgentStylesheet(theme);
    val hr = ApplicationContext.INSTANCE.get(HistoryRepository.class);
    val latestHistory = hr.findLatestHistory();
    //    System.err.println(hr.findLatestHistory());
    //    log.info("Latest history:{}", latestHistory);
    //        rootScene.getStylesheets().addAll("/help.css", "/theme.css");
    rootScene.setFill(Color.TRANSPARENT);
  }

  @SneakyThrows
  @Override
  public void start(Stage stage) {
    rootStage.setScene(rootScene);
    //        VisualModelDebugger.show(rootScene);
    rootStage.show();

    //        FXComponentInspectorHandler.handleAll();
  }
}
