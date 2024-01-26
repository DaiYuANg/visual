pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        google()
        maven { setUrl("https://jitpack.io") }
    }

    plugins {
        id("com.gradle.enterprise") version "3.13.4"
//    id("org.danilopianini.gradle-pre-commit-git-hooks") version gradlePreCommitGitGooksVersion
//    id("org.javamodularity.moduleplugin") version modulepluginVersion
//    id("org.openjfx.javafxplugin") version javafxPluginVersion
//    id("org.beryx.jlink") version jlinkVersion
//    id("io.freefair.lombok") version lombokPluginVersion
//    id("com.diffplug.spotless") version spotlessPluginVersion
//    id("com.gorylenko.gradle-git-properties") version gitPropertiesVersion
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    id("com.gradle.enterprise")
//  id("org.danilopianini.gradle-pre-commit-git-hooks")
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

buildCache {
    local {
        isEnabled = true
        directory = File(rootProject.projectDir, ".gradle/build-cache")
    }
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

rootProject.name = "visual-model"
include("app:visual-model-designer")
include("module:visual-model-database")
include("ui:visual-model-component")
include("module:visual-model-shared")
include("module:visual-model-git")
include("module:visual-model-i18n")
include("app:visual-model-debugger")
include("ui:visual-model-graph-editor")
include("libs:jfa")
include("serialize:visual-model-serialize-json")
include("libs:event")
include("libs:fonts")
include("ui:visual-model-component-annotation")
include("serialize:visual-model-serialize-plantuml")
include("serialize:visual-model-serialize-api")
include("module:visual-model-server")
include("website")
include("app:visual-model-database")
include("ui:visual-model-text-editor")
findProject(":ui:visual-model-text-editor")?.name = "visual-model-text-editor"
