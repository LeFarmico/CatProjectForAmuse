buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(BuildPlugins.androidTools)
        classpath(BuildPlugins.kotlinGradle)
        classpath(BuildPlugins.kotlinSerialization)
    }
}
plugins {
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
