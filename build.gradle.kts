// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20-Beta")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
    }

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20-Beta" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
//    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}