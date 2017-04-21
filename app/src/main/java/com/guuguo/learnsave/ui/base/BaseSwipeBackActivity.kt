package com.guuguo.learnsave.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.AppLaunchChecker.onActivityCreate
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.guuguo.android.lib.app.LBaseActivity
import com.guuguo.learnsave.app.MyApplication
import me.yokeyword.swipebackfragment.LSwipeBackView
import me.yokeyword.swipebackfragment.SwipeBackLayout

/**
 * Created by guodeqing on 7/23/16.
 */

abstract class BaseSwipeBackActivity : BaseActivity(), LSwipeBackView {
    private var mSwipeBackLayout: SwipeBackLayout? = null
    private var mDefaultFragmentBackground = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onActivityCreate()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mSwipeBackLayout!!.attachToActivity(this)
    }

    override fun findViewById(id: Int): View {
        val view = super.findViewById(id)
        if (view == null && mSwipeBackLayout != null) {
            return mSwipeBackLayout!!.findViewById(id)
        }
        return view
    }

    internal fun onActivityCreate() {
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.decorView.setBackgroundDrawable(null)
        mSwipeBackLayout = SwipeBackLayout(this)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mSwipeBackLayout!!.layoutParams = params
    }

    fun getSwipeBackLayout(): SwipeBackLayout {
        return mSwipeBackLayout!!
    }

    fun setSwipeBackEnable(enable: Boolean) {
        mSwipeBackLayout!!.setEnableGesture(enable)
    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment

     * @return true: Activity可以滑动退出, 并且总是优先; false: Activity不允许滑动退出
     */
    override fun swipeBackPriority(): Boolean {
        return supportFragmentManager.backStackEntryCount <= 1
    }

    /**
     * 当Fragment根布局 没有 设定background属性时,
     * 库默认使用Theme的android:windowbackground作为Fragment的背景,
     * 如果不像使用windowbackground作为背景, 可以通过该方法改变Fragment背景。
     */
    protected fun setDefaultFragmentBackground(@DrawableRes backgroundRes: Int) {
        mDefaultFragmentBackground = backgroundRes
    }

    internal fun getDefaultFragmentBackground(): Int {
        return mDefaultFragmentBackground
    }
}
