package org.visual.model.di.providers;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import com.google.inject.Inject;
import com.google.inject.Provider;
import javafx.stage.Stage;

public class FxTrayIconProvider implements Provider<FXTrayIcon> {

    @Inject
    private Stage stage;

    @Override
    public FXTrayIcon get() {
        System.err.println(stage);
        return new FXTrayIcon(stage);
    }
}
