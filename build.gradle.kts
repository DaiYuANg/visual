import com.diffplug.gradle.spotless.SpotlessPlugin
import io.freefair.gradle.plugins.lombok.LombokPlugin
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

val javaVersion :String by project
plugins {
  java
  checkstyle
  jacoco
  idea
  id("io.freefair.lombok") apply false
  id("com.diffplug.spotless")
  id("me.champeau.jmh") version "0.7.1"
  kotlin("jvm")
  kotlin("plugin.lombok") apply false
}

allprojects {
  repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    google()
    maven { setUrl("https://jitpack.io") }
    maven {
      setUrl(
          "https://gitlab.com/api/v4/projects/26584840/packages/maven") /* gitlab-supernaut-maven */
    }
  }
}

subprojects {
  apply {
    apply<KotlinPluginWrapper>()
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply<JavaPlugin>()
    apply<IdeaPlugin>()
    apply<LombokPlugin>()
    apply<SpotlessPlugin>()
  }

  dependencies {
    val junitVersion: String by project
    val slf4jVersion: String by project
    val logbackVersion: String by project
    val guiceVersion: String by project
    val guavaVersion: String by project
    val jbAnnotationVersion: String by project
    val rxjavaVersion: String by project
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.jetbrains:annotations:$jbAnnotationVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
  }
  tasks {
    withType<JavaCompile> {
      options.encoding = Charsets.UTF_8.name()
      dependsOn(rootProject.tasks.spotlessApply.name)
    }
    withType<Test> { useJUnitPlatform() }
  }

  java {
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
    targetCompatibility = JavaVersion.toVersion(javaVersion)
  }

  kotlin{
    target {
      jvmToolchain(javaVersion.toInt())
    }
  }
}

spotless {
  format("misc") {
    target("*.md", ".gitignore", "*.properties")
    indentWithTabs()
    endWithNewline()
    clearSteps()
  }
  kotlinGradle {
    target("*.gradle.kts")
    ktfmt()
  }
  java {
    target("**/*.java")
    palantirJavaFormat()
    removeUnusedImports()
    importOrder()
    eclipse()
    formatAnnotations()
  }
  groovy {
    target("**/*.groovy")
    importOrder()
    greclipse()
  }
  format("styling") {
    target("**/*/*.scss")
    prettier()
  }
  antlr4 {
    target("src/*/antlr4/**/*.g4") // default value, you can change if you want
    antlr4Formatter() // has its own section below
    //        licenseHeader '/* (C) $YEAR */' // or licenseHeaderFile
  }
}
