import CommonPluginExtension.Companion.convertToCamelCase
import io.avaje.inject.plugin.AvajeInjectPlugin

plugins {
  application
  java
  alias(libs.plugins.jlink)
}

apply<KotlinProjectPlugin>()
apply<CommonPlugin>()
apply<FatJarPlugin>()
apply<FxProjectPlugin>()
apply<AvajeInjectPlugin>()
apply<JlinkProject>()
group = "org.visual.database"

version = "unspecified"

plugins.getPlugin(FxProjectPlugin::class.java).modules("javafx.media", "javafx.web")

application {
  mainClass = "${group}.VisualDatabase"
  mainModule.set(group.toString())
  applicationDefaultJvmArgs = commonJvmArgs + listOf("-Xmx1G")
}

dependencies {
  implementation(libs.hikariCP)
  implementation(libs.mavenResloverAPI)
  implementation(libs.mavenResloverImpl)
  implementation(libs.mavenResloverJDK21)
  implementation(libs.mavenResloverSupplier)
  implementation(libs.mavenResloverUtil)
  implementation(projects.ui.visualGraphEditor)
  implementation(projects.ui.visualTextEditor)
  implementation(projects.ui.visualI18n)
  implementation(projects.module.visualShared)
  implementation(projects.ui.visualComponent)
  implementation(libs.avajeValidaor)
  implementation(libs.avajeValidaorCodegen)
  implementation(libs.picocli)
//  implementation(projects.ui.visualCollaborative)
  implementation(projects.ui.visualDebugger)
  annotationProcessor(libs.picocliCodegen)
  implementation(libs.jacksonCore)
  implementation(libs.jacksonDatabind)
  implementation(libs.gestaltGuice)
  implementation(libs.gestaltKotlin)
  implementation(libs.jacksonAnnotations)
  implementation(rootProject.libs.avajeInject)
  annotationProcessor(rootProject.libs.avajeInjectGenerator)
  implementation(rootProject.libs.gestaltConfig)
  implementation(rootProject.libs.gestaltToml)
  testImplementation(rootProject.libs.avajeInjectTest)
}

 jlink {
    addExtraDependencies(
        "javafx",
        "kotlin",
        "jackson",
        "picocli",
        "javax"
    )
    options = listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    enableCds()
    launcher {
        noConsole = true
        name = convertToCamelCase(project.name)
        jvmArgs = commonJvmArgs
    }

 imageZip.set(project.file("${project.layout.buildDirectory}/image-zip/visual-model-image.zip"))
    jpackage { appVersion = version.toString() }
    customImage {
        jdkModules = listOf("java.desktop", "java.xml", "jdk.unsupported")
        appModules = listOf(group.toString())
    }
    mergedModule {
        //        additive = true
        excludeRequires(
            "java.compiler",
            "java.rmi",
            "java.xml.bind",
            "java.corba",
            "org.jetbrains.annotations",
            "java.xml.crypto",
            "jdk.javadoc",
            "org.junit.platform.launcher"
        )
    }
 }
