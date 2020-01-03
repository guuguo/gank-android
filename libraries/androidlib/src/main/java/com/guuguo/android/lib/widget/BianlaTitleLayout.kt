package com.guuguo.android.lib.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.ImageViewCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.guuguo.android.R
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.ktx.*
import com.guuguo.android.lib.systembar.SystemBarHelper
import kotlinx.android.synthetic.main.widget_title_layout.view.*

/**
 * Description: 超级布局，用来实现常见的横向图文布局
 * Create by dance, at 2019/5/21
 */
class BianlaTitleLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attributeSet, defStyleAttr) {


    //左边图片
    private var leftImage: Drawable?
    private var leftImageSize = dp2px(34f)

    //左边文字
    private var leftText = ""
    private var leftTextColor = Color.parseColor("#222222")
    private var leftTextSize = sp2px(16f)
    private var leftTextMarginLeft = dp2px(8f)
    private var leftTextMarginRight = dp2px(8f)
    private var leftTextMarginTop = 0
    private var leftTextMarginBottom = 0

    //左边子文字
    private var leftSubText = ""
    private var leftSubTextColor = Color.parseColor("#777777")
    private var leftSubTextSize = sp2px(13f)

    //中间文字
    private var centerText = ""
    private var centerTextColor = Color.parseColor("#222222")
    private var centerTextSize = sp2px(15f)
    private var centerTextBg: Drawable?
    private var centerTextAlign = TextView.TEXT_ALIGNMENT_CENTER
    private var centerTextStyle = Typeface.NORMAL

    //右边文字
    private var rightText = ""
    private var rightTextColor = Color.parseColor("#777777")
    private var rightTextSize = sp2px(15f)
    private var rightTextBg: Drawable?
    private var rightTextBgColor = 0
    private var rightTextWidth = 0
    private var rightTextHeight = 0

    //右边图片
    private var rightImage: Drawable?
    private var rightImageSize = dp2px(21f)
    private var rightImageMarginEnd = dp2px(15f)

    //右边图片2
    private var rightImage2: Drawable?
    private var rightImage2Size = dp2px(21f)
    private var rightImage2MarginEnd = dp2px(15f)

    //背景
    private var solid = 0 //填充色
    private var stroke = 0 //边框颜色
    private var strokeWidth = 0 //边框大小
    private var corner = 0 //圆角

    //上下分割线
    private var topLineColor = 0
    private var bottomLineColor = 0
    private var lineSize = .6f.dpToPx()
    private var bottomLinePaddingStart = 0

    //是否启用水波纹
    private var enableRipple = true
    private var rippleColor = Color.parseColor("#88999999")

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.BianlaTitleLayout)
        leftImage = ta.getDrawable(R.styleable.BianlaTitleLayout_widget_btl_leftImageSrc)
        leftImageSize = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_leftImageSize, leftImageSize)

        leftText = ta.getString(R.styleable.BianlaTitleLayout_widget_btl_leftText) ?: ""
        leftTextColor = ta.getColor(R.styleable.BianlaTitleLayout_widget_btl_leftTextColor, leftTextColor)
        leftTextSize = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_leftTextSize, leftTextSize)
        leftTextMarginLeft = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_leftTextMarginLeft, leftTextMarginLeft)
        leftTextMarginRight = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_leftTextMarginRight, leftTextMarginRight)
        leftTextMarginTop = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_leftTextMarginTop, leftTextMarginTop)
        leftTextMarginBottom = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_leftTextMarginBottom, leftTextMarginBottom)

        leftSubText = ta.getString(R.styleable.BianlaTitleLayout_widget_btl_leftSubText) ?: ""
        leftSubTextColor = ta.getColor(R.styleable.BianlaTitleLayout_widget_btl_leftSubTextColor, leftSubTextColor)
        leftSubTextSize = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_leftSubTextSize, leftSubTextSize)

        centerText = ta.getString(R.styleable.BianlaTitleLayout_widget_btl_centerText) ?: ""
        centerTextColor = ta.getColor(R.styleable.BianlaTitleLayout_widget_btl_centerTextColor, centerTextColor)
        centerTextSize = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_centerTextSize, centerTextSize)
        centerTextBg = ta.getDrawable(R.styleable.BianlaTitleLayout_widget_btl_centerTextBg)
        centerTextStyle = ta.getInt(R.styleable.BianlaTitleLayout_widget_btl_centerTextStyle, centerTextStyle)
        centerTextAlign = ta.getInt(R.styleable.BianlaTitleLayout_widget_btl_centerTextAlign, centerTextAlign)

        rightText = ta.getString(R.styleable.BianlaTitleLayout_widget_btl_rightText) ?: ""
        rightTextColor = ta.getColor(R.styleable.BianlaTitleLayout_widget_btl_rightTextColor, rightTextColor)
        rightTextSize = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_rightTextSize, rightTextSize)
        rightTextBg = ta.getDrawable(R.styleable.BianlaTitleLayout_widget_btl_rightTextBg)
        rightTextBgColor = ta.getColor(R.styleable.BianlaTitleLayout_widget_btl_rightTextBgColor, rightTextBgColor)
        rightTextWidth = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_rightTextWidth, rightTextWidth)
        rightTextHeight = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_rightTextHeight, rightTextHeight)

        rightImage = ta.getDrawable(R.styleable.BianlaTitleLayout_widget_btl_rightImageSrc)
        rightImageSize = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_rightImageSize, rightImageSize)
        rightImageMarginEnd = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_rightImageMarginLeft, rightImageMarginEnd)

        rightImage2 = ta.getDrawable(R.styleable.BianlaTitleLayout_widget_btl_rightImage2Src)
        rightImage2Size = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_rightImage2Size, rightImage2Size)
        rightImage2MarginEnd = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_rightImage2MarginLeft, rightImage2MarginEnd)

        solid = ta.getColor(R.styleable.BianlaTitleLayout_widget_btl_solid, solid)
        stroke = ta.getColor(R.styleable.BianlaTitleLayout_widget_btl_stroke, stroke)
        strokeWidth = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_strokeWidth, strokeWidth)
        corner = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_corner, corner)

        topLineColor = ta.getColor(R.styleable.BianlaTitleLayout_widget_btl_topLineColor, topLineColor)
        bottomLineColor = ta.getColor(R.styleable.BianlaTitleLayout_widget_btl_bottomLineColor, bottomLineColor)
        bottomLinePaddingStart = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_bottomLinePaddingStart, bottomLinePaddingStart)
        lineSize = ta.getDimensionPixelSize(R.styleable.BianlaTitleLayout_widget_btl_lineSize, lineSize)
        enableRipple = ta.getBoolean(R.styleable.BianlaTitleLayout_widget_btl_enableRipple, enableRipple)
        rippleColor = ta.getColor(R.styleable.BianlaTitleLayout_widget_btl_rippleColor, rippleColor)

        ta.recycle()
        inflate(context, R.layout.widget_title_layout, this)
        applyAttr()
        applySelf()
    }

    fun setup(leftImageRes: Int = 0,
              leftText: String? = null,
              leftSubText: String? = null,
              centerText: String? = null,
              rightText: String? = null,
              rightImageRes: Int = 0,
              rightImage2Res: Int = 0,
              rightImageSize: Int = -1,
              rightImage2Size: Int = -1,
              lineSize: Int = this.lineSize) {
        if (leftImageRes != 0) leftImage = drawable(leftImageRes)
        if (rightImageRes != 0) rightImage = drawable(rightImageRes)
        if (rightImage2Res != 0) rightImage2 = drawable(rightImage2Res)
        if (rightImageSize >= 0) this.rightImageSize = rightImageSize
        if (rightImage2Size >= 0) this.rightImage2Size = rightImage2Size
        leftText?.let { this.leftText = it }
        leftSubText?.let { this.leftSubText = it }
        centerText?.let { this.centerText = it }
        rightText?.let { this.rightText = it }
        this.lineSize = lineSize
        applyAttr()
        applySelf()
    }

    private fun applySelf() {

        if (solid != 0 || stroke != 0) {
            val drawable = createDrawable(color = solid, radius = corner.toFloat(), strokeColor = stroke, strokeWidth = strokeWidth,
                    enableRipple = enableRipple, rippleColor = rippleColor)
            setBackground(drawable)
        } else {
            if (Build.VERSION.SDK_INT >= 21 && enableRipple) {
                val rippleDrawable = RippleDrawable(ColorStateList.valueOf(rippleColor),
                        if (background != null) background else ColorDrawable(Color.TRANSPARENT), null)
                background = rippleDrawable
            }
        }
    }

    private fun applyAttr() {
        //左边图片
        if (leftImage == null) {
            ivLeftImage.gone()
        } else {
            ivLeftImage.visible()
            ivLeftImage.setImageDrawable(leftImage)
            ivLeftImage.widthAndHeight(leftImageSize, leftImageSize)
        }

        //左边文字
        if (leftText.isEmpty()) {
            tvLeftText.gone()
        } else {
            tvLeftText.visible()
            tvLeftText.text = leftText
            tvLeftText.setTextColor(leftTextColor)
            tvLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize.toFloat())
            tvLeftText.margin(bottomMargin = leftTextMarginBottom, topMargin = leftTextMarginTop)
            llLeft.margin(leftMargin = leftTextMarginLeft, rightMargin = leftTextMarginRight)
        }

        //左边子文字
        if (leftSubText.isEmpty()) {
            tvLeftSubText.gone()
        } else {
            tvLeftSubText.visible()
            tvLeftSubText.text = leftSubText
            tvLeftSubText.setTextColor(leftSubTextColor)
            tvLeftSubText.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftSubTextSize.toFloat())
        }

        //中间文字
        if (centerText.isEmpty()) {
            tvCenterText.invisible()
        } else {
            tvCenterText.visible()
            tvCenterText.text = centerText
            tvCenterText.setTextColor(centerTextColor)
            tvCenterText.setTextSize(TypedValue.COMPLEX_UNIT_PX, centerTextSize.toFloat())
            tvCenterText.typeface = Typeface.defaultFromStyle(centerTextStyle)
            tvCenterText.gravity = centerTextAlign
            if (centerTextBg != null) tvCenterText.setBackground(centerTextBg)
        }

        //右边文字
        if (rightText.isEmpty()) {
            tvRightText.gone()
        } else {
            tvRightText.visible()
            tvRightText.text = rightText
            tvRightText.setTextColor(rightTextColor)
            tvRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize.toFloat())
            if (rightTextBg != null) tvRightText.setBackground(rightTextBg)
            if (rightTextWidth != 0) tvRightText.width(rightTextWidth)
            if (rightTextHeight != 0) tvRightText.height(rightTextHeight)
            if (rightTextBgColor != 0) tvRightText.setBackgroundColor(rightTextBgColor)
        }

        //右边图片
        if (rightImage == null) {
            ivRightImage.gone()
        } else {
            ivRightImage.visible()
            ivRightImage.setImageDrawable(rightImage)
            ivRightImage.widthAndHeight(rightImageSize, rightImageSize)
            ivRightImage.margin(rightMargin = rightImageMarginEnd)
        }

        //右边图片2
        if (rightImage2 == null) {
            ivRightImage2.gone()
        } else {
            ivRightImage2.visible()
            ivRightImage2.setImageDrawable(rightImage2)
            ivRightImage2.widthAndHeight(rightImage2Size, rightImage2Size)
            ivRightImage2.margin(rightMargin = rightImage2MarginEnd)
        }
    }

    private val paint = Paint()
    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (topLineColor != 0) {
            paint.color = topLineColor
            canvas.drawRect(Rect(0, 0, measuredWidth, lineSize), paint)
        }
        if (bottomLineColor != 0) {
            paint.color = bottomLineColor
            canvas.drawRect(Rect(bottomLinePaddingStart, measuredHeight - lineSize, measuredWidth, measuredHeight), paint)
        }
    }

    fun leftImageView() = ivLeftImage
    fun rightTextView() = tvRightText
    fun centerTextView() = tvCenterText
    fun rightImageView() = ivRightImage
    fun rightImageView2() = ivRightImage2

    fun immersive(dark: Boolean = true) {
        lineSize = 0
        if (!dark) {
            centerTextColor = Color.WHITE
            rightTextColor = Color.WHITE
            leftImage = tintDrawable(leftImage)
            rightImage = tintDrawable(rightImage)
            rightImage2 = tintDrawable(rightImage2)
        }
        applyAttr()
        applySelf()
        invalidate()
    }

    private fun tintDrawable(it: Drawable?, color: Int = Color.WHITE): Drawable? {
        return it?.let {
            val wrappedDrawable = DrawableCompat.wrap(it.mutate())
            DrawableCompat.setTint(wrappedDrawable, color)
            wrappedDrawable
        }
    }
}