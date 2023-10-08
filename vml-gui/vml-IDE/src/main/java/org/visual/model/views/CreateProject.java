package org.visual.model.views;

import com.google.inject.Singleton;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class CreateProject {

	private final String fxml = "CreateProject";

	private final Stage window = new Stage(StageStyle.DECORATED);

//	private final Parent createProjectScene;

//	@SneakyThrows
//	@Inject
	CreateProject() {
//		createProjectScene = FxmlLoaderHelper.load(fxml);
//		window.setScene(createProjectScene.getScene());
	}

	public void show() {
		window.showAndWait();
	}
}
