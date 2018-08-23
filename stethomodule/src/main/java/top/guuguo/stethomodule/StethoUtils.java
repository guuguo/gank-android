package top.guuguo.stethomodule;

import android.content.Context;

import java.lang.reflect.Method;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class StethoUtils {

    public static void init(Context context) {
        try {
            Class<?> stethoClass = Class.forName("com.facebook.stetho.Stetho");
            Method initializeWithDefaults = stethoClass.getMethod("initializeWithDefaults", Context.class);
            initializeWithDefaults.invoke(null, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static OkHttpClient.Builder configureInterceptor(OkHttpClient.Builder httpClientBuilder) {
        try {
            Class<?> aClass = Class.forName("com.facebook.stetho.okhttp3.StethoInterceptor");
            return httpClientBuilder.addNetworkInterceptor((Interceptor) aClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpClientBuilder;
    }

}
