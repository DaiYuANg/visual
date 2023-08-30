module org.visual.model.gui.ide {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.web;
	requires static lombok;
//	requires com.google.common;
//	requires io.vertx.core;
	requires org.slf4j;
	requires org.jetbrains.annotations;
//	requires org.reflections;
//	requires com.google.guice;
//	requires java.prefs;
//	requires atlantafx.base;
//	requires com.almasb.fxgl.all;
	requires com.dlsc.preferencesfx;
    requires java.prefs;
    requires app.supernaut.fx;
    requires com.google.common;
    requires atlantafx.base;
    requires com.almasb.fxgl.all;
	requires jakarta.inject;
//	requires io.micronaut.inject;  // Needed for Micronaut-generated classes
	requires jakarta.annotation;
//	requires com.google.gson;
//	requires com.google.common.jimfs;

    opens org.visual.model to javafx.fxml;

	exports org.visual.model;
	exports org.visual.model.mvc.controllers;

	opens org.visual.model.mvc.controllers to javafx.fxml;

	exports org.visual.model.mvc.views;

	opens org.visual.model.mvc.views to javafx.fxml;

	exports org.visual.model.i18n;

	opens org.visual.model.i18n to javafx.fxml;

	exports org.visual.model.setup;

	opens org.visual.model.setup to javafx.fxml;

	exports org.visual.model.contexts;

	opens org.visual.model.contexts to javafx.fxml;

	uses app.supernaut.fx.FxLauncher;

}
