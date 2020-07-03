package com.guuguo.android.dialog.base

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.WindowManager.LayoutParams
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.guuguo.android.dialog.utils.StatusBarUtils
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.getActivity
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.systembar.SystemBarHelper
import com.guuguo.android.lib.utils.DisplayUtil


abstract class BaseDialog<T : BaseDialog<T>> : Dialog {
    constructor(mContext: Context) : super(mContext) {
        this.mContext = mContext
        initIt()
    }

    constructor(mContext: Context, themeResId: Int) : super(mContext, themeResId) {
        this.mContext = mContext
        initIt()
    }

    constructor(context: Context, isPopupStyle: Boolean) : this(context) {
        mIsPopupStyle = isPopupStyle
        initIt()
    }

    protected lateinit var mContext: Context

    /** mTag(日志)  */
    protected lateinit var mTag: String
    /** (DisplayMetrics)设备密度  */
    protected lateinit var mDisplayMetrics: DisplayMetrics
    /** enable dismiss outside dialog(设置点击对话框以外区域,是否dismiss)  */
    protected var mCancel: Boolean = false
    /** dialog width scale(宽度比例)  */
    protected var mWidthRatio = 1f
    /** 是否沉浸状态栏全屏  */
    protected var mFullScreen = true
    /** dialog height scale(高度比例)  */
    protected var mHeightRatio: Float = 0.toFloat()
    /** top container(最上层容器,显示阴影那个)  */
    protected lateinit var mContentTop: LinearLayout
    /** container to control dialog height(用于控制对话框高度)  */
    protected lateinit var mDialogContent: LinearLayout
    /** the child of mDialogContent you create.(创建出来的mLlControlHeight的直接子View)  */
    /** get actual created view(获取实际创建的View)  */
    lateinit var mOnCreateView: View
    /** max height(最大高度)  */
    protected var mMaxHeight: Float = 0.toFloat()
    /** show Dialog as PopupWindow(像PopupWindow一样展示Dialog)  */
    private var mIsPopupStyle: Boolean = false
    /** automatic dimiss dialog after given delay(在给定时间后,自动消失)  */
    private var mAutoDismiss: Boolean = false
    /** delay (milliseconds) to dimiss dialog(对话框消失延时时间,毫秒值)  */
    private var mAutoDismissDelay: Long = 1500
    private var mMarginBottom: Int = 0

    private val mHandler = Handler(Looper.getMainLooper())


    fun initIt() {
        setDialogTheme()
        mTag = javaClass.simpleName
        setCanceledOnTouchOutside(true)
        Log.d(mTag, "constructor")
    }


    /** set dialog theme(设置对话框主题)  */
    private fun setDialogTheme() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)// android:windowNoTitle
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))// android:windowBackground
    }

    /**
     * inflate layout for dialog ui and return (填充对话框所需要的布局并返回)
     * <pre>
     * public View onCreateView() {
     * View inflate = View.inflate(mContext, R.layout.dialog_share, null);
     * return inflate;
     * }
    </pre> *
     */
    abstract fun onCreateView(): View

    open fun onViewCreated(inflate: View) {}

    /** set Ui data or logic opreation before attatched window(在对话框显示之前,设置界面数据或者逻辑)  */
    abstract fun setUiBeforShow()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(mTag, "onCreate")
        mDisplayMetrics = mContext.resources.displayMetrics
        mMaxHeight = if (isLandscape()) {
            mDisplayMetrics.heightPixels.toFloat()
        } else {
            if (mFullScreen)
                (DisplayUtil.getScreenRealHeight(mContext) - DisplayUtil.getNavigationBarHeight(mContext)).toFloat() //真实屏幕高度减去底部导航栏的高度
            else (DisplayUtil.getScreenRealHeight(mContext) - StatusBarUtils.getHeight(mContext) - DisplayUtil.getNavigationBarHeight(mContext)).toFloat()
        }
        mContentTop = LinearLayout(mContext)
        mContentTop.gravity = Gravity.CENTER

        mDialogContent = LinearLayout(mContext)
        mDialogContent.orientation = LinearLayout.VERTICAL

        mOnCreateView = onCreateView()
        mDialogContent.addView(mOnCreateView)
        mContentTop.addView(mDialogContent)
        onViewCreated(mOnCreateView)

        if (mIsPopupStyle) {
            setContentView(mContentTop, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT))
        } else {
            setContentView(mContentTop, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        }

        mContentTop.setOnClickListener {
            if (mCancel) {
                dismiss()
            }
        }

        mOnCreateView.isClickable = true
    }

    /**
     * when dailog attached to window,set dialog width and height and show anim
     * (当dailog依附在window上,设置对话框宽高以及显示动画)
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(mTag, "onAttachedToWindow")

        val insetBottom = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getActivity()?.window?.decorView?.rootWindowInsets?.stableInsetBottom.safe()
        } else 0

        val width: Int
        val createdWidth: Int
        if (mWidthRatio == 0f) {
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            createdWidth = ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            width = (mDisplayMetrics.widthPixels * mWidthRatio).toInt()
            createdWidth = ViewGroup.LayoutParams.MATCH_PARENT
        }
        val height: Int
        val createdHeight: Int
        if (mHeightRatio == 0f) {
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            createdHeight = ViewGroup.LayoutParams.WRAP_CONTENT
//            this.mMarginBottom = DisplayUtil.getNavigationBarHeight(mContext)
        } else {
            height = ViewGroup.LayoutParams.MATCH_PARENT
            createdHeight = ViewGroup.LayoutParams.MATCH_PARENT
        }

        mOnCreateView.layoutParams = mOnCreateView.layoutParams.also { it.height = createdHeight;it.width = createdWidth; }
        mDialogContent.layoutParams = LinearLayout.LayoutParams(width, height)

        mContentTop.doOnNextLayout {
            val bgLocation = IntArray(2)
            window!!.decorView.findViewById<FrameLayout>(Window.ID_ANDROID_CONTENT).getLocationOnScreen(bgLocation)
            /** 是否是沉浸式 有些vivo 三星手机无法沉浸式*/
            val isNotRealImmerse = bgLocation[1] == SystemBarHelper.getStatusBarHeight(mContext)

//            if (mFullScreen && !isNotRealImmerse)
//                SystemBarHelper.immersiveStatusBar(window, 0f)
            val contentHeight = mDialogContent.height - insetBottom

            if (mHeightRatio > 0 && mHeightRatio < 1) {
                mDialogContent.updateLayoutParams<LinearLayout.LayoutParams> {
                    if (isNotRealImmerse) {//没顶上去就减去状态栏高度,另外再减去沉浸状态栏的 负的margin导致的高度变高
                        this.height = ((contentHeight - SystemBarHelper.getStatusBarHeight(context) * 2) * mHeightRatio).toInt()
                    } else {
                        this.height = ((contentHeight) * mHeightRatio).toInt()
                    }
                }
            }
            if (isNotRealImmerse) {
                window!!.decorView.findViewById<FrameLayout>(Window.ID_ANDROID_CONTENT).getChildAt(0).apply {
                    top = 0
                    updateLayoutParams<FrameLayout.LayoutParams> {
                        topMargin = 0 //把沉浸式改的topMargin改回来
                        bottomMargin = insetBottom
                    }
                    setPadding(0, 0, 0, SystemBarHelper.getStatusBarHeight(context))
                }
                mOnCreateView.requestLayout()
            }
        }
        setUiBeforShow()
    }


    @JvmName("updateLayoutParamsTyped")
    private inline fun <reified T : ViewGroup.LayoutParams> View.updateLayoutParams(block: T.() -> Unit) {
        val params = layoutParams as T
        block(params)
        layoutParams = params
    }

    override fun show() {
        super.show()
        immersiveStatusBar()
    }

    fun isLandscape() = mContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    open protected fun immersiveStatusBar() {
        val layoutParams = window!!.attributes
        layoutParams.width = mContext.resources.displayMetrics.widthPixels
        layoutParams.height = if (isLandscape()) mContext.resources.displayMetrics.heightPixels else DisplayUtil.getScreenRealHeight(mContext)
        window!!.attributes = layoutParams
        SystemBarHelper.immersiveStatusBar(window, 0f)
    }

    override fun onStart() {
        super.onStart()
        Log.d(mTag, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(mTag, "onStop")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(mTag, "onDetachedFromWindow")
    }

    /** dismiss without anim(无动画dismiss)  */
    fun superDismiss() {
        super.dismiss()
    }

    /** dialog anim by styles(动画弹出对话框,style动画资源)  */
    fun show(animStyle: Int) {
        val window = window
        window!!.setWindowAnimations(animStyle)
        show()
    }

    /** show at location only valid for mIsPopupStyle true(指定位置显示,只对isPopupStyle为true有效)  */
    fun showAtLocation(gravity: Int, x: Int, y: Int) {
        if (mIsPopupStyle) {
            val window = window
            val params = window!!.attributes
            window.setGravity(gravity)
            params.x = x
            params.y = y
        }

        show()
    }

    /** show at location only valid for mIsPopupStyle true(指定位置显示,只对isPopupStyle为true有效)  */
    fun showAtLocation(x: Int, y: Int) {
        val gravity = Gravity.LEFT or Gravity.TOP//Left Top (坐标原点为右上角)
        showAtLocation(gravity, x, y)
    }

    /** set window dim or not(设置背景是否昏暗)  */
    fun dimEnabled(isDimEnabled: Boolean): T {
        if (isDimEnabled) {
            window!!.addFlags(LayoutParams.FLAG_DIM_BEHIND)
        } else {
            window!!.clearFlags(LayoutParams.FLAG_DIM_BEHIND)
        }
        return this as T
    }

    /** 是否全屏(全屏则沉浸状态栏)  */
    fun fullScreen(fullScreen: Boolean): T {
        mFullScreen = fullScreen
        return this as T
    }

    /** set dialog width scale:0-1(设置对话框宽度,占屏幕宽的比例0-1)  */
    fun widthRatio(widthScale: Float): T {
        this.mWidthRatio = widthScale
        return this as T
    }

    /** 设置底部margin,让dialog位置上移或者下移 */
    fun marginBottom(marginBottom: Int): T {
        this.mMarginBottom = marginBottom
        return this as T
    }

    /** set dialog height scale:0-1(设置对话框高度,占屏幕高的比例0-1)  */
    fun heightRatio(heightScale: Float): T {
        mHeightRatio = heightScale
        return this as T
    }

    /** automatic dimiss dialog after given delay(在给定时间后,自动消失)  */
    fun autoDismiss(autoDismiss: Boolean): T {
        mAutoDismiss = autoDismiss
        return this as T
    }

    /** set dealy (milliseconds) to dimiss dialog(对话框消失延时时间,毫秒值)  */
    fun autoDismissDelay(autoDismissDelay: Long): T {
        mAutoDismissDelay = autoDismissDelay
        return this as T
    }

    private fun delayDismiss() {
        if (mAutoDismiss && mAutoDismissDelay > 0) {
            mHandler.postDelayed({ dismiss() }, mAutoDismissDelay)
        }
    }

    /**  if AutoDismiss,prevent Touch (如果自动消失,阻止点击) */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return if (mAutoDismiss) {
            true
        } else super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        if (mAutoDismiss) {
            return
        }
        super.onBackPressed()
    }

    /** dp to px  */
    protected fun dp2px(dp: Float): Int {
        val scale = mContext.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    override fun setCanceledOnTouchOutside(cancel: Boolean) {
        this.mCancel = cancel
        super.setCanceledOnTouchOutside(cancel)
    }

    private inline fun View.updateLayoutParams(block: ViewGroup.LayoutParams.() -> Unit) {
        updateLayoutParams<ViewGroup.LayoutParams>(block)
    }

    private inline fun View.doOnNextLayout(crossinline action: (view: View) -> Unit) {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                view: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                view.removeOnLayoutChangeListener(this)
                action(view)
            }
        })
    }
}
