package com.guuguo.android.lib.utils

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import android.graphics.Rect
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewTreeObserver
import androidx.core.view.doOnLayout

/**[onSoftKeyBoardVisible] callback visible & keyboardHeight */
fun KeyboardLayoutCallbackUtil(owner: LifecycleOwner, onSoftKeyBoardVisible: (Boolean, Int) -> Unit) {
    if (owner !is AppCompatActivity && owner !is androidx.fragment.app.Fragment) return
    val lifecycleObserver = object : LifecycleObserver {
        var isVisiableForLast = false

        val glogalChangeListener = ViewTreeObserver.OnGlobalLayoutListener {
            val activity: Activity = (owner as? AppCompatActivity) ?: (owner as? androidx.fragment.app.Fragment)?.activity!!
            val decorView = activity.window.decorView
            val rect = Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            //计算出可见屏幕的高度
            val displayHight = rect.bottom - rect.top;
            //获得屏幕整体的高度
            val height = decorView.getHeight();
            //获得键盘高度
            val keyboardHeight = height - displayHight;
            val visible = displayHight / height.toFloat() < 0.8;
            if (visible != isVisiableForLast) {
                onSoftKeyBoardVisible(visible, keyboardHeight);
            }
            isVisiableForLast = visible;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            val activity: Activity = (owner as? AppCompatActivity) ?: (owner as? androidx.fragment.app.Fragment)?.activity!!
            activity.window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(glogalChangeListener)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreated() {
            val activity: Activity = (owner as? AppCompatActivity) ?: (owner as? androidx.fragment.app.Fragment)?.activity!!
            activity.window.decorView.viewTreeObserver.addOnGlobalLayoutListener(glogalChangeListener)
        }
    }
    owner.lifecycle.addObserver(lifecycleObserver)
}
