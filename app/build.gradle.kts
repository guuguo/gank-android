import Deps.dagger.android
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
}
fun gitVersionCode() = currentTime()//Date().format("yyMMdd", java.util.TimeZone.getTimeZone("UTC")).toInteger()

android {
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        applicationId = "com.guuguo.gank"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = gitVersionCode()
        versionName = "1.6"
        ndk {
            //设置支持的SO库架构
            abiFilters("armeabi", "x86", "armeabi-v7a", "x86_64", "arm64-v8a")
        }
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
    }

    signingConfigs {
        create("config") {
            keyAlias = "mimi"
            keyPassword = "guuguo123"
            storeFile = file("mimi.jks")
            storePassword = "android"
        }
    }

    buildTypes {

        getByName("debug") {
            versionNameSuffix = "d"
            isMinifyEnabled = false
            isShrinkResources = false
            isZipAlignEnabled = true
            signingConfig = signingConfigs.getByName("config")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            isZipAlignEnabled = true
            signingConfig = signingConfigs.getByName("config")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    applicationVariants.all { variant ->
        variant.outputs.all { output ->
            val fileName = output.outputFile.name
            fileName.replace("app", "gank-${variant.versionName}-${variant.versionCode}")

            true
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }
    dataBinding {
        isEnabled = true
    }
    lintOptions {
        isAbortOnError = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


dependencies {
    implementation(fileTree(hashMapOf("dir" to "libs", "include" to arrayOf("*.jar"))))

    testImplementation(Deps.mockito.core)
    testImplementation(Deps.mockito.all)
    testImplementation(Deps.junit)

    androidTestImplementation(Deps.support.annotations)
    androidTestImplementation(Deps.atsl.runner)
    androidTestImplementation(Deps.atsl.rules)
    androidTestImplementation(Deps.espresso.core)
    androidTestImplementation("org.hamcrest:hamcrest-library:2.1")

//    implementation(Deps.androidlib.dividerview
//    implementation(Deps.androidlib.flowlayoutMaterialAbout
//    implementation(Deps.androidlib.lib
//    implementation(Deps.androidlib.iconfont
    implementation(Deps.androidlib.libdatabindingex)

    implementation(Deps.navigation.runtime_ktx)
    implementation(Deps.navigation.fragment_ktx)

    implementation(Deps.room.runtime)
    kapt(Deps.room.compiler)
    implementation(Deps.room.rxjava2)
    // testimplementation(Deps.room.testing)

    implementation(Deps.support.cardview)
    implementation(Deps.support.app_compat)
    implementation(Deps.support.design)
    implementation(Deps.support.recyclerview)
    implementation(Deps.androidx.vectordrawable)
    implementation(Deps.androidx.ktx)
    implementation(Deps.support.v4)
    implementation(Deps.constraint_layout)
    kapt(Deps.lifecycle.compiler)
    implementation(Deps.paging)
    implementation(Deps.multidex)
    implementation(project(":stethomodule"))

    implementation(Deps.retrofit.runtime)
    implementation(Deps.retrofit.gson)
    implementation(Deps.retrofit.rxjava2)
    implementation(Deps.retrofit.mock)

    implementation(Deps.dagger.runtime)
    implementation(Deps.dagger.android_support)
    kapt(Deps.dagger.compiler)

    implementation(Deps.agentweb.basic)
    implementation(Deps.bugly.upgrade)

    implementation(Deps.kotlin.stdlib)

    implementation("com.github.florent37:viewanimator:1.1.1")

    implementation(project(":flutter_boost"))
    implementation(project(":xservice_kit"))

    implementation("com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.44")
    implementation("com.github.bumptech.glide:glide:4.9.0")
    implementation("com.github.bumptech.glide:okhttp3-integration:4.9.0@aar")
    implementation("com.github.Lauzy:LBehavior:1.0.2")
    implementation("com.github.jrvansuita:MaterialAbout:0.1.9")
    implementation("com.github.castorflex.smoothprogressbar:library:1.1.0")
    implementation("com.github.iielse:ImageWatcher:1.1.2")
    implementation("com.tapadoo.android:alerter:3.0.1")

}

