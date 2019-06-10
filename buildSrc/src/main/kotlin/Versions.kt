import org.apache.tools.ant.taskdefs.Local
import org.gradle.api.artifacts.dsl.RepositoryHandler
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

private object versions {
    const val arch_core = "1.1.1"
    const val room = "2.1.0-alpha01"
    const val lifecycle = "2.0.0"
    const val support = "1.0.0"
    const val androidx = "1.0.0"
    const val ktx = "1.0.0"

    const val design = "1.0.0"
    const val gson = "2.8.2"
    const val dagger = "2.15"
    const val junit = "4.12"
    const val espresso = "3.1.0-beta02"
    const val retrofit = "2.4.0"
    const val okhttp_logging_interceptor = "3.12.1"
    const val mockwebserver = "3.8.1"
    const val apache_commons = "2.5"
    const val mockito = "2.7.19"
    const val mockito_all = "1.10.19"
    const val dexmaker = "2.2.0"
    const val constraint_layout = "2.0.0-alpha2"
    const val glide = "4.8.0"
    const val timber = "4.5.1"
    const val android_gradle_plugin = "3.3.1"
    const val rxjava2 = "2.1.3"
    const val rx_android = "2.0.1"
    const val rx_kotlin = "2.2.0"
    const val atsl_runner = "1.1.0-beta02"
    const val atsl_rules = "1.0.1"
    const val hamcrest = "1.3"
    const val kotlin = "1.3.11"
    const val paging = "1.0.1"
    const val navigation = "1.0.0-alpha04"
    const val work = "1.0.0-alpha08"
    const val arouter_api = "1.4.1"
    const val arouter_compiler = "1.2.2"
    const val qmui = "1.1.3"
    const val agentweb = "4.0.2"
    const val matisse = "0.5.1"
    const val multidex = "2.0.0"
    const val rx_permissions = "0.9.5"
    const val persistent_cookie_jar = "v1.0.1"
    const val convenient_banner = "2.1.4"
    const val smart_refresh_layout = "1.1.0-alpha-12"
    const val statusbar = "1.5.1"
    const val rx_binding = "2.2.0"
    const val xiaojunKtx="1.2.10"
    const val androidlib = "1.0.4"
    const val dividerview = "1.0.2"
    const val flowlayout = "1.14.0"
    const val exoplayer = "2.8.0"
    const val stetho = "1.5.1"
    const val bugly = "1.4.0"

}

object BuildPlugins {
    private object versions {
        const val buildToolsVersion = "3.3.1"
        const val kotlinVersion = "1.3.21"
        const val navigationSafeArgsVersion = "1.0.0"
    }

    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"

    const val androidGradlePlugin = "com.android.tools.build:gradle:${versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kotlinVersion}"
}

object AndroidSdk {
    const val min = 17
    const val compile = 28
    const val target = compile
}

object Deps {

    object support {
        const val annotations = "androidx.annotation:annotation:${versions.support}"
        const val app_compat = "androidx.appcompat:appcompat:${versions.support}"
        const val recyclerview = "androidx.recyclerview:recyclerview:${versions.design}"
        const val recyclerview_selection = "androidx.recyclerview:recyclerview-selection:${versions.design}"
        const val cardview = "androidx.cardview:cardview:${versions.design}"
        const val design = "com.google.android.material:material:${versions.support}"
        const val v4 = "androidx.legacy:legacy-support-v4:${versions.support}"
        const val core_utils = "androidx.legacy:legacy-support-core-utils:${versions.support}"
    }

    object androidx {
        const val ktx = "androidx.core:core-ktx:${versions.ktx}"
        const val vectordrawable = "androidx.vectordrawable:vectordrawable:${versions.support}"
    }

    object stetho {
        const val runtime = "com.facebook.stetho:stetho:${versions.stetho}"
        const val okhttp3 = "com.facebook.stetho:stetho-okhttp3:${versions.stetho}"
    }

    object room {
        const val runtime = "androidx.room:room-runtime:${versions.room}"
        const val compiler = "androidx.room:room-compiler:${versions.room}"
        const val rxjava2 = "androidx.room:room-rxjava2:${versions.room}"
        const val testing = "androidx.room:room-testing:${versions.room}"
    }


    object lifecycle {
        const val runtime = "androidx.lifecycle:lifecycle-runtime:${versions.lifecycle}"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:${versions.lifecycle}"
        const val java8 = "androidx.lifecycle:lifecycle-common-java8:${versions.lifecycle}"
        const val compiler = "androidx.lifecycle:lifecycle-compiler:${versions.lifecycle}"
    }

    object arch_core {
        const val testing = "android.arch.core:core-testing:${versions.arch_core}"
    }

    object retrofit {
        const val runtime = "com.squareup.retrofit2:retrofit:${versions.retrofit}"
        const val gson = "com.squareup.retrofit2:converter-gson:${versions.retrofit}"
        const val rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${versions.retrofit}"
        const val mock = "com.squareup.retrofit2:retrofit-mock:${versions.retrofit}"
    }

    val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${versions.okhttp_logging_interceptor}"

    object dagger {
        const val runtime = "com.google.dagger:dagger:${versions.dagger}"
        const val android = "com.google.dagger:dagger-android:${versions.dagger}"
        const val android_support = "com.google.dagger:dagger-android-support:${versions.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${versions.dagger}"
        const val android_support_compiler = "com.google.dagger:dagger-android-processor:${versions.dagger}"
    }

    object espresso {
        const val core = "androidx.test.espresso:espresso-core:${versions.espresso}"
        const val contrib = "androidx.test.espresso:espresso-contrib:${versions.espresso}"
        const val intents = "androidx.test.espresso:espresso-intents:${versions.espresso}"
    }

    object atsl {
        const val runner = "androidx.test:runner:${versions.atsl_runner}"
        const val rules = "androidx.test:rules:${versions.atsl_runner}"
    }

    object mockito {
        const val core = "org.mockito:mockito-core:${versions.mockito}"
        const val all = "org.mockito:mockito-all:${versions.mockito_all}"
    }

    object kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions.kotlin}"
        const val test = "org.jetbrains.kotlin:kotlin-test-junit:${versions.kotlin}"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kotlin}"
        const val allopen = "org.jetbrains.kotlin:kotlin-allopen:${versions.kotlin}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${versions.kotlin}"
    }

    object glide {
        const val runtime = "com.github.bumptech.glide:glide:${versions.glide}"
        const val compiler = "com.github.bumptech.glide:compiler:${versions.glide}"
        const val okhttp = "com.github.bumptech.glide:okhttp3-integration:${versions.glide}"
    }

    object work {
        const val runtime = "android.arch.work:work-runtime:${versions.work}"
        const val testing = "android.arch.work:work-testing:${versions.work}"
        const val firebase = "android.arch.work:work-firebase:${versions.work}"
        const val runtime_ktx = "android.arch.work:work-runtime-ktx:${versions.work}"
    }

    object navigation {
        const val runtime = "android.arch.navigation:navigation-runtime:${versions.navigation}"
        const val runtime_ktx = "android.arch.navigation:navigation-runtime-ktx:${versions.navigation}"
        const val fragment = "android.arch.navigation:navigation-fragment:${versions.navigation}"
        const val fragment_ktx = "android.arch.navigation:navigation-fragment-ktx:${versions.navigation}"
        const val safe_args_plugin = "android.arch.navigation:navigation-safe-args-gradle-plugin:${versions.navigation}"
        const val testing_ktx = "android.arch.navigation:navigation-testing-ktx:${versions.navigation}"
    }

    object arouter {
        const val api = "com.alibaba:arouter-api:${versions.arouter_api}"
        const val compiler = "com.alibaba:arouter-compiler:${versions.arouter_compiler}"
    }

    object exoplayer {
        const val core = "com.google.android.exoplayer:exoplayer-core:${versions.exoplayer}"
        const val dash = "com.google.android.exoplayer:exoplayer-dash:${versions.exoplayer}"
        const val ui = "com.google.android.exoplayer:exoplayer-ui:${versions.exoplayer}"
    }

    object agentweb {
        const val basic = "com.just.agentweb:agentweb:${versions.agentweb}"
        const val download = "com.just.agentweb:download:${versions.agentweb}"
        const val filechooser = "com.just.agentweb:filechooser:${versions.agentweb}"
    }

    object androidlib {
        const val lib = "com.guuguo.android:androidlib:${versions.androidlib}"
        const val iconfont = "com.guuguo.android:iconfont:${versions.androidlib}"
        const val dividerview = "com.github.guuguo:dividerView:${versions.dividerview}"
        const val flowlayout = "com.github.guuguo:flowlayout:${versions.flowlayout}"
        const val libdatabindingex = "com.guuguo.android:libdatabindingex:${versions.androidlib}"
    }

    object rx_binding {
        const val rx_binding = "com.jakewharton.rxbinding2:rxbinding-kotlin:${versions.rx_binding}"
        const val rx_binding_v4 = "com.jakewharton.rxbinding2:rxbinding-support-v4-kotlin:${versions.rx_binding}"
        const val rx_binding_v7 = "com.jakewharton.rxbinding2:rxbinding-appcompat-v7-kotlin:${versions.rx_binding}"
        const val rx_binding_design = "com.jakewharton.rxbinding2:rxbinding-design-kotlin:${versions.rx_binding}"
        const val rx_binding_recyclerview =
            "com.jakewharton.rxbinding2:rxbinding-recyclerview-v7-kotlin:${versions.rx_binding}"
    }
    object bugly{
        const val upgrade = "com.tencent.bugly:crashreport_upgrade:${versions.bugly}"

    }
    const val wildAndroidKtx = "com.lxj:androidktx:${versions.xiaojunKtx}-x"
    const val dexmaker = "com.linkedin.dexmaker:dexmaker-mockito:${versions.dexmaker}"

    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${versions.constraint_layout}"

    const val timber = "com.jakewharton.timber:timber:${versions.timber}"

    const val junit = "junit:junit:${versions.junit}"

    const val mock_web_server = "com.squareup.okhttp3:mockwebserver:${versions.mockwebserver}"

    const val paging = "android.arch.paging:runtime:${versions.paging}"

    const val hamcrest = "org.hamcrest:hamcrest-all:${versions.hamcrest}"

    const val android_gradle_plugin = "com.android.tools.build:gradle:${versions.android_gradle_plugin}"

    const val qmui = "com.qmuiteam:qmui:${versions.qmui}"

    const val matisse = "com.zhihu.android:matisse:${versions.matisse}"

    const val multidex = "androidx.multidex:multidex:${versions.multidex}"

    const val rx_permissions = "com.tbruyelle.rxpermissions2:rxpermissions:${versions.rx_permissions}"

    const val persistent_cookie_jar = "com.github.franmontiel:PersistentCookieJar:${versions.persistent_cookie_jar}"

    const val convenient_banner = "com.bigkoo:ConvenientBanner:${versions.convenient_banner}"

    const val smart_refresh_layout = "com.scwang.smartrefresh:SmartRefreshLayout:${versions.smart_refresh_layout}"

    const val statusbar = "com.jaeger.statusbarutil:library:${versions.statusbar}"

    const val rxjava2 = "io.reactivex.rxjava2:rxjava:${versions.rxjava2}"
    const val rx_kotlin = "io.reactivex.rxjava2:rxkotlin:${versions.rx_kotlin}"
    const val rx_android = "io.reactivex.rxjava2:rxandroid:${versions.rx_android}"

    const val gson = "com.google.code.gson:gson:${versions.gson}"


    object build_versions {
        const val min_sdk = 21
        const val target_sdk = 28
        const val build_tools = "27.0.3"
    }
}

fun addRepos(handler: RepositoryHandler) {
    handler.maven { url = URI("http://maven.aliyun.com/nexus/content/groups/public") }
    handler.maven { url = URI("http://maven.aliyun.com/nexus/content/repositories/releases") }
    handler.maven { url = URI("https://dl.bintray.com/guuguo/maven") }
    handler.google()
    handler.jcenter()
    handler.mavenCentral()
    handler.maven { url = URI("https://jitpack.io") }
}

fun currentTime() = SimpleDateFormat("yyMMdd", Locale.CHINESE).format(Date()).toInt()
