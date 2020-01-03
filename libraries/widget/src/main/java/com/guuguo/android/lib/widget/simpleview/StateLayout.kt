package com.guuguo.android.lib.widget.simpleview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.guuguo.android.drawable.CircularDrawable
import com.guuguo.android.drawable.IDrawableTheme
import com.guuguo.android.lib.widget.R

/**
 * guuguo 创造于 16/5/28.
 * 项目 androidLib
 */
class StateLayout : FrameLayout {

    var contentView: View? = null
    var currentView: View? = null
    var simpleView: View? = null
    var viewHolder: SimpleViewHolder? = null
    var customView: View? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @Deprecated(message = "名字意义不清楚", replaceWith = ReplaceWith("showError()"))
    fun showErrorWithImage(text: String, btnText: String? = "", listener: OnClickListener? = null, imgRes: Int = R.drawable.widget_state_error) {
        showState(text, btnText, listener, imgRes)
    }

    @Deprecated(message = "名字意义不清楚", replaceWith = ReplaceWith("showEmpty()"))
    fun showEmptyWithImage(text: String, btnText: String? = "", listener: OnClickListener, imgRes: Int) {
        showState(text, btnText, listener, imgRes)
    }

    fun showError(text: String, btnText: String? = "", listener: OnClickListener? = null, imgRes: Int = R.drawable.widget_state_error) {
        showState(text, btnText, listener, imgRes)
    }

    @Deprecated(message = "参数顺序不合理", replaceWith = ReplaceWith("showEmpty()"))
    fun showEmpty(text: String, btnText: String? = "", listener: OnClickListener? = null, imgRes: Int) {
        showState(text, btnText, listener, imgRes)
    }

    fun showEmpty(text: String, imgRes: Int = R.drawable.empty_cute_girl_box, btnText: String? = "", listener: OnClickListener? = null) {
        showState(text, btnText, listener, imgRes)
    }

    fun showText(text: String) {
        showState(text)
    }

    fun showCustomView(layout: Int): StateLayout {
        isLoading = false
        initContentView()
        val customView = LayoutInflater.from(context).inflate(layout, this, false)
        showCustomView(customView)
        return this
    }

    fun showCustomView(v: View): StateLayout {
        isLoading = false
        initContentView()
        customView = v
        if (customView != currentView) {
            removeAllViews()
            addView(customView!!)
            currentView = customView
        }
        return this
    }

    fun showState(text: String, btnText: String? = "", listener: OnClickListener? = null, imgRes: Int = 0) {
        isLoading = false
        showSimpleView().state(text, imgRes, btnText, listener)
    }

    var isLoading = false
    @Deprecated("loadingDrawable 由静态loadingDrawableClass 控制")
    fun showLoading(message: String, loadingDrawable: Drawable) {
        isLoading = true
        showSimpleView().loading(message, loadingDrawable)
    }

    fun showLoading(message: String, isDark: Boolean = false) {
        isLoading = true
        showSimpleView().loading(message, loadingDrawableClass?.newInstance().apply { if (this is IDrawableTheme) if (!isDark) light() else dark() })
    }

    companion object {
        /**当设置为空的时候 加载时默认的progressbar*/
        var loadingDrawableClass: Class<out Drawable>? = CircularDrawable::class.java
    }

    fun restore() {
        isLoading = false
        initContentView()
        showContentView()
    }

    var layoutRes = R.layout.widget_include_simple_empty_view

    private fun showSimpleView(): SimpleViewHolder {
        initContentView()

        if (simpleView == null) {
            simpleView = LayoutInflater.from(context).inflate(layoutRes, this, false)
            viewHolder = SimpleViewHolder(simpleView!!)
        }
        if (simpleView != currentView) {
            removeAllViews()
            addView(simpleView!!)
            currentView = simpleView
        }
        return viewHolder!!
    }

    private fun showContentView() {
        if (contentView != currentView) {
            removeAllViews()
            addView(contentView!!)
            currentView = contentView
        }
    }

    private fun initContentView() {
        if (contentView == null) {
            contentView = getChildAt(0)
            currentView = contentView
        }
    }

    override fun addView(child: View) {
        if (childCount > 0) {
            throw IllegalStateException("StateLayout can host only one direct child")
        }
        super.addView(child, -1)
    }

    override fun addView(child: View, index: Int) {
        if (childCount > 0) {
            throw IllegalStateException("StateLayout can host only one direct child")
        }
        super.addView(child, index)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        if (childCount > 0) {
            throw IllegalStateException("StateLayout can host only one direct child")
        }
        super.addView(child, params)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (childCount > 0) {
            throw IllegalStateException("StateLayout can host only one direct child")
        }
        super.addView(child, index, params)
    }

}