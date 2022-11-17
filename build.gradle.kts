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

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
