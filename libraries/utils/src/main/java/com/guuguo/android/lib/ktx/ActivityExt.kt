package com.guuguo.android.lib.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Description: Activity相关
 * Create by lxj, at 2018/12/7
 */

infix fun Activity.snackShow(str:String){
     Snackbar.make(window.decorView,str,2000).show()
 }
