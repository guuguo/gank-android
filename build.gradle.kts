import org.gradle.internal.impldep.aQute.bnd.osgi.Constants.options

buildscript {

    repositories {
        google()
        jcenter()
        addRepos(repositories)
    }

    dependencies {
        classpath(BuildPlugins.androidGradlePlugin)
        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath("com.novoda:bintray-release:0.9")
    }
}

allprojects {
    repositories {
        addRepos(repositories)
        flatDir {
            dirs("libs")
        }

        maven { setUrl("http://www.guuguo.top/") }
        maven { setUrl("http://dl.bintray.com/kotlin/kotlin-eap") }
    }
}
tasks.register("clean").configure {
    delete("build")
}
