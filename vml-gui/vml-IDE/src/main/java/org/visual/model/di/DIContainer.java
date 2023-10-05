package org.visual.model.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import lombok.Getter;
import lombok.ToString;
import org.visual.model.di.modules.InternalPropertyModule;
import org.visual.model.di.modules.RootModule;
import org.visual.model.di.modules.ViewModule;

@Getter
@ToString
public enum DIContainer {
	INSTANCE;

	private final RootModule root = new RootModule();

	private final ViewModule view = new ViewModule();

	private final InternalPropertyModule internal = new InternalPropertyModule();

	final Injector injector = Guice.createInjector(Stage.PRODUCTION, root, view, internal);
}
