buildscript {

    extra["compose_version"] = "1.4.0-alpha02"
    extra["ktor_version"] = "2.1.3"

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.21")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.7.21")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
