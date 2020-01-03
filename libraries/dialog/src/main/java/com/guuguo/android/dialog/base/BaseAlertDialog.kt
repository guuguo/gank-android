package com.guuguo.android.dialog.base

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView


abstract class BaseAlertDialog<T : BaseAlertDialog<T>>
/**
 * method execute order:
 * show:constrouctor---show---oncreate---onStart---onAttachToWindow
 * dismiss:dismiss---onDetachedFromWindow---onStop
 *
 * @param context
 */
(context: Context) : BaseDialog<T>(context) {
    /** container  */
    protected var mLlContainer: LinearLayout
    //title
    /** title  */
    protected var mTvTitle: TextView
    /** title content(标题)  */
    protected var mTitle: String? = null
    /** title textcolor(标题颜色)  */
    protected var mTitleTextColor: Int = 0
    /** title textsize(标题字体大小,单位sp)  */
    protected var mTitleTextSize: Float = 0.toFloat()
    /** enable title show(是否显示标题)  */
    protected var mIsTitleShow = true

    //content
    /** content  */
    protected var mTvContent: TextView
    /** content text  */
    protected var mContent: String? = null
    /** show gravity of content(正文内容显示位置)  */
    protected var mContentGravity = Gravity.CENTER_VERTICAL
    /** content textcolor(正文字体颜色)  */
    protected var mContentTextColor: Int = 0
    /** content textsize(正文字体大小)  */
    protected var mContentTextSize: Float = 0.toFloat()

    //btns
    /** num of btns, [1,3]  */
    protected var mBtnNum = 2
    /** btn container  */
    protected var mLlBtns: LinearLayout
    /** btns  */
    protected var mTvBtnLeft: TextView
    protected var mTvBtnRight: TextView
    protected var mTvBtnMiddle: TextView
    /** btn text(按钮内容)  */
    protected var mBtnLeftText = "取消"
    protected var mBtnRightText = "确定"
    protected var mBtnMiddleText = "继续"
    /** btn textcolor(按钮字体颜色)  */
    protected var mLeftBtnTextColor: Int = 0
    protected var mRightBtnTextColor: Int = 0
    protected var mMiddleBtnTextColor: Int = 0
    /** btn textsize(按钮字体大小)  */
    protected var mLeftBtnTextSize = 15f
    protected var mRightBtnTextSize = 15f
    protected var mMiddleBtnTextSize = 15f
    /** btn press color(按钮点击颜色)  */
    protected var mBtnPressColor = Color.parseColor("#E3E3E3")// #85D3EF,#ffcccccc,#E3E3E3
    /** left btn click listener(左按钮接口)  */
    protected var mOnBtnLeftClickL: (() -> Unit)? = null
    /** right btn click listener(右按钮接口)  */
    protected var mOnBtnRightClickL: (() -> Unit)? = null
    /** middle btn click listener(右按钮接口)  */
    protected var mOnBtnMiddleClickL: (() -> Unit)? = null

    /** corner radius,dp(圆角程度,单位dp)  */
    protected var mCornerRadius = 3f
    /** background color(背景颜色)  */
    protected var mBgColor = Color.parseColor("#ffffff")

    init {
        widthRatio(0.88f)

        mLlContainer = LinearLayout(context)
        mLlContainer.orientation = LinearLayout.VERTICAL

        /** title  */
        mTvTitle = TextView(context)

        /** content  */
        mTvContent = TextView(context)

        /**btns */
        mLlBtns = LinearLayout(context)
        mLlBtns.orientation = LinearLayout.HORIZONTAL

        mTvBtnLeft = TextView(context)
        mTvBtnLeft.gravity = Gravity.CENTER

        mTvBtnMiddle = TextView(context)
        mTvBtnMiddle.gravity = Gravity.CENTER

        mTvBtnRight = TextView(context)
        mTvBtnRight.gravity = Gravity.CENTER

    }

    override fun setUiBeforShow() {
        /** title  */
        mTvTitle.visibility = if (mIsTitleShow) View.VISIBLE else View.GONE

        mTvTitle.text = if (TextUtils.isEmpty(mTitle)) "温馨提示" else mTitle
        mTvTitle.setTextColor(mTitleTextColor)
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSize)

        /** content  */
        mTvContent.gravity = mContentGravity
        mTvContent.text = mContent
        mTvContent.setTextColor(mContentTextColor)
        mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, mContentTextSize)
        mTvContent.setLineSpacing(0f, 1.3f)

        /**btns */
        mTvBtnLeft.text = mBtnLeftText
        mTvBtnRight.text = mBtnRightText
        mTvBtnMiddle.text = mBtnMiddleText

        mTvBtnLeft.setTextColor(mLeftBtnTextColor)
        mTvBtnRight.setTextColor(mRightBtnTextColor)
        mTvBtnMiddle.setTextColor(mMiddleBtnTextColor)

        mTvBtnLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLeftBtnTextSize)
        mTvBtnRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, mRightBtnTextSize)
        mTvBtnMiddle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mMiddleBtnTextSize)

        if (mBtnNum == 1) {
            mTvBtnLeft.visibility = View.GONE
            mTvBtnRight.visibility = View.GONE
        } else if (mBtnNum == 2) {
            mTvBtnMiddle.visibility = View.GONE
        }

        mTvBtnLeft.setOnClickListener {
            mOnBtnLeftClickL?.invoke() ?: dismiss()
        }

        mTvBtnRight.setOnClickListener {
            mOnBtnRightClickL?.invoke() ?: dismiss()
        }

        mTvBtnMiddle.setOnClickListener {
            mOnBtnMiddleClickL?.invoke() ?: dismiss()
        }
    }

    /** set title text(设置标题内容) @return MaterialDialog  */
    fun title(title: String): T {
        mTitle = title
        return this as T
    }

    /** set title textcolor(设置标题字体颜色)  */
    fun titleTextColor(titleTextColor: Int): T {
        mTitleTextColor = titleTextColor
        return this as T
    }

    /** set title textsize(设置标题字体大小)  */
    fun titleTextSize(titleTextSize_SP: Float): T {
        mTitleTextSize = titleTextSize_SP
        return this as T
    }

    /** enable title show(设置标题是否显示)  */
    fun isTitleShow(isTitleShow: Boolean): T {
        mIsTitleShow = isTitleShow
        return this as T
    }

    /** set content text(设置正文内容)  */
    fun content(content: String): T {
        mContent = content
        return this as T
    }

    /** set content gravity(设置正文内容,显示位置)  */
    fun contentGravity(contentGravity: Int): T {
        mContentGravity = contentGravity
        return this as T
    }

    /** set content textcolor(设置正文字体颜色)  */
    fun contentTextColor(contentTextColor: Int): T {
        mContentTextColor = contentTextColor
        return this as T
    }

    /** set content textsize(设置正文字体大小,单位sp)  */
    fun contentTextSize(contentTextSize_SP: Float): T {
        mContentTextSize = contentTextSize_SP
        return this as T
    }

    /**
     * set btn text(设置按钮文字内容)
     * btnTexts size 1, middle
     * btnTexts size 2, left right
     * btnTexts size 3, left right middle
     */
    fun btnNum(btnNum: Int): T {
        if (btnNum < 1 || btnNum > 3) {
            throw IllegalStateException("btnNum is [1,3]!")
        }
        mBtnNum = btnNum

        return this as T
    }

    /**
     * set btn text(设置按钮文字内容)
     * btnTexts size 1, middle
     * btnTexts size 2, left right
     * btnTexts size 3, left right middle
     */
    fun btnText(vararg btnTexts: String): T {
        if (btnTexts.size < 1 || btnTexts.size > 3) {
            throw IllegalStateException(" range of param btnTexts length is [1,3]!")
        }

        if (btnTexts.size == 1) {
            mBtnMiddleText = btnTexts[0]
        } else if (btnTexts.size == 2) {
            mBtnLeftText = btnTexts[0]
            mBtnRightText = btnTexts[1]
        } else if (btnTexts.size == 3) {
            mBtnLeftText = btnTexts[0]
            mBtnRightText = btnTexts[1]
            mBtnMiddleText = btnTexts[2]
        }

        return this as T
    }

    /**
     * set btn textcolor(设置按钮字体颜色)
     * btnTextColors size 1, middle
     * btnTextColors size 2, left right
     * btnTextColors size 3, left right middle
     */
    fun btnTextColor(vararg btnTextColors: Int): T {
        if (btnTextColors.size < 1 || btnTextColors.size > 3) {
            throw IllegalStateException(" range of param textColors length is [1,3]!")
        }

        if (btnTextColors.size == 1) {
            mMiddleBtnTextColor = btnTextColors[0]
        } else if (btnTextColors.size == 2) {
            mLeftBtnTextColor = btnTextColors[0]
            mRightBtnTextColor = btnTextColors[1]
        } else if (btnTextColors.size == 3) {
            mLeftBtnTextColor = btnTextColors[0]
            mRightBtnTextColor = btnTextColors[1]
            mMiddleBtnTextColor = btnTextColors[2]
        }

        return this as T
    }

    /**
     * set btn textsize(设置字体大小,单位sp)
     * btnTextSizes size 1, middle
     * btnTextSizes size 2, left right
     * btnTextSizes size 3, left right middle
     */
    fun btnTextSize(vararg btnTextSizes: Float): T {
        if (btnTextSizes.size < 1 || btnTextSizes.size > 3) {
            throw IllegalStateException(" range of param btnTextSizes length is [1,3]!")
        }

        if (btnTextSizes.size == 1) {
            mMiddleBtnTextSize = btnTextSizes[0]
        } else if (btnTextSizes.size == 2) {
            mLeftBtnTextSize = btnTextSizes[0]
            mRightBtnTextSize = btnTextSizes[1]
        } else if (btnTextSizes.size == 3) {
            mLeftBtnTextSize = btnTextSizes[0]
            mRightBtnTextSize = btnTextSizes[1]
            mMiddleBtnTextSize = btnTextSizes[2]
        }

        return this as T
    }

    /** set btn press color(设置按钮点击颜色)  */
    fun btnPressColor(btnPressColor: Int): T {
        mBtnPressColor = btnPressColor
        return this as T
    }

    /** set corner radius (设置圆角程度)  */
    fun cornerRadius(cornerRadius_DP: Float): T {
        mCornerRadius = cornerRadius_DP
        return this as T
    }

    /** set backgroud color(设置背景色)  */
    fun bgColor(bgColor: Int): T {
        mBgColor = bgColor
        return this as T
    }

    /**
     * set btn click listener(设置按钮监听事件)
     * onBtnClickLs size 1, middle
     * onBtnClickLs size 2, left right
     * onBtnClickLs size 3, left right middle
     */
    fun setOnBtnClickL(vararg onBtnClickLs: (() -> Unit)?) {
        if (onBtnClickLs.isEmpty() || onBtnClickLs.size > 3) {
            throw IllegalStateException(" range of param onBtnClickLs length is [1,3]!")
        }

        if (onBtnClickLs.size == 1) {
            mOnBtnMiddleClickL = onBtnClickLs[0]
        } else if (onBtnClickLs.size == 2) {
            mOnBtnLeftClickL = onBtnClickLs[0]
            mOnBtnRightClickL = onBtnClickLs[1]
        } else if (onBtnClickLs.size == 3) {
            mOnBtnLeftClickL = onBtnClickLs[0]
            mOnBtnRightClickL = onBtnClickLs[1]
            mOnBtnMiddleClickL = onBtnClickLs[2]
        }
    }

    fun getColor(id: Int): Int {
        return if (Build.VERSION.SDK_INT >= 23) {
            mContext.getColor(id)
        } else {
            mContext.resources.getColor(id);
        }
    }
}
