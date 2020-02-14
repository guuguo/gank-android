// Top-level build file where you can add configuration options common to all sub-projects/modules.
import java.net.URI

buildscript {
    repositories {
        google()
        jcenter()
//        addRepos(repositories)
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.3")
        classpath(kotlin("gradle-plugin", version = "1.3.61"))
        classpath("com.novoda:bintray-release:0.9")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()

//        addRepos(repositories)
        flatDir {
            dirs("libs")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
