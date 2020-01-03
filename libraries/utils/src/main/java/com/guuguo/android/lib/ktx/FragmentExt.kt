package com.guuguo.android.lib.ktx

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.guuguo.android.lib.utils.LifecycleHandler

/**
 * Description: Fragment相关扩展
 * Create by dance, at 2018/12/5
 */

/**
 * fragment批处理，自动commit
 */
fun androidx.fragment.app.FragmentActivity.fragmentManager(action: androidx.fragment.app.FragmentTransaction.() -> Unit){
    supportFragmentManager.beginTransaction()
            .apply { action() }
            .commitAllowingStateLoss()
}

fun androidx.fragment.app.FragmentActivity.replace(layoutId: Int, f: androidx.fragment.app.Fragment, bundle: Array<out Pair<String, Any?>>? = null){
    if(bundle!=null)f.arguments = bundle.toBundle()
    supportFragmentManager.beginTransaction()
            .replace(layoutId, f)
            .commitAllowingStateLoss()
}

fun androidx.fragment.app.FragmentActivity.add(layoutId: Int, f: androidx.fragment.app.Fragment, bundle: Array<out Pair<String, Any?>>? = null){
    if(bundle!=null)f.arguments = bundle.toBundle()
    supportFragmentManager.beginTransaction()
            .add(layoutId, f)
            .commitAllowingStateLoss()
}

fun androidx.fragment.app.FragmentActivity.hide(f: androidx.fragment.app.Fragment){
    supportFragmentManager.beginTransaction()
            .hide(f)
            .commitAllowingStateLoss()
}

fun androidx.fragment.app.FragmentActivity.show(f: androidx.fragment.app.Fragment){
    supportFragmentManager.beginTransaction()
            .show(f)
            .commitAllowingStateLoss()
}
fun androidx.fragment.app.FragmentActivity.remove(f: androidx.fragment.app.Fragment){
    supportFragmentManager.beginTransaction()
            .remove(f)
            .commitAllowingStateLoss()
}


fun androidx.fragment.app.Fragment.replace(layoutId: Int, f: androidx.fragment.app.Fragment, bundle: Array<out Pair<String, Any?>>? = null){
    if(bundle!=null)f.arguments = bundle.toBundle()
    childFragmentManager.beginTransaction()
            .replace(layoutId, f)
            .commitAllowingStateLoss()
}

fun androidx.fragment.app.Fragment.add(layoutId: Int, f: androidx.fragment.app.Fragment, bundle: Array<out Pair<String, Any?>>? = null){
    if(bundle!=null)f.arguments = bundle.toBundle()
    childFragmentManager.beginTransaction()
            .add(layoutId, f)
            .commitAllowingStateLoss()
}

fun androidx.fragment.app.Fragment.hide(f: androidx.fragment.app.Fragment){
    childFragmentManager.beginTransaction()
            .hide(f)
            .commitAllowingStateLoss()
}

fun androidx.fragment.app.Fragment.show(f: androidx.fragment.app.Fragment){
    childFragmentManager.beginTransaction()
            .show(f)
            .commitAllowingStateLoss()
}
fun androidx.fragment.app.Fragment.remove(f: androidx.fragment.app.Fragment){
    childFragmentManager.beginTransaction()
            .remove(f)
            .commitAllowingStateLoss()
}

//post, postDelay
fun androidx.fragment.app.Fragment.post(action: ()->Unit){
    LifecycleHandler(this).post { action() }
}

fun androidx.fragment.app.Fragment.postDelay(delay:Long = 0, action: ()->Unit){
    LifecycleHandler(this).postDelayed({ action() }, delay)
}