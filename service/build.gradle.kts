plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
}
android {
    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

}

dependencies {
    implementation(fileTree(hashMapOf("dir" to "libs", "include" to arrayOf("*.jar"))))
    api("androidx.appcompat:appcompat:1.1.0")
    api("androidx.core:core-ktx:1.1.0")
    testApi("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    api(project(":libraries:libdatabindingex"))

    api("com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.44")

    api(Deps.retrofit.runtime)
    api(Deps.retrofit.gson)
    api(Deps.retrofit.rxjava2)
    api(Deps.retrofit.mock)
    api(Deps.okhttp3)

}
