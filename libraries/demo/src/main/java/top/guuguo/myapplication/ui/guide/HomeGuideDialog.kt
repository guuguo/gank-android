package com.bianla.app.widget.dialog

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.doOnNextLayout
import androidx.core.view.updateLayoutParams
import com.github.florent37.viewanimator.ViewAnimator
import com.guuguo.android.dialog.base.BaseDialog
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.dpToPxF
import com.guuguo.android.lib.systembar.SystemBarHelper
import com.guuguo.android.lib.utils.DisplayUtil
import com.guuguo.android.lib.widget.GuideHighLightBgView
import com.guuguo.android.lib.widget.ShadowFrameLayout
import top.guuguo.myapplication.R
import top.guuguo.myapplication.ui.guide.GuideType
import top.guuguo.myapplication.ui.guide.IGuideType

/**
 * PackageName com.bianla.app.widget.dialog
 * Created by admin on 2016/11/29.
 */

class HomeGuideDialog(var activity: Activity, var targetView: View, var guildType: IGuideType) : BaseDialog<HomeGuideDialog>(activity) {

    fun getLayoutResId() = R.layout.home_layout_guild_home_without_scale
    var setUpCustomView: ((Rect, ViewGroup) -> Unit)? = null
    var padding = 0.dpToPx()
    var anchorMargin = 0.dpToPx()
    var anchorOffset = 0.dpToPx()
    /**0没有，1圆圈，2方形*/
    var targetShape = 2
    private var targetWidth = 0
    private var targetHeight = 0

    fun initSize(width: Int, height: Int) = also {
        it.targetWidth = width
        it.targetHeight = height
    }

    override fun setUiBeforShow() {

        findViewById<View>(R.id.iv_i_know).setOnClickListener {
            dismiss()
        }

        val sflMsg = findViewById<ShadowFrameLayout>(R.id.sfl_msg)
        val circleView = findViewById<ImageView>(R.id.iv_target_circle)
        val guideContent = findViewById<GuideHighLightBgView>(R.id.guild_content)
        val tvHint = findViewById<TextView>(R.id.tv_hint)
        val container = findViewById<FrameLayout>(R.id.container)
        guideContent.setOnClickListener {
            dismiss()
        }
        setBeforeShow(this)
        targetView.requestLayout()
        targetView.doOnNextLayout {
            it.apply {
                if (targetWidth == 0)
                    targetWidth = width
                if (targetHeight == 0)
                    targetHeight = height

                val circleWidth = targetWidth + padding * 2
                val circleHeight = targetHeight + padding * 2

                ///传进来的view的位置
                val location = IntArray(2)
                getLocationOnScreen(location)

                ///targetView的重中点位置
                val lCx = location[0] + width / 2
                val lCY = location[1] + height / 2
                guideContent.targetCX = lCx.toFloat()
                guideContent.targetCY = lCY.toFloat()

                when (targetShape) {
                    1 -> {
                        guideContent.isCircle = true
                        guideContent.targetRadius = Math.sqrt(circleWidth * circleWidth + circleHeight * circleHeight.toDouble()).toFloat() / 2
                    }
                    2 -> {
                        guideContent.isCircle = false
                        guideContent.targetWidth = circleWidth
                        guideContent.targetHeight = circleHeight
                    }
                }
                guideContent.invalidate()

                val mLeft = lCx - circleWidth / 2
                var mTop = 0
                var mBottom = 0

                val bgLocation = IntArray(2)
                guideContent.getLocationOnScreen(bgLocation)
                //因为 topMargin 改变,measuredHeight 不会马上改变，所以直接测量计算的值拿来用
                var contentHeight = guideContent.measuredHeight
                //如果背景顶部是状态栏高度，则 margin 等于负的状态栏高度，适配三星部分手机状态栏顶不上去
                if (bgLocation[1] == SystemBarHelper.getStatusBarHeight(mContext)) {
                    container.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        contentHeight += SystemBarHelper.getStatusBarHeight(context)
                        topMargin = -SystemBarHelper.getStatusBarHeight(mContext)
                    }
                }

                ///圈圈在下面的时候
                if (lCY > getScreenDisplayHeight() / 2) {
                    guideContent.removeAllViews()
                    guideContent.addView(sflMsg)
                    guideContent.addView(circleView)
                    mTop = 0
                    guideContent.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                    mBottom = contentHeight - (location[1] + measuredHeight / 2) - circleHeight / 2

                    sflMsg.triangleAlign = ShadowFrameLayout.TRIANGLE_ALIGN_BOTTOM
                    sflMsg.updateLayoutParams<LinearLayout.LayoutParams> {
                        bottomMargin = anchorMargin
                    }
                } else {
                    mBottom = 0
                    mTop = lCY - circleHeight / 2
                    sflMsg.updateLayoutParams<LinearLayout.LayoutParams> {
                        topMargin = anchorMargin
                    }
                }
                when {
                    ///卡片提示文字 宽度小的时候，直接targetView居中显示
                    sflMsg.measuredWidth / 2 < Math.min(lCx, guideContent.measuredWidth - lCx) -> {
                        sflMsg.updateLayoutParams<LinearLayout.LayoutParams> {
                            gravity = Gravity.START
                            leftMargin = lCx - sflMsg.measuredWidth / 2
                        }
                        sflMsg.triangleStartExtent = sflMsg.measuredWidth / 2f
                    }

                    ///卡片提示文字 在左边的时候
                    lCx < DisplayUtil.getScreenWidth() / 2 -> {
                        sflMsg.updateLayoutParams<LinearLayout.LayoutParams> {
                            gravity = Gravity.START
                        }
                        sflMsg.triangleStartExtent = lCx.toFloat() + anchorOffset

                        ///在右边的时候
                    }
                    else -> {
                        sflMsg.updateLayoutParams<LinearLayout.LayoutParams> {
                            gravity = Gravity.END
                        }
                        sflMsg.triangleStartExtent = lCx.toFloat() - guideContent.measuredWidth + sflMsg.measuredWidth + anchorOffset
                    }
                }
                ///tvHint 最大宽度减掉各种margin
                tvHint.maxWidth = guideContent.measuredWidth - 30.dpToPx() - 27.dpToPx() - 47.5f.dpToPx().toInt()
                setUpCustomView?.invoke(Rect(location[0], location[1], location[0] + targetWidth, location[1] + targetHeight), container)
                circleView.updateLayoutParams<LinearLayout.LayoutParams> {
                    leftMargin = mLeft
                    topMargin = mTop
                    bottomMargin = mBottom
                    width = circleWidth
                    height = circleHeight
                }

            }
        }

    }

    override fun onCreateView(): View {
        val view = layoutInflater.inflate(getLayoutResId(), null)
        widthRatio(1f)
        heightRatio(1f)
        dimEnabled(false)
        return view
    }

    private var mWidth: Int = 0

    //guid_type
    companion object {
        /**
         *
         * @param type Int  引导弹窗类型
         * @param userState IHomeUserState? 为空的时候不检测存在性
         * @param showCall ((type: Int) -> Boolean) 返回true  才说明成功弹了，否则没成功，不更新标志位
         */
        fun checkGuideShow(type: IGuideType, showCall: ((type: IGuideType) -> Boolean)?) {
            if (!type.hasShow()) {
                if (showCall?.invoke(type) == true) {
                    type.hasShow(true)
                }
            }
        }

        fun setBeforeShow(dialog: HomeGuideDialog) {
            dialog.apply {
                val hint = findViewById<TextView>(R.id.tv_hint)
                val circleView = findViewById<ImageView>(R.id.iv_target_circle)
                guildType.beforeShow(this)
                hint.text = guildType.getTextStr()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss()
        }
        return super.onKeyDown(keyCode, event)
    }
}

