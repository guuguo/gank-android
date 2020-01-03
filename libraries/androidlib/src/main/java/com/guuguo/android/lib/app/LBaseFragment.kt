package com.guuguo.android.lib.app

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import com.google.android.material.appbar.AppBarLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.guuguo.android.R
import com.guuguo.android.lib.extension.dpToPx
import com.guuguo.android.lib.extension.initNav
import com.guuguo.android.lib.extension.safe
import com.guuguo.android.lib.ktx.gone
import com.guuguo.android.lib.ktx.visible
import com.guuguo.android.lib.systembar.SystemBarHelper
import com.guuguo.android.lib.widget.BianlaTitleLayout
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.Observable


/**
 * Created by guodeqing on 16/5/31.
 * [getLayoutResId] 设置 layout it
 * [customHeaderType] 设置自定义头上类型
 * [generateCustomHeader] 构建自定义头部
 * [loadDataObs] rxJava 的可观察数据加载 用户数据串型操作等
 * [loadData] 不可观察数据加载，放任自流
 * [getToolBar] 获取自定义的toolbar 用来沉浸状态栏，添加返回按钮以及电击事件等操作
 * [isNavigationBack] 配置是否在 toolbar 上添加返回按钮
 */
abstract class LBaseFragment : RxFragment() {

    protected val TAG = this.javaClass.simpleName
    lateinit var activity: AppCompatActivity
    protected abstract fun getLayoutResId(): Int

    private var isPrepare = false
    var mFirstLazyLoad = true
    protected var contentView: View? = null

    open protected fun setLayoutResId(inflater: LayoutInflater?, resId: Int, container: ViewGroup?): View {
        return inflater!!.inflate(resId, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as AppCompatActivity
    }

    var customHeader: View? = null
    ///自定义头部类型
    open fun customHeaderType() = CustomHeader.NONE

    enum class CustomHeader {
        ///无自定义头
        NONE,
        ///上下排列头
        LINEAR,
        ///悬浮顶部头
        FLOAT
    }

    /** 让自定义头适配状态栏*/
    protected fun adapterCustomHeaderSystemBar(darkFont: Boolean = true) {
        customHeader?.let {
            if (it is AppBarLayout) {
                SystemBarHelper.setPadding(activity, it)
            } else if (it is BianlaTitleLayout) {
                SystemBarHelper.setHeightAndPadding(activity, it)
            }
            if (darkFont)
                SystemBarHelper.setStatusBarDarkMode(activity)
        }
    }

    ///构建自定义头部,并加入parent中
    open fun generateCustomHeader(parent: ViewGroup): View? =
            layoutInflater.inflate(R.layout.base_common_title, parent).findViewById(R.id.super_layout)

    protected open fun init(view: View, savedInstanceState: Bundle?) {
        activity = getActivity() as AppCompatActivity
        initToolbar()
        initView()
        loadData()
        loadDataObs(false)?.subscribe({}, {})?.isDisposed
        loadData(false)

        //如果准备好 懒加载
        isPrepare = true
        if (userVisibleHint && !isHidden) {
            lazyLoad()
            mFirstLazyLoad = false
        }
    }

    open fun onBackPressed(): Boolean = false

    /*toolbar*/
    open fun getBackIconRes(): Int = 0

    open fun getToolBar(): Toolbar? = null //fragment有自己的toolbar就重写该方法。fragment修改toolbar用activity.getSupportActionBar
    open protected fun initToolbar() {
        if (customHeaderType() != CustomHeader.NONE && contentView != null) {

            val parentView = contentView!!.parent!! as ViewGroup
            val params = contentView?.layoutParams
            parentView.removeView(contentView)

            val newContent = if (customHeaderType() == CustomHeader.LINEAR) {
                LinearLayout(contentView!!.context).apply {
                    orientation = LinearLayout.VERTICAL
                    customHeader = generateCustomHeader(this)
                    addView(contentView, params?.also { ViewGroup.LayoutParams(it.width, it.height) })
                }
            } else {
                FrameLayout(contentView!!.context).apply {
                    addView(contentView, params?.also { ViewGroup.LayoutParams(it.width, it.height) })
                    customHeader = generateCustomHeader(this)
                    customHeader?.setBackgroundColor(Color.TRANSPARENT)
                }
            }

            parentView.addView(newContent, params)
            customHeader?.let {
                if (it is BianlaTitleLayout) {
                    it.setup(centerText = getHeaderTitle(), leftImageRes = getBackIconRes().safe(R.drawable.ic_arrow_back_24dp))
                    it.leftImageView().setOnClickListener {
                        if (!onBackPressed()) activity.onBackPressed()
                    }

                } else if (it is AppBarLayout) {
                    if (isNavigationBack()) {
                        it.findViewById<Toolbar>(R.id.id_tool_bar)?.initNav(this, getBackIconRes().safe(R.drawable.ic_arrow_back_white_24dp))
                    }
                    val str = getHeaderTitle()
                    if (str != null)
                        setTitle(str)
                }
            }

        } else {
            if (isNavigationBack()) {
                getToolBar()?.initNav(activity, getBackIconRes().safe(R.drawable.ic_arrow_back_white_24dp))
            }
            val str = getHeaderTitle()
            if (str != null)
                setTitle(str)
        }
    }


    /*init*/
    @Deprecated("用带参数的方法吧", replaceWith = ReplaceWith("loadData(false)"), level = DeprecationLevel.WARNING)
    open fun loadData() {
    }

    /**需要返回 obs 的都在这里面写好了 默认init的时候也会被调用*/
    open fun loadDataObs(isRefresh: Boolean): Observable<*>? = null

    /**[isFromNet] 是否单纯从网络获取*/
    open fun loadData(isFromNet: Boolean) {}

    protected open fun initVariable(savedInstanceState: Bundle?) {}
    protected open fun initView() {}
    protected open fun getMenuResId() = 0
    open fun isNavigationBack() = true

    open fun overridePendingTransition() = Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = super.onCreateView(inflater, container, savedInstanceState)
        initVariable(savedInstanceState)
        if (contentView == null) {
            contentView = setLayoutResId(inflater, getLayoutResId(), container)
        }
        return contentView
    }
    /*menu and title*/

    open fun getHeaderTitle(): String? = null
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (getMenuResId() != 0)
            inflater.inflate(getMenuResId(), menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    protected open fun setTitle(title: String) {
        activity.title = title
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
    }

    //如果准备好并可见,而且没有懒加载过 懒加载
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isPrepare) {
            lazyLoad()
            mFirstLazyLoad = false
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isPrepare) {
            lazyLoad()
            mFirstLazyLoad = false
        }
        childFragmentManager.fragments.forEach {
            if (hidden) it.userVisibleHint = false
            else if (it.isVisible) {
                it.userVisibleHint = true
            }

            if (it.isVisible) it.onHiddenChanged(hidden)
        }
    }

    fun <V : View> findViewById(@IdRes id: Int): V? {
        return contentView?.findViewById<V>(id)
    }

    open fun lazyLoad() {
    }


    open val isFullScreen: Boolean
        get() = false
}