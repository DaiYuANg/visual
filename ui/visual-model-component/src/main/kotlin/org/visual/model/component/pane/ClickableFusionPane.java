package org.visual.model.component.pane;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import org.visual.model.component.theme.Theme;

public class ClickableFusionPane extends FusionPane {
    private EventHandler<?> handler;

    public ClickableFusionPane() {
        super();
        getNode().setCursor(Cursor.HAND);
    }

    public ClickableFusionPane(boolean manuallyHandleOuterRegion) {
        super(manuallyHandleOuterRegion);
        getNode().setCursor(Cursor.HAND);
    }

    @Override
    protected AbstractFusionPane buildRootNode() {
        return new FusionPaneImpl() {
            @Override
            protected Color downColor() {
                return Theme.current().fusionButtonDownBackgroundColor();
            }

            @Override
            protected void onMouseClicked() {
                var h = handler;
                if (h != null) {
                    h.handle(null);
                }
            }
        };
    }

    public void setOnAction(EventHandler<?> handler) {
        this.handler = handler;
    }
}
