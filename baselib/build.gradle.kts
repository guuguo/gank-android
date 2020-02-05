plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
}
android {
    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode =(1)
        versionName =("1.0")

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    dataBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(fileTree(hashMapOf("dir" to "libs", "include" to arrayOf("*.jar"))))

//    api("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")
    api("androidx.appcompat:appcompat:1.1.0")
    api("androidx.core:core-ktx:1.1.0")
    testApi("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    api(project(":libraries:libdatabindingex"))

    api("com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.0-beta11")

    api(Deps.wildAndroidKtx)
    api(project(":stethomodule"))
    api(Deps.agentweb.basic)
    api(Deps.bugly.upgrade)
    api(Deps.kotlin.stdlib)
    api(Deps.wildAndroidKtx)
    api(Deps.rounded_imageview)
    api(Deps.paging)

    api(Deps.retrofit.runtime)
    api(Deps.retrofit.gson)
    api(Deps.retrofit.rxjava2)
    api(Deps.retrofit.mock)
    api(Deps.okhttp3)

    api(Deps.lifecycle.runtime)
    api(Deps.lifecycle.extensions)
    api(Deps.lifecycle.ktx)

    api(Deps.support.annotations)
    api(Deps.support.app_compat)
    api(Deps.support.design)
    api(Deps.support.recyclerview)
    api(Deps.androidx.vectordrawable)
    api(Deps.androidx.ktx)
    api(Deps.support.v4)
    api(Deps.constraint_layout)

    api(Deps.glide.runtime)
    api(Deps.glide.okhttp)

}
