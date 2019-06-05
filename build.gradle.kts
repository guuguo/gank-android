import java.net.URI

buildscript {

    repositories {
        google()
        jcenter()
        addRepos(repositories)
    }

    dependencies {
        classpath(BuildPlugins.androidGradlePlugin)
        classpath(BuildPlugins.kotlinGradlePlugin)
    }
}

allprojects {
    repositories {
        addRepos(repositories)
        flatDir {
            dirs("libs")
        }

        maven { url = URI("http://www.guuguo.top/") }
        maven { url = URI("http://dl.bintray.com/kotlin/kotlin-eap") }
    }
}
tasks.register("clean").configure {
    delete("build")
}