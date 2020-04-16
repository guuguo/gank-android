package com.guuguo.theme

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import org.koin.java.KoinJavaComponent.get

object ThemeUtils {
    val ARG_ISDARK = "ARG_ISDARK";
    val ARG_DARK_FOLLOW_SYSTEM = "ARG_DARK_FOLLOW_SYSTEM";
    val prefs by lazy {
        val app: Application = get(clazz = Application::class.java)
        app.getSharedPreferences("THEME_SP", MODE_PRIVATE)
    }

    fun changeToTheme(newDark: Boolean) {
        prefs.edit(true) { putBoolean(ARG_ISDARK, newDark) }
        AppCompatDelegate.setDefaultNightMode(if (newDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun enableFollowSystem(bool: Boolean) {
        prefs.edit(true) { putBoolean(ARG_DARK_FOLLOW_SYSTEM, bool) }
        if (bool) {
            AppCompatDelegate.setDefaultNightMode(if (isSystemEnableDark()) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }else{
            AppCompatDelegate.setDefaultNightMode(if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    val isDark get() = prefs.getBoolean(ARG_ISDARK, false)
    val darkFollowSystem get() = prefs.getBoolean(ARG_DARK_FOLLOW_SYSTEM, true)
    //检查当前系统是否已开启暗黑模式 true开启
    fun isSystemEnableDark(): Boolean {
        val app: Application = get(clazz = Application::class.java)
        val mode = app.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_YES;
    }

}