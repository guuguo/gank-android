package com.guuguo.android.lib.widget.simpleview

import android.content.res.Resources
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.guuguo.android.drawable.CircularDrawable
import com.guuguo.android.lib.widget.R

/**
 * mimi 创造于 2017-06-17.
 * 项目 order
 */

class SimpleViewHolder(var view: View) {
    val mLayout: ViewGroup = view.findViewById(R.id.layoutEmpty)
    val mImg: ImageView = view.findViewById(R.id.imageView)
    val mLoading: View? = view.findViewById(R.id.widget_loading)
    val mTvText: TextView = view.findViewById(R.id.tv_text)
    val mBtn: TextView = view.findViewById(R.id.btn_empty)

    fun loading(msg: String, loadingDrawable: Drawable? = CircularDrawable().apply { light() }): SimpleViewHolder {
        mBtn.visibility = View.GONE
        loadingDrawable?.let {
            mImg.visibility = View.VISIBLE
            mImg.setImageDrawable(loadingDrawable)
            mImg.layoutParams.width = 50.dpToPx()
            mImg.requestLayout()
            val lDrawable = mImg.drawable
            if (lDrawable is Animatable)
                lDrawable.start()
        } ?: apply {
            mImg.visibility = View.GONE
            mLoading?.visibility = View.VISIBLE
        }
        if (msg.isEmpty())
            mTvText.visibility = View.GONE
        else
            mTvText.visibility = View.VISIBLE
        mTvText.text = msg
        return this
    }

    fun state(text: String, imgRes: Int = 0, btnText: String? = "", listener: View.OnClickListener? = null): SimpleViewHolder {
        mLoading?.visibility = View.GONE
        mImg.layoutParams.width = 100.dpToPx()
        mImg.requestLayout()
        if (imgRes == 0)
            mImg.visibility = View.GONE
        else {
            mImg.visibility = View.VISIBLE
            mImg.setImageResource(imgRes)
        }
        if (btnText.isNullOrEmpty())
            mBtn.visibility = View.GONE
        else {
            mBtn.visibility = View.VISIBLE
            mBtn.text = btnText
            mBtn.setOnClickListener(listener)
        }
        if (text.isEmpty())
            mTvText.visibility = View.GONE
        else
            mTvText.visibility = View.VISIBLE

        mTvText.text = text
        return this
    }

    fun showError(text: String, btnText: String? = "", listener: View.OnClickListener? = null, imgRes: Int = R.drawable.widget_state_error) {
        showState(text, btnText, listener, imgRes)
    }

    fun showEmpty(text: String, btnText: String? = "", listener: View.OnClickListener? = null, imgRes: Int = R.drawable.empty_cute_girl_box) {
        showState(text, btnText, listener, imgRes)
    }

    fun showText(text: String) {
        showState(text)
    }


    fun showState(text: String, btnText: String? = "", listener: View.OnClickListener? = null, imgRes: Int = R.drawable.empty_cute_girl_box) {
        state(text, imgRes, btnText, listener)
    }

    fun showLoading(message: String) {
        loading(message)
    }

    fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    }
}
