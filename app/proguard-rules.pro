# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:

#---------------------------------------------------------------------------基本不用动的混淆规则START-----------------------------------------------------------------------------#

# 代码混淆压缩比，在0和7之间，默认为5，一般不需要改
-optimizationpasses 5

# 混淆时不使用大小写混合，混淆后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclasses

# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers

# 不做预校验，preverify是proguard的4个步骤之一
# Android不需要preverify，去掉这一步可加快混淆速度
-dontpreverify

# 指定混淆时采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不改变
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 保护代码中的Annotation不被混淆，这在JSON实体映射时非常重要，比如fastJson
-keepattributes *Annotation*

# 避免混淆泛型，这在JSON实体映射时非常重要，比如fastJson
-keepattributes Signature

#抛出异常时保留代码行号，在异常分析中可以方便定位
-keepattributes SourceFile,LineNumberTable

-dontskipnonpubliclibraryclasses #用于告诉ProGuard，不要跳过对非公开类的处理。默认情况下是跳过的，因为程序中不会引用它们，有些情况下人们编写的代码与类库中的类在同一个包下，并且对包中内容加以引用，此时需要加入此条声明。

-dontusemixedcaseclassnames#这个是给Microsoft Windows用户的，因为ProGuard假定使用的操作系统是能区分两个只是大小写不同的文件名，但是Microsoft Windows不是这样的操作系统，所以必须为ProGuard指定-dontusemixedcaseclassnames选项

#WebView 处理
-keepclassmembers class fqcn.of.javascript.interface.for.webview {public *;}

#忽略警告
-ignorewarnings

# 针对android-support-v4.jar的解决方案
-dontwarn android.support.v4.**
-keep class android.support.v4.**  { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**

# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

# 针对android-support-v7.jar的解决方案
-dontwarn android.support.v7.**
-keep class android.support.v7.**  { *; }

# 针对 androidx 的解决方案
-dontwarn androidx.appcompat.widget.**
-keep class androidx.appcompat.widget.**  { *; }


# 有了verbose这句话，混淆后就会生成映射文件
# 包含有类名->混淆后类名的映射关系
# 然后使用printmapping指定映射文件的名称
-verbose
-printmapping proguardMapping.txtA
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#保持枚举 enum 类不被混淆
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
#避免混淆泛型 如果混淆报错建议关掉
#-keepattributes Signature

#（可选）避免Log打印输出
-assumenosideeffects class android.util.Log {
   public static *** v(...);
   public static *** d(...);
   public static *** i(...);
   public static *** w(...);
 }

  -dontwarn javax.annotation.**
  -dontwarn javax.inject.**

#---------------------------------------------------------------------------基本不用动的混淆规则END-----------------------------------------------------------------------------#



#------------------------------------------------------------------------------项目内混淆规则START--------------------------------------------------------------------------------#

#Module
-keep public class com.guuguo.gank.model.entity.** {*;}
-keep public class com.guuguo.gank.model.other.** {*;}
-keep public class com.guuguo.gank.model.** {*;}
-keep public class com.guuguo.gank.constant.** {*;}

# 保留JS方法不被混淆
-keep class com.bianla.app.js.**{*;}


#AndroidLib

#Lib
-dontwarn com.broadcom.**
-dontwarn com.samsumg.android.sdk.**
-dontwarn org.apache.commons.**
-keep class cn.com.bodivis.bodysdk.**{*;}
-keep class com.hyphenate.easeui.**{*;}
#-------------------------------------------------------------------------------项目内混淆规则END---------------------------------------------------------------------------------#



#-----------------------------------------------------------------------------三方SDK混淆规则START-------------------------------------------------------------------------------#

#agentweb
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**
-keepclassmembers class com.just.agentweb.sample.common.AndroidInterface{ *; }

#微信SDK 混淆方案
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** { *;}

#高德地图混淆
-dontwarn com.amap.api.maps.UnityGLRenderer
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
-keep   class com.amap.api.services.**{*;}

#支付宝钱包
-dontwarn com.alipay.**
-dontwarn HttpUtils.HttpFetcher
-dontwarn com.ta.utdid2.**
-dontwarn com.ut.device.**
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.mobilesecuritysdk.*
-keep class com.ut.*

#Glide混淆
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {**[] $VALUES;public *;}

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#忽略Kotlin警告
-dontwarn kotlin.**

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *;}

#eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}

#agentweb
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#AVI Loading
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

#视频播放
-dontwarn tv.danmaku.ijk.**
-dontwarn com.shuyu.gsyvideoplayer.**
-keep class tv.danmaku.ijk.** { *; }
-keep class com.shuyu.gsyvideoplayer.** { *; }

-keep class com.kyleduo.switchbutton.**{*;}

#UMeng
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**
-dontwarn com.meizu.**
-keepattributes *Annotation*
-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class com.meizu.** {*;}
-keep class org.apache.thrift.** {*;}
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}
-keep public class **.R$*{
   public static final int *;
}
 #HMS 华为推送
# -ignorewarning
 -keepattributes *Annotation*
 -keepattributes Exceptions
 -keepattributes InnerClasses
 -keepattributes Signature
 -keepattributes SourceFile,LineNumberTable
 -keep class com.hianalytics.android.**{*;}
 -keep class com.huawei.updatesdk.**{*;}
 -keep class com.huawei.hms.**{*;}

 #阿里web安全
 -keep class com.taobao.securityjni.**{*;}
 -keep class com.taobao.wireless.security.**{*;}
 -keep class com.ut.secbody.**{*;}
 -keep class com.taobao.dp.**{*;}
 -keep class com.alibaba.wireless.security.**{*;}

 # OkHttp3
 -dontwarn okhttp3.logging.**
 -dontwarn okio.**
 -keep class okhttp3.internal.**{*;}
 # Retrofit
 -dontwarn retrofit2.**
 -keep class retrofit2.** { *; }
 -keepclassmembernames,allowobfuscation interface * {
     @retrofit2.http.* <methods>;
 }
 -dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
 # RxJava RxAndroid
 -dontwarn sun.misc.**
 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
     long producerIndex;
     long consumerIndex;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode producerNode;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
 }

 #huanxin
 -dontwarn  com.hyphenate.**
 -keep class com.hyphenate.** {*;}
 -keep class com.superrtc.** {*;}
 -keep class com.baidu.**.{*;}

 #RxCache
 -dontwarn io.rx_cache2.internal.**
 -keepclassmembers enum io.rx_cache2.Source { *; }
 -keepclassmembernames class * { @io.rx_cache2.* <methods>; }

 #BaseQuickAdapter混淆
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}
 #Arouter
 -keep public class com.alibaba.android.arouter.routes.**{*;}
 -keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
 # 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
 -keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
 # 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
 -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

#AVLoadingIndicatorView
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

#PictureSelector 2.0
-keep class com.luck.picture.lib.** { *; }

-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }


 #--------------------------------------------------------------------------------三方SDK混淆规则END---------------------------------------------------------------------------------#

