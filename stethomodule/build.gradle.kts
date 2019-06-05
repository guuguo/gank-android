import Deps.arouter.api
import Deps.dagger.android
import com.android.build.gradle.ProguardFiles.getDefaultProguardFile

plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(28)

    defaultConfig {
        minSdkVersion(15)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

}

dependencies {
    //stetho
//    debugApi("com.facebook.stetho:stetho:${STETHO_VERSION}")
//    debugApi("com.facebook.stetho:stetho-okhttp3:$STETHO_VERSION")
    api("com.squareup.okhttp3:okhttp:3.14.1")
}
