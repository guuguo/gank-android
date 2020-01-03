package com.guuguo.android.lib.widget.simpleview

import android.view.View
import android.view.View.OnClickListener
import com.guuguo.android.lib.widget.R


/**
 * 自定义要切换的布局，通过IVaryViewHelper实现真正的切换<br></br>
 * 使用者可以根据自己的需求，使用自己定义的布局样式

 * @author guuguo
 */
class SimpleViewHelper(private val helper: VaryViewHelper) {

    //    var simpleView: SimpleView? = null
//        private set
    var isWrapContent: Boolean = true

    constructor(view: View) : this(VaryViewHelper(view))


    fun showView(view: View) {
        helper.showLayout(view)
    }

    var simpleView: SimpleView? = null
    var viewHolder: SimpleViewHolder? = null


    fun showError(text: String, btnText: String? = "", listener: OnClickListener? = null, imgRes: Int = R.drawable.widget_state_error) {
        showState(text, btnText, listener, imgRes)
    }

    fun showEmpty(text: String, btnText: String? = "", listener: OnClickListener?=null, imgRes: Int) {
        showState(text, btnText, listener, imgRes)
    }

    fun showText(text: String) {
        showState(text)
    }


    fun showState(text: String, btnText: String? = "", listener: OnClickListener? = null, imgRes: Int = 0) {
        showSimpleView().state(text, imgRes, btnText, listener)
    }

    fun showLoading(message: String) {
        showSimpleView().loading(message)
    }

    fun restore() {
        helper.restoreView()
    }


    private fun showSimpleView(): SimpleViewHolder {
        if (simpleView == null) {
            simpleView = SimpleView(helper.context)
            viewHolder = simpleView!!.viewHolder
        }
        helper.showLayout(simpleView!!.view)
        return viewHolder!!
    }

}
