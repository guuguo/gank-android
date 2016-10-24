package com.guuguo.learnsave.extension

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created by 大哥哥 on 2016/10/24 0024.
 */

fun showTipWithAction(view: View, tipText: String, actionText:String , listener: View.OnClickListener) {
    Snackbar.make(view, tipText, Snackbar.LENGTH_INDEFINITE).setAction(actionText, listener).show();
}

fun showTipWithAction(view: View, tipText: String, actionText:String , listener: View.OnClickListener, duration:Int){
    Snackbar.make(view, tipText, duration).setAction(actionText, listener).show();
}

fun showSnackTip(view: View, tipText: String) {
    Snackbar.make(view, tipText, Snackbar.LENGTH_SHORT).show();
}