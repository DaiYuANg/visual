package org.visual.component.control;

import javafx.scene.image.Image;
import javafx.scene.layout.CornerRadii;
import org.jetbrains.annotations.NotNull;
import org.visual.component.container.VStage;
import org.visual.component.container.VStageInitParams;

public class CloseButton extends WindowControlButton {
  private CornerRadii cornerRadii;

  public CloseButton(VStage stage, VStageInitParams initParams) {
    super(stage, initParams);
  }

  @Override
  protected void init(@NotNull VStageInitParams initParams) {
    if (initParams.iconifyButton || initParams.maximizeAndResetButton) {
      cornerRadii = CornerRadii.EMPTY;
    } else {
      cornerRadii = new CornerRadii(0, 0, 0, 4, false);
    }
  }

  @Override
  protected void onMouseClicked() {
    stage.close();
  }

  @Override
  protected CornerRadii getCornerRadii() {
    return cornerRadii;
  }

  @Override
  protected Image getNormalImage() {
    return null;
  }

  @Override
  protected Image getHoverImage() {
    return null;
  }
}
