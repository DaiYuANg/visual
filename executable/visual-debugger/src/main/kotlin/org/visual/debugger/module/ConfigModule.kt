package org.visual.debugger.module

import com.google.inject.AbstractModule
import com.google.inject.Provides
import jakarta.inject.Named
import org.github.gestalt.config.Gestalt
import org.github.gestalt.config.builder.GestaltBuilder
import org.github.gestalt.config.loader.EnvironmentVarsLoader
import org.github.gestalt.config.loader.MapConfigLoader
import org.github.gestalt.config.loader.PropertyLoader
import org.github.gestalt.config.source.ClassPathConfigSourceBuilder
import org.github.gestalt.config.source.EnvironmentConfigSourceBuilder
import org.github.gestalt.config.source.SystemPropertiesConfigSourceBuilder
import org.visual.debugger.view.VisualDebuggerView
import org.visual.shared.PreferencesWrapper
import java.util.prefs.Preferences

class ConfigModule:AbstractModule() {
    override fun configure() {
    }

    @Provides
    @Named("VisualModelDebuggerGestalt")
    fun gestalt(): Gestalt? {
        val configLoaders =
            listOf(EnvironmentVarsLoader(), PropertyLoader(), MapConfigLoader())
        val environmentSource =
            EnvironmentConfigSourceBuilder.builder()
                .setPrefix("VISUAL_MODEL")
                .setFailOnErrors(false)
                .build()
        val classPathSource =
            ClassPathConfigSourceBuilder.builder()
                .setResource("visual.model.debugger.properties")
                .build()
        val javafxClassPathSource =
            ClassPathConfigSourceBuilder.builder().setResource("javafx.properties").build()
        val systemSource = SystemPropertiesConfigSourceBuilder.builder().setFailOnErrors(false).build()
        val builder = GestaltBuilder().useCacheDecorator(true).addConfigLoaders(configLoaders)
        builder.addSources(
            listOf(classPathSource, environmentSource, systemSource, javafxClassPathSource)
        )
        val gestalt = builder.build()
        gestalt.loadConfigs()
        return gestalt
    }

    @Provides
    fun preferences(): PreferencesWrapper {
        return PreferencesWrapper(Preferences.userNodeForPackage(VisualDebuggerView::class.java))
    }
}