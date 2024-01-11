plugins{
    kotlin("jvm")
    kotlin("plugin.allopen")
    kotlin("plugin.lombok")
}

dependencies{
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")
}

java {
    modularity.inferModulePath.set(true)
}

tasks.compileJava{
    options.compilerArgumentProviders.add(CommandLineArgumentProvider {
        // Provide compiled Kotlin classes to javac – needed for Java/Kotlin mixed sources to work
        listOf("--patch-module", "${project.group}=${sourceSets["main"].output.asPath}")
    })
}
