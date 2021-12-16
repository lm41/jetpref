buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1")
    }
}

task("clean") {
    delete(rootProject.buildDir)
}
